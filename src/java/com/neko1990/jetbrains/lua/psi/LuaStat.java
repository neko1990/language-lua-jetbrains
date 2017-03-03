package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by neko1990 on 3/2/17.
 */
public abstract class LuaStat extends LuaPsiNode {
    public LuaStat(@NotNull ASTNode node) {
        super(node);
    }
}
