package com.neko1990.jetbrains.lua.structview;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.neko1990.jetbrains.lua.psi.BlockSubtree;
import com.neko1990.jetbrains.lua.psi.FunctionSubtree;
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

	private TreeElement[] getChildrenByPrefix(String prefix) {
		Collection<? extends PsiElement> funcs      = XPath.findAll(LuaLanguage.INSTANCE, element, prefix + "/chunk/stat/functionstat");
		Collection<? extends PsiElement> localfuncs = XPath.findAll(LuaLanguage.INSTANCE, element, prefix + "/chunk/stat/localfunctionstat");
		Collection<? extends PsiElement> upvalues   = XPath.findAll(LuaLanguage.INSTANCE, element, prefix + "/chunk/stat/localstat/namelist/NAME");
		int size = funcs.size() + upvalues.size() +localfuncs.size();
		List<TreeElement> treeElements = new ArrayList<>(size);
		for (PsiElement el : funcs) {
			treeElements.add(new LuaStructureViewElement(el));
		}
		for (PsiElement el : localfuncs) {
			treeElements.add(new LuaStructureViewElement(el));
		}
		for (PsiElement el : upvalues) {
			treeElements.add(new LuaStructureViewElement(el));
		}
		return treeElements.toArray(new TreeElement[size]);
	}

	@NotNull
	@Override
	public TreeElement[] getChildren() {
		if ( element instanceof LuaPSIFileRoot ) {
			return getChildrenByPrefix("/file");
		} else if (element instanceof BlockSubtree){
			return getChildrenByPrefix("/block");
		} else if (element instanceof FunctionSubtree) {
			return getChildrenByPrefix("/functionstat/funcbody");
		}
		return new TreeElement[0];
	}
}
