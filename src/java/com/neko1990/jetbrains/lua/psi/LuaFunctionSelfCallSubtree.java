package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.neko1990.jetbrains.lua.LuaParserDefinition;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;

/**
 * Created by neko1990 on 2/26/17.
 */
public class LuaFunctionSelfCallSubtree extends ANTLRPsiNode {
    public String myMethodName ;
    public LuaFunctionSelfCallSubtree(ASTNode node) {
        super(node);
        myMethodName = getNode().getFirstChildNode().getTreeNext().getText();

    }

    public String getArgDesc() {
        ASTNode funcargs_node = getNode().getLastChildNode();
        IElementType funcargs_node_ietype = funcargs_node.getFirstChildNode().getElementType();
        if ( funcargs_node_ietype instanceof TokenIElementType) {
            if (((TokenIElementType) funcargs_node_ietype ).getANTLRTokenType() == LuaParser.LPAREN) {
                funcargs_node.getFirstChildNode();
                return "Call With Arguments" ;
            } else {
                return "Error: Should Be `(' ";
            }
        }
        if (funcargs_node_ietype instanceof RuleIElementType) {
            RuleIElementType ruleElType = (RuleIElementType) funcargs_node_ietype;
            switch ( ruleElType.getRuleIndex() ) {
                case LuaParser.RULE_constructor:
                    return "Call With Table Constructor";
                case LuaParser.RULE_string:
                    return "Args: \"" + funcargs_node.getText() + "\"";
                default:
                    return "Error: Unknown Rule";
            }
        }
        return "Error: Unknown Arg Desc";
    }

    @Override
    public String getName() {
        return "LuaMethodCall:[" + myMethodName + " Arg:"+ getArgDesc() + "]";
    }
}
