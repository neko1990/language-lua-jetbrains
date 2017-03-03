package com.neko1990.jetbrains.lua.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;
import org.antlr.jetbrains.adaptor.psi.Trees;
import com.neko1990.jetbrains.lua.LuaLanguage;
import com.neko1990.jetbrains.lua.LuaParserDefinition;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import com.neko1990.jetbrains.lua.parser.LuaParser;

/** From doc: "Every element which can be renamed or referenced
 *             needs to implement com.intellij.psi.PsiNamedElement interface."
 *
 *  So, all leaf nodes that represent variables, functions, classes, or
 *  whatever in your plugin language must be instances of this not just
 *  LeafPsiElement.  Your ASTFactory should create this kind of object for
 *  NAME tokens. This node is for references *and* definitions because you can
 *  highlight a function and say "jump to definition". Also we want defs
 *  to be included in "find usages." Besides, there is no context information
 *  in the AST factory with which you could decide whether this node
 *  is a definition or a reference.
 *
 *  PsiNameIdentifierOwner (vs PsiNamedElement) implementations are the
 *  corresponding subtree roots that define symbols.
 *
 *  You can click on an NAME in the editor and ask for a rename for any node
 *  of this type.
 */
public class LuaNamePSILeafNode extends ANTLRPsiLeafNode implements PsiNamedElement {
	public LuaNamePSILeafNode(IElementType type, CharSequence text) {
		super(type, text);
	}

	@Override
	public String getName() {
		return getText();
	}

	/** Alter this node to have text specified by the argument. Do this by
	 *  creating a new node through parsing of an NAME and then doing a
	 *  replace.
	 */
	@Override
	public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
		if ( getParent()==null ) return this;
		PsiElement newID = Trees.createLeafFromText(getProject(),
		                                            LuaLanguage.INSTANCE,
		                                            getContext(),
		                                            name,
													LuaParserDefinition.NAME);
		if ( newID!=null ) {
			return this.replace(newID); // use replace on leaves but replaceChild on NAME nodes that are part of defs/decls.
		}
		return this;
	}

	 /** Create and return a PsiReference object associated with this
	 *  `NAME node`. The reference object will be asked to resolve this ref
	 *  by using the text of this node to identify the appropriate definition
	 *  site. The definition site is typically a subtree for a function
	 *  or variable definition whereas this reference is just to this NAME
	 *  leaf node.
	 *
	 *  As the AST factory has no context and cannot create different kinds
	 *  of PsiNamedElement nodes according to context, every NAME node
	 *  in the tree will be of this type. So, we distinguish references
	 *  from definitions or other uses by looking at context in this method
	 *  as we have parent (context) information.
	 */
	@Override
	public PsiReference getReference() {
		PsiElement parent = getParent();
		IElementType elType = parent.getNode().getElementType();
		// do not return a reference for the `NAME nodes` in a definition
		if ( elType instanceof RuleIElementType ) {
			switch ( ((RuleIElementType) elType).getRuleIndex() ) {
				case LuaParser.RULE_functionname: // function `functionname'() end // it's a definition
				case LuaParser.RULE_localfunctionstat: // local function `NAME'() end // it's a definition
				case LuaParser.RULE_namelist: // local a , b = xxxxx  // [a , b] it'a definition
				case LuaParser.RULE_colonfield:  // only in functionname
				case LuaParser.RULE_recfield:
				case LuaParser.RULE_listfield:
				case LuaParser.RULE_foriterdef:
				case LuaParser.RULE_prefixexp: // it should be resolved in global state.
					return new LuaVariableRef(this);
				case LuaParser.RULE_dotfield:  // part of functionname (definition) or primaryexp (index or newindex)
				{
					PsiElement parent2 =  parent.getParent();
					IElementType pElType = parent2.getNode().getElementType();
					switch (((RuleIElementType) pElType).getRuleIndex()) {
						case LuaParser.RULE_functionname:
							return null;
						case LuaParser.RULE_primaryexp:
							return new LuaVariableRef(this);  // this should field ref
						default:
							return null;
					}
				}
				default:
					return null;
			}
		}
		return null;
	}
}
