package com.neko1990.jetbrains.lua.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class LuaVariableRef extends LuaElementRef {
	public LuaVariableRef(@NotNull IdentifierPSINode element) {
		super(element);
	}

	@Override
	public boolean isDefSubtree(PsiElement def) {
		return def instanceof LuaLocalVarDefSubtree;
	}
}
