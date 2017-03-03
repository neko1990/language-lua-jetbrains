package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LuaLocalVarDefSubtree extends LuaStat {
	public List<LuaPsiNode> targets;
	public List<LuaPsiNode> values;

	public LuaLocalVarDefSubtree(@NotNull ASTNode node ) {
		super(node);
	}
}
