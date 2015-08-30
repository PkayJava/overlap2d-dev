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

import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.data.manager.PreferencesManager;
import com.o2d.pkayjava.editor.event.MenuItemListener;
import com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBarMediator;
import com.o2d.pkayjava.editor.view.ui.widget.CustomMenu;
import com.o2d.pkayjava.editor.view.ui.widget.CustomMenuBar;
import org.apache.commons.lang3.SystemUtils;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.kotcrab.vis.ui.widget.PopupMenu;
import com.o2d.pkayjava.runtime.data.SceneVO;

public class Overlap2DMenuBar extends CustomMenuBar {

    private static final String TAG;
    public static final String NAME;

    // File Menu
    public static final String FILE_MENU;
    public static final String NEW_PROJECT;
    public static final String OPEN_PROJECT;
    public static final String SAVE_PROJECT;
    public static final String IMPORT_TO_LIBRARY;
    public static final String RECENT_PROJECTS;
    public static final String CLEAR_RECENTS;
    public static final String EXPORT;
    public static final String EXPORT_SETTINGS;
    public static final String EXIT;
    public static final String NEW_SCENE;
    public static final String SELECT_SCENE;
    public static final String DELETE_CURRENT_SCENE;

    // Edit Menu
    public static final String EDIT_MENU;
    public static final String CUT;
    public static final String COPY;
    public static final String PAST;
    public static final String UNDO;
    public static final String REDO;

    // GameObject Menu
    public static final String GAME_OBJECT_MENU;
    public static final String CREATE_EMPTY;
    public static final String CREATE_EMPTY_CHILD;

    // Window Menu
    public static final String WINDOW_MENU;
    public static final String SPRITE_ANIMATIONS_EDITOR_OPEN;
    public static final String CUSTOM_VARIABLES_EDITOR_OPEN;

    public static final String RECENT_LIST_MODIFIED;

    static {
        TAG = Overlap2DMenuBarMediator.class.getName();
        NAME = TAG;

        // File Menu
        FILE_MENU = NAME + "." + "FILE_MENU";
        NEW_PROJECT = FILE_MENU + "." + "NEW_PROJECT";
        OPEN_PROJECT = FILE_MENU + "." + "OPEN_PROJECT";
        SAVE_PROJECT = FILE_MENU + "." + "SAVE_PROJECT";
        IMPORT_TO_LIBRARY = FILE_MENU + "." + "IMPORT_TO_LIBRARY";
        RECENT_PROJECTS = FILE_MENU + "." + "RECENT_PROJECTS";
        CLEAR_RECENTS = FILE_MENU + "." + "CLEAR_RECENTS";
        EXPORT = FILE_MENU + "." + "EXPORT";
        EXPORT_SETTINGS = FILE_MENU + "." + "EXPORT_SETTINGS";
        EXIT = NAME + "." + "EXIT";
        NEW_SCENE = FILE_MENU + "." + "NEW_SCENE";
        SELECT_SCENE = FILE_MENU + "." + "SELECT_SCENE";
        DELETE_CURRENT_SCENE = FILE_MENU + "." + "DELETE_CURRENT_SCENE";

        // Edit Menu
        EDIT_MENU = NAME + "." + "EDIT_MENU";
        CUT = EDIT_MENU + "." + "CUT";
        COPY = EDIT_MENU + "." + "COPY";
        PAST = EDIT_MENU + "." + "PAST";
        UNDO = EDIT_MENU + "." + "UNDO";
        REDO = EDIT_MENU + "." + "REDO";

        // GameObject Menu
        GAME_OBJECT_MENU = NAME + "." + "GAME_OBJECT_MENU";
        CREATE_EMPTY = GAME_OBJECT_MENU + "." + "CREATE_EMPTY";
        CREATE_EMPTY_CHILD = GAME_OBJECT_MENU + "." + "CREATE_EMPTY_CHILD";

        // Window Menu
        WINDOW_MENU = NAME + "." + "WINDOW_MENU";
        SPRITE_ANIMATIONS_EDITOR_OPEN = WINDOW_MENU + "." + "SPRITE_ANIMATIONS_EDITOR_OPEN";
        CUSTOM_VARIABLES_EDITOR_OPEN = WINDOW_MENU + "." + "CUSTOM_VARIABLES_EDITOR_OPEN";
        RECENT_LIST_MODIFIED = WINDOW_MENU + "." + "RECENT_LIST_MODIFIED";
    }

    private final FileMenu fileMenu;
    private final String maskKey;
    private final EditMenu editMenu;
    private final GameObjectMenu gameObjectMenu;
    private final WindowMenu windowMenu;
    private final Overlap2DFacade facade;

    public Overlap2DMenuBar() {
        facade = Overlap2DFacade.getInstance();
        maskKey = SystemUtils.IS_OS_MAC_OSX || SystemUtils.IS_OS_MAC ? "Cmd" : "Ctrl";
        fileMenu = new FileMenu();
        editMenu = new EditMenu();
        gameObjectMenu = new GameObjectMenu();
        windowMenu = new WindowMenu();


//        getTable().debug();
        addMenu(fileMenu);
        addMenu(editMenu);
        addMenu(gameObjectMenu);
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
            customVars = new MenuItem("Custom Variables", new MenuItemListener(CUSTOM_VARIABLES_EDITOR_OPEN, null, WINDOW_MENU));
            animations = new MenuItem("Sprite Animations", new MenuItemListener(SPRITE_ANIMATIONS_EDITOR_OPEN, null, WINDOW_MENU));
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
            cut = new MenuItem("Cut", new MenuItemListener(CUT, null, EDIT_MENU)).setShortcut(maskKey + " + X");
            copy = new MenuItem("Copy", new MenuItemListener(COPY, null, EDIT_MENU)).setShortcut(maskKey + " + C");
            paste = new MenuItem("Paste", new MenuItemListener(PAST, null, EDIT_MENU)).setShortcut(maskKey + " + P");
            undo = new MenuItem("Undo", new MenuItemListener(UNDO, null, EDIT_MENU)).setShortcut(maskKey + " + Z");
            redo = new MenuItem("Redo", new MenuItemListener(REDO, null, EDIT_MENU)).setShortcut(maskKey + " + Shift + Z");
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

    private class GameObjectMenu extends O2DMenu {

        private final MenuItem createEmpty;
        private final MenuItem createEmptyChild;

        public GameObjectMenu() {
            super("GameObject");
            createEmpty = new MenuItem("Create Empty", new MenuItemListener(CREATE_EMPTY, null, GAME_OBJECT_MENU));
            createEmptyChild = new MenuItem("Create Empty Child", new MenuItemListener(CREATE_EMPTY_CHILD, null, GAME_OBJECT_MENU));
            addItem(createEmpty);
            addItem(createEmptyChild);
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
            saveProject = new MenuItem("Save Project", new MenuItemListener(SAVE_PROJECT, null, FILE_MENU));
            addItem(new MenuItem("New Project", new MenuItemListener(NEW_PROJECT, null, FILE_MENU)));
            addItem(new MenuItem("Open Project", new MenuItemListener(OPEN_PROJECT, null, FILE_MENU)));
            addItem(saveProject);
            //
            scenesMenuItem = new MenuItem("Scenes");
            scenesPopupMenu = new PopupMenu();

            scenesMenuItem.setSubMenu(scenesPopupMenu);
            addItem(scenesMenuItem);
            //
            addSeparator();
            importToLibrary = new MenuItem("Import Resources", new MenuItemListener(IMPORT_TO_LIBRARY, null, FILE_MENU));
            export = new MenuItem("Export", new MenuItemListener(EXPORT, null, FILE_MENU));
            exportSettings = new MenuItem("Export Settings", new MenuItemListener(EXPORT_SETTINGS, null, FILE_MENU));
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
            PreferencesManager prefs = PreferencesManager.getInstance();
            prefs.buildRecentHistory();
            reInitRecent(prefs.getRecentHistory());
            //
            addSeparator();
            addItem(new MenuItem("Exit", new MenuItemListener(EXIT, null, FILE_MENU)));
            sceneMenuItems = new Array<>();
        }

        public void addScenes(ArrayList<SceneVO> scenes) {
            for (SceneVO sceneVO : scenes) {
                MenuItem menuItem = new MenuItem(sceneVO.sceneName, new MenuItemListener(SELECT_SCENE, sceneVO.sceneName, FILE_MENU));
                sceneMenuItems.add(menuItem);
                scenesPopupMenu.addItem(menuItem);
            }
        }

        public void reInitScenes(ArrayList<SceneVO> scenes) {
            sceneMenuItems.clear();
            scenesPopupMenu.clear();
            scenesPopupMenu.addItem(new MenuItem("Create New Scene", new MenuItemListener(NEW_SCENE, null, FILE_MENU)));
            scenesPopupMenu.addItem(new MenuItem("Delete Current Scene", new MenuItemListener(DELETE_CURRENT_SCENE, null, FILE_MENU)));
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
                MenuItem menuItem = new MenuItem(getFolderName(path), new MenuItemListener(RECENT_PROJECTS, path, FILE_MENU));
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

            MenuItem menuItem = new MenuItem("Clear list", new MenuItemListener(CLEAR_RECENTS, null, FILE_MENU));
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

    private class O2DMenu extends CustomMenu {

        public O2DMenu(String title) {
            super(title);
            Cell labelCell = openButton.getLabelCell();
            labelCell.width(openButton.getWidth() + 14);
        }
    }

    public void addMenuItem(String menu, String subMenuName, String notificationName) {
        if (menu.equals(FILE_MENU)) {
            fileMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
        if (menu.equals(EDIT_MENU)) {
            editMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
        if (menu.equals(WINDOW_MENU)) {
            windowMenu.addItem(new MenuItem(subMenuName, new MenuItemListener(notificationName, null, menu)));
        }
    }
}
