package com.neko1990.jetbrains.lua.structview;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.neko1990.jetbrains.lua.psi.LuaBlockSubtree;
import com.neko1990.jetbrains.lua.psi.LuaFunctionDefSubtree;
import com.neko1990.jetbrains.lua.psi.LuaPSIFileRoot;
import org.jetbrains.annotations.NotNull;

public class LuaStructureViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
	public LuaStructureViewModel(LuaPSIFileRoot root) {
		super(root, new LuaStructureViewRootElement(root));
	}

	@NotNull
	public Sorter[] getSorters() {
		return new Sorter[] {Sorter.ALPHA_SORTER};
	}

	@Override
	public boolean isAlwaysLeaf(StructureViewTreeElement element) {
		return !isAlwaysShowsPlus(element);
	}

	@Override
	public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
		Object value = element.getValue();
		LuaStructureViewElement lua_element = (LuaStructureViewElement) element;
		return (value instanceof LuaPSIFileRoot)
				|| ((value instanceof LuaBlockSubtree) && (lua_element.getChildren().length > 0))
				|| ((value instanceof LuaFunctionDefSubtree) && (lua_element.getChildren().length > 0))
				;
	}
}
