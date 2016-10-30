package com.neko1990.jetbrains.lua.psi;

import com.intellij.psi.PsiElement;
import com.neko1990.jetbrains.lua.psi.FunctionSubtree;
import org.jetbrains.annotations.NotNull;

/** A reference object associated with (referring to) a IdentifierPSINode
 *  underneath a call_expr rule subtree root.
 */
public class FunctionRef extends LuaElementRef {
	public FunctionRef(@NotNull IdentifierPSINode element) {
		super(element);
	}

	@Override
	public boolean isDefSubtree(PsiElement def) {
		return def instanceof FunctionSubtree;
	}
}
