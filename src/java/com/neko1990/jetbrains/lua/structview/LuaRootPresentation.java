package com.neko1990.jetbrains.lua.structview;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiFile;
import com.neko1990.jetbrains.lua.LuaIcons;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LuaRootPresentation implements ItemPresentation {
	protected final PsiFile element;

	protected LuaRootPresentation(PsiFile element) {
		this.element = element;
	}

	@Nullable
	@Override
	public Icon getIcon(boolean unused) {
		return LuaIcons.LUA_FILE_ICON;
	}

	@Nullable
	@Override
	public String getPresentableText() {
		return element.getVirtualFile().getNameWithoutExtension();
	}

	@Nullable
	@Override
	public String getLocationString() {
		return null;
	}
}
