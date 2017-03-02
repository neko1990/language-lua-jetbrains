package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;

/**
 * Created by neko1990 on 2/23/17.
 */
public class LuaIfStatSubtree extends LuaStat {
    public LuaIfStatSubtree(ASTNode node) {
        super(node);
    }
}
