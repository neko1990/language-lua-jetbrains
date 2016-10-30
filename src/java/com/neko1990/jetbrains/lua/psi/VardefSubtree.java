package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.IdentifierDefSubtree;
import org.jetbrains.annotations.NotNull;

public class VardefSubtree extends IdentifierDefSubtree {
	public VardefSubtree(@NotNull ASTNode node) {
		super(node,node.getElementType());
	}
}
