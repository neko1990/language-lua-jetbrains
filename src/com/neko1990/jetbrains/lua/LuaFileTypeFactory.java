package com.neko1990.jetbrains.lua;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;


/**
 * Created by miaomiao on 2016/10/29.
 */
public class LuaFileTypeFactory extends FileTypeFactory{
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(LuaFileType.INSTANCE, "lua");
    }
}
