package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.Nullable;

/**
 * Created by neko1990 on 3/1/17.
 */
public class LuaChunkSubTree extends ANTLRPsiNode implements ScopeNode {
    public LuaChunkSubTree(ASTNode node) {
        super(node);
    }

    public LuaStat[] getStats() {return (LuaStat[]) getChildren();}

    @Nullable
    @Override
    public PsiElement resolve(PsiNamedElement element) {
        return null;
    }
}
