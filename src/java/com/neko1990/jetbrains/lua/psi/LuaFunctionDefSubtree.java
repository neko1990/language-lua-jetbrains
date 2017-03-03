package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.neko1990.jetbrains.lua.luatypes.Proto;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/** A subtree associated with a function definition.
 *  Its scope is the set of arguments.
 */
public class LuaFunctionDefSubtree extends LuaStat {
	// public Proto proto;
	public String name;
	public List<LuaPsiNode> params;
	public LuaChunkSubTree body;
	public boolean hasThreeDots;

	public LuaFunctionDefSubtree(@NotNull ASTNode node ) {
		super(node);
	}
}
