package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.neko1990.jetbrains.lua.LuaLanguage;
import com.neko1990.jetbrains.lua.luatypes.Proto;
import org.antlr.jetbrains.adaptor.SymtabUtils;
import org.antlr.jetbrains.adaptor.psi.IdentifierDefSubtree;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaLocalFunctionDefSubtree extends IdentifierDefSubtree implements ScopeNode {
    public Proto proto;

    public LuaLocalFunctionDefSubtree(@NotNull ASTNode node) {
        super(node, node.getElementType() );
        // proto = new Proto(node);
    }

    @Nullable
    @Override
    public PsiElement resolve(PsiNamedElement element) {
        return SymtabUtils.resolve(this, LuaLanguage.INSTANCE,
                element, "/localfunctionstat/funcbody/param/NAME");
    }
}
