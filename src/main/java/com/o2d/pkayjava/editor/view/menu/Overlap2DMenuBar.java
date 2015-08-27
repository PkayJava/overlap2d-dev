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

package com.o2d.pkayjava.editor.view.menu;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.SystemUtils;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.o2d.pkayjava.editor.data.manager.PreferencesManager;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.event.MenuItemListener;
import com.o2d.pkayjava.runtime.data.SceneVO;
import com.o2d.pkayjava.editor.view.ui.widget.CustomMenu;
import com.o2d.pkayjava.editor.view.ui.widget.CustomMenuBar;

public class Overlap2DMenuBar extends com.o2d.pkayjava.editor.view.ui.widget.CustomMenuBar {

    public static final String prefix = "com.uwsoft.editor.view.Overlap2DMenuBar";

    public static final String FILE_MENU = prefix + ".FILE_MENU";
    public static final String NEW_PROJECT = prefix + ".NEW_PROJECT";
    public static final String OPEN_PROJECT = prefix + ".OPEN_PROJECT";
    public static final String SAVE_PROJECT = prefix + ".SAVE_PROJECT";
    public static final String IMPORT_TO_LIBRARY = prefix + ".IMPORT_TO_LIBRARY";
    public static final String RECENT_PROJECTS = prefix + ".RECENT_PROJECTS";
    public static final String CLEAR_RECENTS = prefix + ".CLEAR_RECENTS";
    public static final String EXPORT = prefix + ".EXPORT";
    public static final String EXPORT_SETTINGS = prefix + ".EXPORT_SETTINGS";
    public static final String EXIT = prefix + ".EXIT";
    public static final String NEW_SCENE = prefix + ".NEW_SCENE";
    public static final String SELECT_SCENE = prefix + ".SELECT_SCENE";
    public static final String DELETE_CURRENT_SCENE = prefix + ".DELETE_CURRENT_SCENE";
    //
    public static final String EDIT_MENU = prefix + ".EDIT_MENU";
    public static final String CUT = prefix + ".CUT";
    public static final String COPY = prefix + ".COPY";
    public static final String PAST = prefix + ".PAST";
    public static final String UNDO = prefix + ".UNDO";
    public static final String REDO = prefix + ".REDO";
    //

    public static final String WINDOW_MENU = prefix + ".WINDOW_MENU";
    public static final String SPRITE_ANIMATIONS_EDITOR_OPEN = prefix + ".SPRITE_ANIMATIONS_EDITOR_OPEN";
    public static final String CUSTOM_VARIABLES_EDITOR_OPEN = prefix + ".CUSTOM_VARIABLES_EDITOR_OPEN";

    public static final String RECENT_LIST_MODIFIED = prefix + ".RECENT_LIST_MODIFIED";


    private static final String TAG = Overlap2DMenuBar.class.getCanonicalName();
    private final FileMenu fileMenu;
    private final String maskKey;
    private final EditMenu editMenu;
    private final WindowMenu windowMenu;
    private final com.o2d.pkayjava.editor.Overlap2DFacade facade;

    public Overlap2DMenuBar() {
        facade = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance();
        maskKey = SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_MAC ? "Cmd" : "Ctrl";
        fileMenu = new FileMenu();
        editMenu = new EditMenu();
        windowMenu = new WindowMenu();
//        getTable().debug();
        addMenu(fileMenu);
        addMenu(editMenu);
        addMenu(windowMenu);
        setProjectOpen(false);
    }


    public void addScenes(ArrayList<SceneVO> scenes) {
        fileMenu.addScenes(scenes);
    }

    public void reInitScenes(ArrayList<SceneVO> scenes) {
        fileMenu.reInitScenes(scenes);
    }

    public void reInitRecent(ArrayList<String> paths) {
        fileMenu.reInitRecent(paths);
    }

    public void setProjectOpen(boolean open) {
        fileMenu.setProjectOpen(open);
        editMenu.setProjectOpen(open);
        windowMenu.setProjectOpen(open);
    }

    private class WindowMenu extends O2DMenu {


        private final MenuItem customVars;
        private final MenuItem animations;

        public WindowMenu() {
            super("Window");
            customVars = new MenuItem("Custom Variables", new com.o2d.pkayjava.editor.event.MenuItemListener(CUSTOM_VARIABLES_EDITOR_OPEN, null, WINDOW_MENU));
            animations = new MenuItem("Sprite Animations", new com.o2d.pkayjava.editor.event.MenuItemListener(SPRITE_ANIMATIONS_EDITOR_OPEN, null, WINDOW_MENU));
            addItem(customVars);
            addItem(animations);
        }

        public void setProjectOpen(boolean open) {
            customVars.setDisabled(!open);
            animations.setDisabled(!open);
        }

    }

    private class EditMenu extends O2DMenu {


        private final MenuItem cut;
        private final MenuItem copy;
        private final MenuItem paste;
        private final MenuItem undo;
        private final MenuItem redo;

        public EditMenu() {
            super("Edit");
            cut = new MenuItem("Cut", new com.o2d.pkayjava.editor.event.MenuItemListener(CUT, null, EDIT_MENU)).setShortcut(maskKey + " + X");
            copy = new MenuItem("Copy", new com.o2d.pkayjava.editor.event.MenuItemListener(COPY, null, EDIT_MENU)).setShortcut(maskKey + " + C");
            paste = new MenuItem("Paste", new com.o2d.pkayjava.editor.event.MenuItemListener(PAST, null, EDIT_MENU)).setShortcut(maskKey + " + P");
            undo = new MenuItem("Undo", new com.o2d.pkayjava.editor.event.MenuItemListener(UNDO, null, EDIT_MENU)).setShortcut(maskKey + " + Z");
            redo = new MenuItem("Redo", new com.o2d.pkayjava.editor.event.MenuItemListener(REDO, null, EDIT_MENU)).setShortcut(maskKey + " + Shift + Z");
            addItem(cut);
            addItem(copy);
            addItem(paste);
            addItem(undo);
            addItem(redo);
        }

        public void setProjectOpen(boolean open) {
            cut.setDisabled(!open);
            copy.setDisabled(!open);
            paste.setDisabled(!open);
            undo.setDisabled(!open);
            redo.setDisabled(!open);
        }

    }


    private class FileMenu extends O2DMenu {

        private final PopupMenu scenesPopupMenu;
        private final Array<MenuItem> sceneMenuItems;
        private final MenuItem saveProject;
        private final MenuItem scenesMenuItem;
        private final MenuItem importToLibrary;
        private final MenuItem export;
        private final MenuItem exportSettings;

        private final PopupMenu recentProjectsPopupMenu;
        private final Array<MenuItem> recentProjectsMenuItems;
        private final MenuItem recentProjectsMenuItem;

        public FileMenu() {
            super("File");
            saveProject = new MenuItem("Save Project", new com.o2d.pkayjava.editor.event.MenuItemListener(SAVE_PROJECT, null, FILE_MENU));
            addItem(new MenuItem("New Project", new com.o2d.pkayjava.editor.event.MenuItemListener(NEW_PROJECT, null, FILE_MENU)));
            addItem(new MenuItem("Open Project", new com.o2d.pkayjava.editor.event.MenuItemListener(OPEN_PROJECT, null, FILE_MENU)));
            addItem(saveProject);
            //
            scenesMenuItem = new MenuItem("Scenes");
            scenesPopupMenu = new PopupMenu();

            scenesMenuItem.setSubMenu(scenesPopupMenu);
            addItem(scenesMenuItem);
            //
            addSeparator();
            importToLibrary = new MenuItem("Import Resources", new com.o2d.pkayjava.editor.event.MenuItemListener(IMPORT_TO_LIBRARY, null, FILE_MENU));
            export = new MenuItem("Export", new com.o2d.pkayjava.editor.event.MenuItemListener(EXPORT, null, FILE_MENU));
            exportSettings = new MenuItem("Export Settings", new com.o2d.pkayjava.editor.event.MenuItemListener(EXPORT_SETTINGS, null, FILE_MENU));
            addItem(importToLibrary);
            addItem(export);
            addItem(exportSettings);
            //
            addSeparator();
            recentProjectsMenuItem = new MenuItem("Recent Projects...");
            recentProjectsPopupMenu = new PopupMenu();
            recentProjectsMenuItem.setSubMenu(recentProjectsPopupMenu);
            addItem(recentProjectsMenuItem);
            recentProjectsMenuItems = new Array<>();
            com.o2d.pkayjava.editor.data.manager.PreferencesManager prefs = com.o2d.pkayjava.editor.data.manager.PreferencesManager.getInstance();
            prefs.buildRecentHistory();
            reInitRecent(prefs.getRecentHistory());
            //
            addSeparator();
            addItem(new MenuItem("Exit", new com.o2d.pkayjava.editor.event.MenuItemListener(EXIT, null , FILE_MENU)));
            sceneMenuItems = new Array<>();
        }

        public void addScenes(ArrayList<SceneVO> scenes) {
            for (SceneVO sceneVO : scenes) {
                MenuItem menuItem = new MenuItem(sceneVO.sceneName, new com.o2d.pkayjava.editor.event.MenuItemListener(SELECT_SCENE, sceneVO.sceneName, FILE_MENU));
                sceneMenuItems.add(menuItem);
                scenesPopupMenu.addItem(menuItem);
            }
        }

        public void reInitScenes(ArrayList<SceneVO> scenes) {
            sceneMenuItems.clear();
            scenesPopupMenu.clear();
            scenesPopupMenu.addItem(new MenuItem("Create New Scene", new com.o2d.pkayjava.editor.event.MenuItemListener(NEW_SCENE, null, FILE_MENU)));
            scenesPopupMenu.addItem(new MenuItem("Delete Current Scene", new com.o2d.pkayjava.editor.event.MenuItemListener(DELETE_CURRENT_SCENE, null, FILE_MENU)));
            scenesPopupMenu.addSeparator();
            addScenes(scenes);
        }

        public String getFolderName(String path) {
            File path1 = new File(path);
            File path2 = new File(path1.getParent());
            return path2.getName();
        }

        public void addRecent(ArrayList<String> paths) {
            for (String path : paths) {
                MenuItem menuItem = new MenuItem(getFolderName(path), new com.o2d.pkayjava.editor.event.MenuItemListener(RECENT_PROJECTS, path, FILE_MENU));
                recentProjectsMenuItems.add(menuItem);
                recentProjectsPopupMenu.addItem(menuItem);
            }
        }

        public void reInitRecent(ArrayList<String> paths) {
            recentProjectsMenuItems.clear();
            recentProjectsPopupMenu.clear();

            addRecent(paths);

            if (paths.size() > 0) {
            	recentProjectsPopupMenu.addSeparator();
            }

            MenuItem menuItem = new MenuItem("Clear list", new com.o2d.pkayjava.editor.event.MenuItemListener(CLEAR_RECENTS, null, FILE_MENU));
            recentProjectsMenuItems.add(menuItem);
            recentProjectsPopupMenu.addItem(menuItem);
        }

        public void setProjectOpen(boolean open) {
            saveProject.setDisabled(!open);
            scenesMenuItem.setDisabled(!open);
            importToLibrary.setDisabled(!open);
            export.setDisabled(!open);
            exportSettings.setDisabled(!open);
        }

//        private class RecentProjectListener extends ChangeListener {
//            private final String path;
//
//            public RecentProjectListener(String path) {
//                this.path = path;
//            }
//
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                Gdx.app.log(TAG, "recentProject : " + path);
//                mediator.recentProjectItemClicked(path);
//            }
//        }
    }

    class O2DMenu extends com.o2d.pkayjava.editor.view.ui.widget.CustomMenu {

        public O2DMenu(String title) {
            super(title);
            Cell labelCell = openButton.getLabelCell();
            labelCell.width(openButton.getWidth() + 14);
        }
    }

    public void addMenuItem(String menu, String subMenuName, String notificationName) {
        if(menu.equals(FILE_MENU)) {
            fileMenu.addItem(new MenuItem(subMenuName, new com.o2d.pkayjava.editor.event.MenuItemListener(notificationName, null, menu)));
        }
        if(menu.equals(EDIT_MENU)) {
            editMenu.addItem(new MenuItem(subMenuName, new com.o2d.pkayjava.editor.event.MenuItemListener(notificationName, null, menu)));
        }
        if(menu.equals(WINDOW_MENU)) {
            windowMenu.addItem(new MenuItem(subMenuName, new com.o2d.pkayjava.editor.event.MenuItemListener(notificationName, null, menu)));
        }
    }
}
