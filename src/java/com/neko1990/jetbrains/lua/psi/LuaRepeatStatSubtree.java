package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;

/**
 * Created by neko1990 on 2/23/17.
 */
public class LuaRepeatStatSubtree extends ANTLRPsiNode {
    public LuaRepeatStatSubtree(ASTNode node) {
        super(node);
    }
}