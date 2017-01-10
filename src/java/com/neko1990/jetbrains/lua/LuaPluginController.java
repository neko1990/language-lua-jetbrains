package com.neko1990.jetbrains.lua;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Created by miaomiao on 2016/10/29.
 */
public class LuaPluginController implements ProjectComponent {
    public static final String PLUGIN_ID = "com.neko1990.jetbrains.lua";
    public static final Logger LOG = Logger.getInstance("LuaPluginController");

    public Project project;
    public boolean projectIsClosed = false;

    public LuaPluginController(Project project) {
        this.project = project;
    }

    @Override
    public void projectClosed() {
        LOG.info("projectClosed " + project.getName());
        //synchronized ( shutdownLock ) { // They should be called from EDT only so no lock
        projectIsClosed = true;
        project = null;
    }

    @Override
    public void projectOpened() {
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID));
        String version = "0.0.1";
        if ( plugin!=null ) {
            version = plugin.getVersion();
        }
        LOG.info("Lua Plugin version "+version+", Java version "+ SystemInfo.JAVA_VERSION);
    }

    @Override
    public void initComponent() { }

    @Override
    public void disposeComponent() { }

    @NotNull
    @Override
    public String getComponentName() {
        return "LuaLanguage.ProjectComponent";
    }
}
