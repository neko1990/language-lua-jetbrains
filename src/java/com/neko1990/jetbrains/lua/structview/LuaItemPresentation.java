package com.neko1990.jetbrains.lua.structview;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.neko1990.jetbrains.lua.LuaIcons;
import com.neko1990.jetbrains.lua.LuaLanguage;
import com.neko1990.jetbrains.lua.psi.LuaBlockStatSubtree;
import com.neko1990.jetbrains.lua.psi.LuaBlockSubtree;
import com.neko1990.jetbrains.lua.psi.LuaFunctionDefSubtree;
import com.neko1990.jetbrains.lua.psi.LuaLocalFunctionDefSubtree;
import org.antlr.jetbrains.adaptor.xpath.XPath;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LuaItemPresentation implements ItemPresentation {
	protected final PsiElement element;

	protected LuaItemPresentation(PsiElement element) {
		this.element = element;
	}

	@Nullable
	@Override
	public Icon getIcon(boolean unused) {
		if (element instanceof LuaBlockStatSubtree) {
			return LuaIcons.LUA_FILE_ICON;
		} else if (element instanceof LuaFunctionDefSubtree) {
			return LuaIcons.LUA_FIELD_ICON;
		} else if (element instanceof LuaLocalFunctionDefSubtree) {
			return LuaIcons.LUA_FUNCTION_ICON;
		} else {
			return LuaIcons.LUA_PROPERTY_ICON;
		}
	}

	@Nullable
	@Override
	public String getPresentableText() {
		if (element instanceof LuaBlockSubtree) {
			return "LuaBlockSubtree";
		} else if (element instanceof LuaFunctionDefSubtree) {
			for (PsiElement el : XPath.findAll(LuaLanguage.INSTANCE, element, "/functionstat/functionname")) {
				ASTNode node = el.getNode();
				return node.getText();
			}
			return "LuaFunctionDefSubtree";
		} else if (element instanceof LuaLocalFunctionDefSubtree) {
			for (PsiElement el : XPath.findAll(LuaLanguage.INSTANCE, element, "/localfunctionstat/NAME")) {
				ASTNode node = el.getNode();
				return node.getText();
			}
			return "LuaLocalFunctionDefSubtree";
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
