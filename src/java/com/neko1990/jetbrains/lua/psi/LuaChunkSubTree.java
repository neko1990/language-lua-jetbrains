package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;

import java.util.List;

/**
 * Created by neko1990 on 3/1/17.
 */
public class LuaChunkSubTree extends LuaPsiNode {
    public List<LuaStat> seq;
    // public LuaStat last; // returnstat | breakstat

    public LuaChunkSubTree(ASTNode node) {
        super(node);
    }
}
