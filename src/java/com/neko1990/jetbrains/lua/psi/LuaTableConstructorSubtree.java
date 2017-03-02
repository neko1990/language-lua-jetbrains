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

/**
 * Created by neko1990 on 1/13/17.
 */
public class LuaTableConstructorSubtree extends ANTLRPsiNode implements ScopeNode {
    public LuaTableConstructorSubtree(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public PsiElement resolve(PsiNamedElement element) {
        PsiElement result =  SymtabUtils.resolve(this, LuaLanguage.INSTANCE,element, "/constructor/recfield/NAME");
        if (result != null) {
            return result;
        } else {
            return SymtabUtils.resolve(this, LuaLanguage.INSTANCE,element, "/constructor/listfield/NAME");
        }
    }
}
