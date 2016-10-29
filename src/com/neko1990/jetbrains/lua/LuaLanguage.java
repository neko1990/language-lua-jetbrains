package com.neko1990.jetbrains.lua;

import com.intellij.lang.Language;

/**
 * Created by miaomiao on 2016/10/29.
 */
public class LuaLanguage extends Language {
    public static final LuaLanguage INSTANCE = new LuaLanguage();

    private LuaLanguage() {
        super("Lua");
    }
}
