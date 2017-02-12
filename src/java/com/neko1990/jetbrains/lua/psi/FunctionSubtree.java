package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.neko1990.jetbrains.lua.LuaLanguage;
import org.antlr.jetbrains.adaptor.SymtabUtils;
import org.antlr.jetbrains.adaptor.psi.IdentifierDefSubtree;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A subtree associated with a function definition.
 *  Its scope is the set of arguments.
 */
public class FunctionSubtree extends IdentifierDefSubtree implements ScopeNode {
	public FunctionSubtree(@NotNull ASTNode node) {
		super(node, node.getElementType() );
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
		return SymtabUtils.resolve(this, LuaLanguage.INSTANCE,
		                           element, "/functionstat/funcbody/param/NAME");
	}
}
