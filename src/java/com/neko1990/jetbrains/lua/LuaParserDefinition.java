package com.neko1990.jetbrains.lua;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.neko1990.jetbrains.lua.psi.*;
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.antlr.jetbrains.adaptor.parser.ANTLRParserAdaptor;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiLeafNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import com.neko1990.jetbrains.lua.parser.LuaLexer;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LuaParserDefinition implements ParserDefinition {
	public static final IFileElementType FILE =
		new IFileElementType(LuaLanguage.INSTANCE);

	public static TokenIElementType NAME;

	static {
		PSIElementTypeFactory.defineLanguageIElementTypes(LuaLanguage.INSTANCE,
		                                                  LuaParser.tokenNames,
		                                                  LuaParser.ruleNames);
		List<TokenIElementType> tokenIElementTypes =
			PSIElementTypeFactory.getTokenIElementTypes(LuaLanguage.INSTANCE);
		NAME = tokenIElementTypes.get(LuaLexer.NAME);
	}

	public static final TokenSet COMMENTS =
		PSIElementTypeFactory.createTokenSet(
			LuaLanguage.INSTANCE,
			LuaLexer.SHORT_COMMENT,
			LuaLexer.LONG_COMMENT);

	public static final TokenSet WHITESPACE =
		PSIElementTypeFactory.createTokenSet(
			LuaLanguage.INSTANCE,
			LuaLexer.WS);

	public static final TokenSet STRING =
		PSIElementTypeFactory.createTokenSet(
			LuaLanguage.INSTANCE,
			LuaLexer.NORMALSTRING,
			LuaLexer.LONGSTRING,
			LuaLexer.CHARSTRING);

	@NotNull
	@Override
	public Lexer createLexer(Project project) {
		LuaLexer lexer = new LuaLexer(null);
		return new ANTLRLexerAdaptor(LuaLanguage.INSTANCE, lexer);
	}

	@NotNull
	public PsiParser createParser(final Project project) {
		final LuaParser parser = new LuaParser(null);
		return new ANTLRParserAdaptor(LuaLanguage.INSTANCE, parser) {
			@Override
			protected ParseTree parse(Parser parser, IElementType root) {
				// start rule depends on root passed in; sometimes we want to create an NAME node etc...
				if ( root instanceof IFileElementType ) {
					return ((LuaParser) parser).file();
				}
				// TODO: What if root is not file elementtype, should we write all rules?
				// let's hope it's an NAME as needed by "rename function"
				return ((LuaParser) parser).chunk();
			}
		};
	}

	/** "Tokens of those types are automatically skipped by PsiBuilder." */
	@NotNull
	public TokenSet getWhitespaceTokens() {
		return WHITESPACE;
	}

	@NotNull
	public TokenSet getCommentTokens() {
		return COMMENTS;
	}

	@NotNull
	public TokenSet getStringLiteralElements() {
		return STRING;
	}

	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;
	}

	/** What is the IFileElementType of the root parse tree node? It
	 *  is called from {@link #createFile(FileViewProvider)} at least.
	 */
	@Override
	public IFileElementType getFileNodeType() {
		return FILE;
	}

	/** Create the root of your PSI tree (a PsiFile).
	 *
	 *  From IntelliJ IDEA Architectural Overview:
	 *  "A PSI (Program Structure Interface) file is the root of a structure
	 *  representing the contents of a file as a hierarchy of elements
	 *  in a particular programming language."
	 *
	 *  PsiFile is to be distinguished from a FileASTNode, which is a parse
	 *  tree node that eventually becomes a PsiFile. From PsiFile, we can get
	 *  it back via: {@link PsiFile#getNode}.
	 */
	@Override
	public PsiFile createFile(FileViewProvider viewProvider) {
		return new LuaPSIFileRoot(viewProvider);
	}

	@NotNull
	public PsiElement createLuaExpr(ASTNode node) {
		return new LuaSimpleExprNode(node);
	}

	/** Convert from *NON-LEAF* parse node (AST they call it)
	 *  to PSI node. Leaves are created in the AST factory.
	 *  Rename re-factoring can cause this to be
	 *  called on a TokenIElementType since we want to rename NAME nodes.
	 *  In that case, this method is called to create the root node
	 *  but with NAME type. Kind of strange, but we can simply create a
	 *  ASTWrapperPsiElement to make everything work correctly.
	 *
	 *  RuleIElementType.  Ah! It's that NAME is the root
	 *  IElementType requested to parse, which means that the root
	 *  node returned from parsetree->PSI conversion.  But, it
	 *  must be a CompositeElement! The adaptor calls
	 *  rootMarker.done(root) to finish off the PSI conversion.
	 *  See {@link ANTLRParserAdaptor#parse(IElementType root,
	 *  PsiBuilder)}
	 *
	 *  If you don't care to distinguish PSI nodes by type, it is
	 *  sufficient to create a {@link ANTLRPsiNode} around
	 *  the parse tree node
	 */
	@NotNull
	public PsiElement createElement(ASTNode node) {
		IElementType elType = node.getElementType();
		if ( elType instanceof TokenIElementType ) {
//			switch (( (TokenIElementType) elType ).getANTLRTokenType() ) {
//				case LuaParser.NAME:
//					return new ANTLRPsiLeafNode(elType,node.getText());
//			}  // It should not be a NAME node LuaASTFactory.createLeaf() already done this.
			return new ANTLRPsiLeafNode(elType,node.getText());
		}
		if ( !(elType instanceof RuleIElementType) ) {
			return new ANTLRPsiNode(node);
		}
		RuleIElementType ruleElType = (RuleIElementType) elType;
		switch ( ruleElType.getRuleIndex() ) {
			case LuaParser.RULE_chunk:
				return new LuaChunkSubTree(node);
			case LuaParser.RULE_ifstat:
				return new LuaIfStatSubtree(node);
			case LuaParser.RULE_whilestat:
				return new LuaWhileStatSubtree(node);
			case LuaParser.RULE_block:
				return new LuaBlockStatSubtree(node);
			case LuaParser.RULE_functionstat:
				return new LuaFunctionDefSubtree(node);
			case LuaParser.RULE_forstat:
				return new LuaForSubtree(node);
			case LuaParser.RULE_repeatstat:
				return new LuaRepeatStatSubtree(node);
			case LuaParser.RULE_localfunctionstat:
				return new LuaLocalFunctionDefSubtree(node);
			case LuaParser.RULE_localstat:
				return new LuaLocalVarDefSubtree(node);
			/////////////////////////////////////////////////
			case LuaParser.RULE_foriterdef:
				return new LuaForIterationDefSubtree(node);
			// all statement except exprstat //
			case LuaParser.RULE_assignexpr:
				return new LuaAssign(node);
			case LuaParser.RULE_primaryexp:
				return new LuaPrimaryNode(node);
			/////////////////////////////////////////////////
			case LuaParser.RULE_expr:
				return createLuaExpr(node);
			case LuaParser.RULE_constructor:
				return new LuaTableConstructorSubtree(node);
			default:
				return new ANTLRPsiNode(node);
		}
	}
}
