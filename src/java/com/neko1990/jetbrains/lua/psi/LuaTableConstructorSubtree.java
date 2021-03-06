package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.neko1990.jetbrains.lua.LuaLanguage;
import org.antlr.jetbrains.adaptor.SymtabUtils;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaTableConstructorSubtree extends LuaPsiNode {
    public List<LuaPsiNode>  records; // recfields
    public List<LuaPsiNode> listfields;

    public LuaTableConstructorSubtree(@NotNull ASTNode node) {
        super(node);
    }
}
