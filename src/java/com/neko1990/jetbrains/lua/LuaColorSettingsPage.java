package com.neko1990.jetbrains.lua;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class LuaColorSettingsPage implements ColorSettingsPage {
	private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
		new AttributesDescriptor("Name", LuaSyntaxHighlighter.NAME),
		new AttributesDescriptor("Keyword", LuaSyntaxHighlighter.KEYWORD),
		new AttributesDescriptor("String", LuaSyntaxHighlighter.STRING),
		new AttributesDescriptor("Line comment", LuaSyntaxHighlighter.LINE_COMMENT),
		new AttributesDescriptor("Block comment", LuaSyntaxHighlighter.BLOCK_COMMENT),
	};

	@Nullable
	@Override
	public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return LuaIcons.LUA_FILE_ICON;
	}

	@NotNull
	@Override
	public SyntaxHighlighter getHighlighter() {
		return new LuaSyntaxHighlighter();
	}

	@NotNull
	@Override
	public String getDemoText() {
		return
			"--[[ block comment ]]\n"+
			"function f(a) {\n"+
			"   -- line comment\n"+
			"   local i = 1\n" +
			"   if (i < #(a)) then\n" +
			"       print(a[i])\n" +
			"   end\n" +
			"   g(\"hi mom\")\n" +
			"end\n" +
			"function g(c) end\n";
	}

	@NotNull
	@Override
	public AttributesDescriptor[] getAttributeDescriptors() {
		return DESCRIPTORS;
	}

	@NotNull
	@Override
	public ColorDescriptor[] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "Lua";
	}
}
