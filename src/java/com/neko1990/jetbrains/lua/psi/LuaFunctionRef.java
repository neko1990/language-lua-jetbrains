package com.neko1990.jetbrains.lua.psi;

import com.intellij.psi.PsiElement;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;

/** A reference object associated with (referring to) a LuaNamePSILeafNode
 *  underneath a call_expr rule subtree root.
 */
public class LuaFunctionRef extends LuaNameRef  {
	public LuaFunctionRef(@NotNull LuaNamePSILeafNode element) {
		super(element);
	}

	@Override
	public boolean isDefSubtree(PsiElement def) {
		return (def instanceof LuaFunctionDefSubtree) || (def instanceof LuaLocalFunctionDefSubtree);
	}
}
