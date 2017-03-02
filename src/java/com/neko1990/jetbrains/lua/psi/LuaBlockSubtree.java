package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.neko1990.jetbrains.lua.LuaLanguage;
import org.antlr.jetbrains.adaptor.SymtabUtils;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LuaBlockSubtree extends LuaStat implements ScopeNode {
	public LuaBlockSubtree(@NotNull ASTNode node) {
		super(node);
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
		return SymtabUtils.resolve(this, LuaLanguage.INSTANCE,
		                           element, "/block/chunk/stat/localstat/namelist/NAME");
	}
}
