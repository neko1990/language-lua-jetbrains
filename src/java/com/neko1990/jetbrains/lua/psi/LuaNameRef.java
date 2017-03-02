package com.neko1990.jetbrains.lua.psi;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import org.antlr.jetbrains.adaptor.psi.ScopeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class LuaNameRef extends PsiReferenceBase<LuaNamePSILeafNode> {
	public LuaNameRef(@NotNull LuaNamePSILeafNode element) {
		super(element, new TextRange(0, element.getText().length()));
	}

	@NotNull
	@Override
	public Object[] getVariants() {
		return new Object[0];
	}

	/** Change the REFERENCE's NAME node (not the targeted def's NAME node)
	 *  to reflect a rename.
	 *
	 *  Without this method, we get an error ("Cannot find manipulator...").
	 *
	 *  getElement() refers to the identifier node that references the definition.
	 */
	@Override
	public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
		return myElement.setName(newElementName);
	}

	/** Resolve a reference to the definition subtree (subclass of
	 *  IdentifierDefSubtree), do not resolve to the NAME child of that
	 *  definition subtree root.
	 */
	@Nullable
	@Override
	public PsiElement resolve() {
		ScopeNode scope = (ScopeNode)myElement.getContext();
		if ( scope==null ) return null;

		return scope.resolve(myElement);
	}

	@Override
	public boolean isReferenceTo(PsiElement def) {
		String refName = myElement.getName();
		if ( def instanceof LuaNamePSILeafNode && isDefSubtree(def.getParent()) ) {
			def = def.getParent();
		}
		if ( isDefSubtree(def) ) {
			PsiElement id = ((PsiNameIdentifierOwner)def).getNameIdentifier();
			String defName = id!=null ? id.getText() : null;
			return refName!=null && defName!=null && refName.equals(defName);
		}
		return false;
	}

	/** Is the targeted def a subtree associated with this ref's kind of node?
	 *  E.g., for a variable def, this should return true for LuaLocalVarDefSubtree.
	 */
	public abstract boolean isDefSubtree(PsiElement def);
}
