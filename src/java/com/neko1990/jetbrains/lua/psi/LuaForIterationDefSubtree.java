package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaForIterationDefSubtree extends ANTLRPsiNode {
    public LuaForIterationDefSubtree(@NotNull ASTNode node ) {
        super(node);
    }
}
