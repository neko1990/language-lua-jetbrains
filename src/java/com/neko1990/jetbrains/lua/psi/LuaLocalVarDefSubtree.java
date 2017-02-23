package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.IdentifierDefSubtree;
import org.jetbrains.annotations.NotNull;

public class LuaLocalVarDefSubtree extends IdentifierDefSubtree {
	public LuaLocalVarDefSubtree(@NotNull ASTNode node) {
		super(node,node.getElementType());
	}
}
