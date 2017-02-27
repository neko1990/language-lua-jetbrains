package com.neko1990.jetbrains.lua.psi;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.tree.IElementType;
import com.neko1990.jetbrains.lua.parser.LuaParser;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.lexer.TokenIElementType;
import org.antlr.jetbrains.adaptor.psi.ANTLRPsiNode;
import org.jetbrains.annotations.NotNull;

public class LuaFunctionNormalCallSubtree extends ANTLRPsiNode {
	public LuaFunctionNormalCallSubtree(@NotNull ASTNode node) {
		super(node);
	}

	public String getArgDesc() {
		ASTNode funcargs_node = getNode().getFirstChildNode();
		IElementType funcargs_node_ietype = funcargs_node.getFirstChildNode().getElementType();
		if ( funcargs_node_ietype instanceof TokenIElementType ) {
			if (((TokenIElementType) funcargs_node_ietype ).getANTLRTokenType() == LuaParser.LPAREN) {
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
		return "LuaFunctionCall:[" + getArgDesc() + "]";
	}
}
