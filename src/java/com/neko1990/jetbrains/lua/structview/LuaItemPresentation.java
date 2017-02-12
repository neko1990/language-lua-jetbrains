package com.neko1990.jetbrains.lua.structview;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.neko1990.jetbrains.lua.LuaIcons;
import com.neko1990.jetbrains.lua.LuaLanguage;
import com.neko1990.jetbrains.lua.psi.BlockSubtree;
import com.neko1990.jetbrains.lua.psi.FunctionSubtree;
import com.neko1990.jetbrains.lua.psi.LocalFunctionSubtree;
import org.antlr.jetbrains.adaptor.lexer.RuleIElementType;
import org.antlr.jetbrains.adaptor.xpath.XPath;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

public class LuaItemPresentation implements ItemPresentation {
	protected final PsiElement element;

	protected LuaItemPresentation(PsiElement element) {
		this.element = element;
	}

	@Nullable
	@Override
	public Icon getIcon(boolean unused) {
		if (element instanceof BlockSubtree) {
			return LuaIcons.LUA_FILE_ICON;
		} else if (element instanceof FunctionSubtree) {
			return LuaIcons.LUA_FIELD_ICON;
		} else if (element instanceof LocalFunctionSubtree) {
			return LuaIcons.LUA_FUNCTION_ICON;
		} else {
			return LuaIcons.LUA_PROPERTY_ICON;
		}
	}

	@Nullable
	@Override
	public String getPresentableText() {
		if (element instanceof BlockSubtree) {
			return "BlockSubtree";
		} else if (element instanceof FunctionSubtree) {
			for (PsiElement el : XPath.findAll(LuaLanguage.INSTANCE, element, "/functionstat/functionname")) {
				ASTNode node = el.getNode();
				return node.getText();
			}
			return "FunctionSubtree";
		} else if (element instanceof LocalFunctionSubtree) {
			for (PsiElement el : XPath.findAll(LuaLanguage.INSTANCE, element, "/localfunctionstat/NAME")) {
				ASTNode node = el.getNode();
				return node.getText();
			}
			return "LocalFunctionSubtree";
		}
		else {
			ASTNode node = element.getNode();
			return node.getText();
		}
	}

	@Nullable
	@Override
	public String getLocationString() {
		return null;
	}
}
