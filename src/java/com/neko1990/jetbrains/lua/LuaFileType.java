package com.neko1990.jetbrains.lua;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by miaomiao on 2016/10/29.
 */
public class LuaFileType extends LanguageFileType {
    public static final LuaFileType INSTANCE = new LuaFileType();

    private LuaFileType() {
        super(LuaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Lua file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Lua language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "Lua";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return LuaIcons.LUA_FILE_ICON;
    }
}
