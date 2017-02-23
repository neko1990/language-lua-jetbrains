package com.neko1990.jetbrains.lua;

import com.intellij.lang.ASTNode;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import com.neko1990.jetbrains.lua.psi.LuaFunctionDefSubtree;
import com.neko1990.jetbrains.lua.psi.IdentifierPSINode;
import com.neko1990.jetbrains.lua.psi.LuaLocalFunctionDefSubtree;
import com.neko1990.jetbrains.lua.psi.LuaLocalVarDefSubtree;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by neko1990 on 1/11/17.
 */
public class LuaFindUsagesProvider implements FindUsagesProvider {
    /** Is "find usages" meaningful for a kind of definition subtree? */
    private boolean canFindUsageDirectlyFor(ASTNode node) {
        return node instanceof IdentifierPSINode ||
                node instanceof LuaFunctionDefSubtree ||
                node instanceof LuaLocalFunctionDefSubtree ||
                node instanceof LuaLocalVarDefSubtree;
    }
    @Override
    public boolean canFindUsagesFor(PsiElement psiElement) {
        ASTNode node = psiElement.getNode();
        return canFindUsageDirectlyFor(node);
    }

    @Nullable
    @Override
    public WordsScanner getWordsScanner() { return null; }

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
