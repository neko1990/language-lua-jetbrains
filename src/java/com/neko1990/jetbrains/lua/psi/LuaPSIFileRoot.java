package com.neko1990.jetbrains.lua.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.antlr.jetbrains.adaptor.SymtabUtils;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import com.neko1990.jetbrains.lua.LuaIcons;
import com.neko1990.jetbrains.lua.LuaFileType;
import com.neko1990.jetbrains.lua.LuaLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class LuaPSIFileRoot extends PsiFileBase implements ScopeNode {
    public LuaPSIFileRoot(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, LuaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return LuaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "LuaFile: {name: " + getName() + "}";
    }

    @Override
    public Icon getIcon(int flags) {
        return LuaIcons.LUA_FILE_ICON;
    }

	/** Return null since a file scope has no enclosing scope. It is
	 *  not itself in a scope.
	 */
	@Override
	public ScopeNode getContext() {
		return null;
	}

	@Nullable
	@Override
	public PsiElement resolve(PsiNamedElement element) {
		if ( element.getParent() instanceof LuaFunctionNormalCallSubtree) {
			return SymtabUtils.resolve(this, LuaLanguage.INSTANCE,
					element, "/file/chunk/stat/localstat/NAME");
		}
		return SymtabUtils.resolve(this, LuaLanguage.INSTANCE,
		                           element, "/chunk/NAME");
	}
}
