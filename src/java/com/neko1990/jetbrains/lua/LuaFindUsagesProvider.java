package com.neko1990.jetbrains.lua;

import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import com.neko1990.jetbrains.lua.psi.FunctionSubtree;
import com.neko1990.jetbrains.lua.psi.IdentifierPSINode;
import com.neko1990.jetbrains.lua.psi.VardefSubtree;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by neko1990 on 1/11/17.
 */
public class LuaFindUsagesProvider implements FindUsagesProvider {
    /** Is "find usages" meaningful for a kind of definition subtree? */
    @Override
    public boolean canFindUsagesFor(PsiElement psiElement) {
        return psiElement instanceof IdentifierPSINode || // the case where we highlight the ID in def subtree itself
                psiElement instanceof FunctionSubtree ||   // remaining cases are for resolve() results
                psiElement instanceof VardefSubtree;
    }

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return null; // null implies use SimpleWordScanner default
    }

    @Nullable
    @Override
    public String getHelpId(PsiElement psiElement) {
        return null;
    }

    /** What kind of thing is the ID node? Can group by in "Find Usages" dialog */
    @NotNull
    @Override
    public String getType(PsiElement element) {
        // The parent of an ID node will be a RuleIElementType:
        // function, vardef, formal_arg, statement, expr, call_expr, primary
        ANTLRPsiNode parent = (ANTLRPsiNode)element.getParent();
        RuleIElementType elType = (RuleIElementType)parent.getNode().getElementType();
        switch ( elType.getRuleIndex() ) {
            case LuaParser.RULE_functioncall:
                return "function";
            case LuaParser.RULE_primaryexp:
                return "variable";
            case LuaParser.RULE_param:
                return "parameter";
            case LuaParser.RULE_stat:
            case LuaParser.RULE_expr:
                return "variable";
        }
        return "";
    }

    @NotNull
    @Override
    public String getDescriptiveName(PsiElement element) {
        return element.getText();
    }

    @NotNull
    @Override
    public String getNodeText(PsiElement element, boolean useFullName) {
        String text = element.getText();
        return text;
    }
}
