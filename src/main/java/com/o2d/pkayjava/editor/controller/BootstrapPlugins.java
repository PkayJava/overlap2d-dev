/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.o2d.pkayjava.editor.controller;

import com.commons.plugins.O2DPlugin;
import com.puremvc.patterns.command.SimpleCommand;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.PluginManager;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import net.mountainblade.modular.Module;
import net.mountainblade.modular.ModuleManager;
import net.mountainblade.modular.impl.DefaultModuleManager;

import java.io.File;
import java.util.Collection;


/**
 * Created by azakhary on 7/24/2015.
 */
public class BootstrapPlugins extends SimpleCommand {

    public void execute(Notification notification) {
        super.execute(notification);
        facade = Overlap2DFacade.getInstance();

        PluginManager pluginManager = new PluginManager();
        facade.registerProxy(pluginManager);

        ProjectManager projectManager = facade.retrieveProxy(ProjectManager.NAME);
        File pluginDir = null;
        if (projectManager != null) {
            pluginDir = new File(projectManager.getRootPath() + File.separator + "plugins");
        }

        ModuleManager manager = new DefaultModuleManager();

        Collection<Module> loadedPlugins = null;
        if (pluginDir != null) {
            loadedPlugins = manager.loadModules(pluginDir);
        }

        if (loadedPlugins != null) {
            for (Module module : loadedPlugins) {
                try {
                    pluginManager.initPlugin((O2DPlugin) module.getClass().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (manager != null) {
            loadedPlugins = manager.loadModules("com.o2d.pkayjava.editor.plugins");
        }
        if (loadedPlugins != null) {
            for (Module module : loadedPlugins) {
                try {
                    pluginManager.initPlugin((O2DPlugin) module.getClass().newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
