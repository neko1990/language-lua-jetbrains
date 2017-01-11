package com.neko1990.jetbrains.lua.structview;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.jetbrains.adaptor.xpath.XPath;
import com.neko1990.jetbrains.lua.LuaLanguage;
import com.neko1990.jetbrains.lua.psi.LuaPSIFileRoot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LuaStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
	protected final PsiElement element;

	public LuaStructureViewElement(PsiElement element) {
		this.element = element;
	}

	@Override
	public Object getValue() {
		return element;
	}

	@Override
	public void navigate(boolean requestFocus) {
		if (element instanceof NavigationItem ) {
			((NavigationItem) element).navigate(requestFocus);
		}
	}

	@Override
	public boolean canNavigate() {
		return element instanceof NavigationItem &&
			   ((NavigationItem)element).canNavigate();
	}

	@Override
	public boolean canNavigateToSource() {
		return element instanceof NavigationItem &&
			   ((NavigationItem)element).canNavigateToSource();
	}

	@NotNull
	@Override
	public String getAlphaSortKey() {
		String s = element instanceof PsiNamedElement ? ((PsiNamedElement) element).getName() : null;
		if ( s==null ) return "unknown key";
		return s;
	}

	@NotNull
	@Override
	public ItemPresentation getPresentation() {
		return new LuaItemPresentation(element);
	}

	@NotNull
	@Override
	public TreeElement[] getChildren() {
		if ( element instanceof LuaPSIFileRoot ) {
			Collection<? extends PsiElement> funcs = XPath.findAll(LuaLanguage.INSTANCE, element, "//functionname/NAME");
			Collection<? extends PsiElement> vars = XPath.findAll(LuaLanguage.INSTANCE, element, "//stat/NAME");
			List<TreeElement> treeElements = new ArrayList<>(funcs.size() + vars.size());
			for (PsiElement el : funcs) {
				treeElements.add(new LuaStructureViewElement(el.getParent()));
			}
			for (PsiElement el : vars) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			return treeElements.toArray(new TreeElement[funcs.size()]);
		}
		return new TreeElement[0];
	}
}
