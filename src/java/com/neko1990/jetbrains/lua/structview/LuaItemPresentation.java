package com.neko1990.jetbrains.lua.structview;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.neko1990.jetbrains.lua.LuaIcons;
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
		return LuaIcons.ACTION_ICON;
	}

	@Nullable
	@Override
	public String getPresentableText() {
		ASTNode node = element.getNode();
		return node.getText();
	}

	@Nullable
	@Override
	public String getLocationString() {
		return null;
	}
}
