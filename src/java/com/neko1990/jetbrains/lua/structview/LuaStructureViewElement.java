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
import org.jetbrains.jps.builders.logging.BuildLoggingManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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
			Collection<? extends PsiElement> funcs = XPath.findAll(LuaLanguage.INSTANCE, element, "/file/chunk/stat/functionstat");
			Collection<? extends PsiElement> localfuncs = XPath.findAll(LuaLanguage.INSTANCE, element, "/file/chunk/stat/localfunctionstat");
			Collection<? extends PsiElement> upvalues = XPath.findAll(LuaLanguage.INSTANCE, element, "/file/chunk/stat/localstat/namelist/NAME");
			List<TreeElement> treeElements = new ArrayList<>(funcs.size() + upvalues.size() +localfuncs.size());
			for (PsiElement el : funcs) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			for (PsiElement el : localfuncs) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			for (PsiElement el : upvalues) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			return treeElements.toArray(new TreeElement[funcs.size()]);
		} else if (element instanceof BlockSubtree){
			Collection<? extends PsiElement> funcs = XPath.findAll(LuaLanguage.INSTANCE, element, "/chunk/stat/functionstat");
			Collection<? extends PsiElement> localfuncs = XPath.findAll(LuaLanguage.INSTANCE, element, "/chunk/stat/localfunctionstat");
			Collection<? extends PsiElement> upvalues = XPath.findAll(LuaLanguage.INSTANCE, element, "/chunk/stat/localstat/namelist/NAME");
			List<TreeElement> treeElements = new ArrayList<>(funcs.size() + upvalues.size() +localfuncs.size());
			for (PsiElement el : funcs) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			for (PsiElement el : localfuncs) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			for (PsiElement el : upvalues) {
				treeElements.add(new LuaStructureViewElement(el));
			}
		} else if (element instanceof FunctionSubtree) {
			Collection<? extends PsiElement> funcs = XPath.findAll(LuaLanguage.INSTANCE, element, "/funcbody/chunk/stat/functionstat");
			Collection<? extends PsiElement> localfuncs = XPath.findAll(LuaLanguage.INSTANCE, element, "/funcbody/chunk/stat/localfunctionstat");
			Collection<? extends PsiElement> upvalues = XPath.findAll(LuaLanguage.INSTANCE, element, "/funcbody/chunk/stat/localstat/namelist/NAME");
			List<TreeElement> treeElements = new ArrayList<>(funcs.size() + upvalues.size() +localfuncs.size());
			for (PsiElement el : funcs) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			for (PsiElement el : localfuncs) {
				treeElements.add(new LuaStructureViewElement(el));
			}
			for (PsiElement el : upvalues) {
				treeElements.add(new LuaStructureViewElement(el));
			}
		}
		return new TreeElement[0];
	}
}
