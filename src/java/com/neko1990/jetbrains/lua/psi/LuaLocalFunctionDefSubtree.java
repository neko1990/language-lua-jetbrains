package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.neko1990.jetbrains.lua.luatypes.Proto;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaLocalFunctionDefSubtree extends LuaStat{
    public Proto proto;

    public String name;
    public List<LuaPsiNode> params;
    public LuaChunkSubTree body;
    public boolean hasThreeDots;

    public LuaLocalFunctionDefSubtree(@NotNull ASTNode node ) {
        super(node);
    }
}
