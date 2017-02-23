package com.neko1990.jetbrains.lua;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.neko1990.jetbrains.lua.parser.LuaVisitor;
import com.neko1990.jetbrains.lua.psi.LuaFunctionDefSubtree;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

/**
 * Created by neko1990 on 2/13/17.
 *
 * Annotator helps highlight and annotate any code based on specific rules.
 */
public class LuaAnnotator extends PsiElementVisitor implements Annotator {
    private AnnotationHolder myHolder = null;
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof LuaFunctionDefSubtree) {
            myHolder = holder;
            ((ANTLRPsiNode) element).accept(this);
            myHolder = null;
        }
    }
}
