package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class LuaLocalVarDefSubtree extends LuaStat {
	public LuaLocalVarDefSubtree(@NotNull ASTNode node ) {
		super(node);
	}
}
