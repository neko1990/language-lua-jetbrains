package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.neko1990.jetbrains.lua.luatypes.Proto;
import org.jetbrains.annotations.NotNull;

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaLocalFunctionDefSubtree extends LuaStat{
    public Proto proto;

    public LuaLocalFunctionDefSubtree(@NotNull ASTNode node ) {
        super(node);
        // proto = new Proto(node);
    }
}
