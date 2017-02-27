package com.neko1990.jetbrains.lua;

import com.intellij.lang.ASTNode;
import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.neko1990.jetbrains.lua.parser.LuaLexer;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import com.neko1990.jetbrains.lua.psi.LuaFunctionDefSubtree;
import com.neko1990.jetbrains.lua.psi.LuaNamePSILeafNode;
import com.neko1990.jetbrains.lua.psi.LuaLocalFunctionDefSubtree;
import com.neko1990.jetbrains.lua.psi.LuaLocalVarDefSubtree;
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.neko1990.jetbrains.lua.parser.LuaParser.*;

/**
 * Created by neko1990 on 1/11/17.
 */
public class LuaFindUsagesProvider implements FindUsagesProvider {
    /** Is "find usages" meaningful for a kind of definition subtree? */
    public static TokenIElementType NAME;
    static {
        PSIElementTypeFactory.defineLanguageIElementTypes(LuaLanguage.INSTANCE,
                LuaParser.tokenNames,
                LuaParser.ruleNames);
        List<TokenIElementType> tokenIElementTypes =
                PSIElementTypeFactory.getTokenIElementTypes(LuaLanguage.INSTANCE);
        NAME = tokenIElementTypes.get(LuaLexer.NAME);
    }
    private boolean canFindUsageDirectlyFor(ASTNode node) {
        return node instanceof LuaNamePSILeafNode ||
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
    public WordsScanner getWordsScanner() {
        ANTLRLexerAdaptor lexer = new ANTLRLexerAdaptor(LuaLanguage.INSTANCE, new LuaLexer(null));
        return new DefaultWordsScanner( lexer ,
                TokenSet.create(  lexer.getTokenType(LuaLexer.NAME)),
                TokenSet.create(  lexer.getTokenType(LuaLexer.INT)) ,
                TokenSet.create(
                        lexer.getTokenType(LuaLexer.SHORT_COMMENT) ,
                        lexer.getTokenType(LuaLexer.LONG_COMMENT)
                )
        ) {{
            setMayHaveFileRefsInLiterals(true);
        }};
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
        ANTLRPsiNode parent = (ANTLRPsiNode)element.getParent();
        RuleIElementType elType = (RuleIElementType)parent.getNode().getElementType();
        switch ( elType.getRuleIndex() ) {
            case RULE_functioncall:
                return "function";
            case RULE_primaryexp:
                return "variable";
            case RULE_param:
                return "parameter";
            case RULE_stat:
            case RULE_expr:
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
