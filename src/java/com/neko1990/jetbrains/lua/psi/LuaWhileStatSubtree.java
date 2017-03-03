package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;

/**
 * Created by neko1990 on 2/23/17.
 */
public class LuaWhileStatSubtree extends LuaStat {
    public LuaPsiNode test;
    public LuaBlockSubtree body;

    public LuaWhileStatSubtree(ASTNode node) {
        super(node);
    }
}
