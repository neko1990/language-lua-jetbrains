package com.neko1990.jetbrains.lua;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.neko1990.jetbrains.lua.psi.LuaLocalVarDefSubtree;
import com.neko1990.jetbrains.lua.psi.LuaNameRef;
import org.jetbrains.annotations.NotNull;

/**
 * Created by neko1990 on 3/2/17.
 */
public class LuaReferenceContributor extends PsiReferenceContributor {
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(LuaLocalVarDefSubtree.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
                        return PsiReference.EMPTY_ARRAY;
                    }
                });
    }

}
