package com.neko1990.jetbrains.lua.structview;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class LuaStructureViewRootElement extends LuaStructureViewElement {
	public LuaStructureViewRootElement(PsiFile element) {
		super(element);
	}

	@NotNull
	@Override
	public ItemPresentation getPresentation() {
		return new LuaRootPresentation((PsiFile)element);
	}
}
