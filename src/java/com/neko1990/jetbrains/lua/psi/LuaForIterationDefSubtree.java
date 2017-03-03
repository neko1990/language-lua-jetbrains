package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaForIterationDefSubtree extends LuaPsiNode {
    public List<LuaPsiNode> targets;
    public List<LuaPsiNode> iters;

    public LuaForIterationDefSubtree(@NotNull ASTNode node ) {
        super(node);
    }
}
