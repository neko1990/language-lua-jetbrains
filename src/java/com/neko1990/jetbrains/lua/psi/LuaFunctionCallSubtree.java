package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class LuaFunctionCallSubtree extends ANTLRPsiNode {
	public LuaFunctionCallSubtree(@NotNull ASTNode node) {
		super(node);
	}
}
