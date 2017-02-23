package com.neko1990.jetbrains.lua;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.antlr.jetbrains.adaptor.lexer.ANTLRLexerAdaptor;
import org.antlr.jetbrains.adaptor.lexer.PSIElementTypeFactory;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import com.neko1990.jetbrains.lua.parser.LuaLexer;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import org.jetbrains.annotations.NotNull;
import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * A highlighter is really just a mapping from token type to
 * some text attributes using {@link #getTokenHighlights(IElementType)}.
 * The reason that it returns an array, TextAttributesKey[], is
 * that you might want to mix the attributes of a few known highlighters.
 * A {@link TextAttributesKey} is just a name for that a theme
 * or IDE skin can set. For example, {@link com.intellij.openapi.editor.DefaultLanguageHighlighterColors#KEYWORD}
 * is the key that maps to what identifiers look like in the editor.
 * To change it, see dialog: Editor > Colors & Fonts > Language Defaults.
 * <p>
 * From <a href="http://www.jetbrains.org/intellij/sdk/docs/reference_guide/custom_language_support/syntax_highlighting_and_error_highlighting.html">doc</a>:
 * "The mapping of the TextAttributesKey to specific attributes used
 * in an editor is defined by the EditorColorsScheme class, and can
 * be configured by the user if the plugin provides an appropriate
 * configuration interface.
 * ...
 * The syntax highlighter returns the {@link TextAttributesKey}
 * instances for each token type which needs special highlighting.
 * For highlighting lexer errors, the standard TextAttributesKey
 * for bad characters HighlighterColors.BAD_CHARACTER can be used."
 */
public class LuaSyntaxHighlighter extends SyntaxHighlighterBase {
    // This Highlighter only provide limited info because it is based on lexer output.
    // For more syntax info , you must use Annotator.
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
    //    public static final TextAttributesKey TEMPLATE_LANGUAGE_COLOR =
    //            createTextAttributesKey("LUA_TEMPLATE_LANGUAGE_COLOR", DefaultLanguageHighlighterColors.TEMPLATE_LANGUAGE_COLOR);
    public static final TextAttributesKey NAME =
            createTextAttributesKey("LUA_ID", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey NUMBER =
            createTextAttributesKey("LUA_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey KEYWORD =
            createTextAttributesKey("LUA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey STRING =
            createTextAttributesKey("LUA_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey BLOCK_COMMENT =
            createTextAttributesKey("LUA_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey LINE_COMMENT =
            createTextAttributesKey("LUA_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey DOC_COMMENT =
            createTextAttributesKey("LUA_DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey OPERATION_SIGN =
            createTextAttributesKey("LUA_OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey BRACES =
            createTextAttributesKey("LUA_BRACES", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey DOT =
            createTextAttributesKey("LUA_DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON =
            createTextAttributesKey("LUA_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA =
            createTextAttributesKey("LUA_COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PARENTHESES =
            createTextAttributesKey("LUA_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACKETS =
            createTextAttributesKey("LUA_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
    //    public static final TextAttributesKey LABEL =
    //            createTextAttributesKey("LUA_LABEL", DefaultLanguageHighlighterColors.LABEL);
    public static final TextAttributesKey CONSTANT =
            createTextAttributesKey("LUA_CONSTANT", DefaultLanguageHighlighterColors.CONSTANT);
    public static final TextAttributesKey LOCAL_VARIABLE =
            createTextAttributesKey("LUA_LOCAL_VARIABLE", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey GLOBAL_VARIABLE =
            createTextAttributesKey("LUA_GLOBAL_VARIABLE", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);
    public static final TextAttributesKey FUNCTION_DECLARATION =
            createTextAttributesKey("LUA_FUNCTION_DECLARATION", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
    public static final TextAttributesKey FUNCTION_CALL =
            createTextAttributesKey("LUA_FUNCTION_CALL", DefaultLanguageHighlighterColors.FUNCTION_CALL);
    public static final TextAttributesKey PARAMETER =
            createTextAttributesKey("LUA_PARAMETER", DefaultLanguageHighlighterColors.PARAMETER);
    //    public static final TextAttributesKey CLASS_NAME =
    //            createTextAttributesKey("LUA_CLASS_NAME", DefaultLanguageHighlighterColors.CLASS_NAME);
    //    public static final TextAttributesKey INTERFACE_NAME =
    //            createTextAttributesKey("LUA_INTERFACE_NAME", DefaultLanguageHighlighterColors.INTERFACE_NAME);
    //    public static final TextAttributesKey CLASS_REFERENCE =
    //            createTextAttributesKey("LUA_CLASS_REFERENCE", DefaultLanguageHighlighterColors.CLASS_REFERENCE);
    //    public static final TextAttributesKey INSTANCE_METHOD =
    //            createTextAttributesKey("LUA_INSTANCE_METHOD", DefaultLanguageHighlighterColors.INSTANCE_METHOD);
    //    public static final TextAttributesKey INSTANCE_FIELD =
    //            createTextAttributesKey("LUA_INSTANCE_FIELD", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
    public static final TextAttributesKey STATIC_METHOD =
        createTextAttributesKey("LUA_STATIC_METHOD", DefaultLanguageHighlighterColors.STATIC_METHOD);
    public static final TextAttributesKey STATIC_FIELD =
            createTextAttributesKey("LUA_STATIC_FIELD", DefaultLanguageHighlighterColors.STATIC_FIELD);
    //    public static final TextAttributesKey DOC_COMMENT_MARKUP =
    //            createTextAttributesKey("LUA_DOC_COMMENT_MARKUP", DefaultLanguageHighlighterColors.DOC_COMMENT_MARKUP);
    //    public static final TextAttributesKey DOC_COMMENT_TAG =
    //            createTextAttributesKey("LUA_DOC_COMMENT_TAG", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG);
    //    public static final TextAttributesKey DOC_COMMENT_TAG_VALUE =
    //            createTextAttributesKey("LUA_DOC_COMMENT_TAG_VALUE", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG_VALUE);
    //    public static final TextAttributesKey VALID_STRING_ESCAPE =
    //            createTextAttributesKey("LUA_VALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
    //    public static final TextAttributesKey INVALID_STRING_ESCAPE =
    //            createTextAttributesKey("LUA_INVALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);
    public static final TextAttributesKey PREDEFINED_SYMBOL =
            createTextAttributesKey("LUA_PREDEFINED_SYMBOL", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    //    public static final TextAttributesKey METADATA =
    //            createTextAttributesKey("LUA_METADATA", DefaultLanguageHighlighterColors.METADATA);
    //    public static final TextAttributesKey MARKUP_TAG =
    //            createTextAttributesKey("LUA_MARKUP_TAG", DefaultLanguageHighlighterColors.MARKUP_TAG);
    //    public static final TextAttributesKey MARKUP_ATTRIBUTE =
    //            createTextAttributesKey("LUA_MARKUP_ATTRIBUTE", DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE);
    //    public static final TextAttributesKey MARKUP_ENTITY =
    //            createTextAttributesKey("LUA_MARKUP_ENTITY", DefaultLanguageHighlighterColors.MARKUP_ENTITY);

    static {
        PSIElementTypeFactory.defineLanguageIElementTypes(
                LuaLanguage.INSTANCE,
                LuaParser.tokenNames,
                LuaParser.ruleNames);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        LuaLexer lexer = new LuaLexer(null);
        return new ANTLRLexerAdaptor(LuaLanguage.INSTANCE, lexer);
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (!(tokenType instanceof TokenIElementType)) return EMPTY_KEYS;
        TokenIElementType myType = (TokenIElementType) tokenType;
        int ttype = myType.getANTLRTokenType();
        TextAttributesKey attrKey;
        switch (ttype) {
            case LuaLexer.NAME:
                attrKey = NAME;
                break;
            case LuaLexer.NORMALSTRING:
            case LuaLexer.CHARSTRING:
            case LuaLexer.LONGSTRING:
                attrKey = STRING;
                break;
            case LuaLexer.INT:
            case LuaLexer.HEX:
            case LuaLexer.FLOAT:
                attrKey = NUMBER;
                break;
            case LuaLexer.SHORT_COMMENT:
                attrKey = LINE_COMMENT;
                break;
            case LuaLexer.LONG_COMMENT:
                attrKey = BLOCK_COMMENT;
                break;
            // SYMBOLS
            case LuaLexer.DOT:
            case LuaLexer.COLON:
                attrKey = DOT;
                break;
            case LuaLexer.LPAREN:
            case LuaLexer.RPAREN:
                attrKey = PARENTHESES;
                break;
            case LuaLexer.LBRACK:
            case LuaLexer.RBRACK:
                attrKey = BRACKETS;
                break;
            case LuaLexer.LBRACE:
            case LuaLexer.RBRACE:
                attrKey = BRACES;
                break;
            case LuaLexer.SEMICO:
                attrKey = SEMICOLON;
                break;
            case LuaLexer.COMMA:
                attrKey = COMMA;
                break;
            // KEYWORDS
            case LuaLexer.TK_LOCAL:
            case LuaLexer.TK_IF:
            case LuaLexer.TK_THEN:
            case LuaLexer.TK_ELSE:
            case LuaLexer.TK_ELSEIF:
            case LuaLexer.TK_WHILE:
            case LuaLexer.TK_REPEAT:
            case LuaLexer.TK_UNTIL:
            case LuaLexer.TK_FOR:
            case LuaLexer.TK_IN:
            case LuaLexer.TK_FUNCTION:
            case LuaLexer.TK_DO:
            case LuaLexer.TK_END:
                attrKey = KEYWORD;
                break;
            case LuaLexer.TRUE:
            case LuaLexer.FALSE:
            case LuaLexer.TK_NIL:
            case LuaLexer.DOTS:
                attrKey = KEYWORD;
                break;
            case LuaLexer.TK_RETURN:
            case LuaLexer.TK_BREAK:
                attrKey = KEYWORD;
                break;
            // OPERATORS
            case LuaLexer.EQUAL:
            case LuaLexer.OP_ADD:
            case LuaLexer.OP_SUB:
            case LuaLexer.OP_MUL:
            case LuaLexer.OP_DIV:
            case LuaLexer.OP_MOD:
            case LuaLexer.OP_POW:
            case LuaLexer.OP_CONCAT:
            case LuaLexer.TK_LT:
            case LuaLexer.TK_LE:
            case LuaLexer.TK_GT:
            case LuaLexer.TK_GE:
            case LuaLexer.TK_EQ:
            case LuaLexer.TK_NEQ:
            case LuaLexer.TK_AND:
            case LuaLexer.TK_OR:
            case LuaLexer.TK_NOT:
            case LuaLexer.TK_LEN:
                attrKey = OPERATION_SIGN;
                break;
            //case LuaLexer.WS:      <-- no need to assign attrKey
            //case LuaLexer.SHEBANG: <-- no need to assign attrKey
            default:
                return EMPTY_KEYS;
        }
        return new TextAttributesKey[]{attrKey};
    }
}
