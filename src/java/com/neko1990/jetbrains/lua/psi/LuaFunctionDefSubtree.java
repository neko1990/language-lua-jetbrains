package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.neko1990.jetbrains.lua.luatypes.Proto;
import org.jetbrains.annotations.NotNull;

/** A subtree associated with a function definition.
 *  Its scope is the set of arguments.
 */
public class LuaFunctionDefSubtree extends LuaStat {
	public Proto proto;

	public LuaFunctionDefSubtree(@NotNull ASTNode node ) {
		super(node);
	}

}
