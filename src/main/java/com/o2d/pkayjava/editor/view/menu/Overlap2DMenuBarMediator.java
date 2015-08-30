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

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;
import com.kotcrab.vis.ui.util.dialog.InputDialogListener;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.data.manager.PreferencesManager;
import com.o2d.pkayjava.editor.view.menu.*;
import com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.CommandManager;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;
import com.o2d.pkayjava.runtime.data.SceneVO;

/**
 * Created by sargis on 3/25/15.
 */
public class Overlap2DMenuBarMediator extends SimpleMediator<Overlap2DMenuBar> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = Overlap2DMenuBarMediator.class.getName();
        NAME = TAG;
    }

    private ProjectManager projectManager;

    public Overlap2DMenuBarMediator() {
        super(NAME, new Overlap2DMenuBar());
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = Overlap2DFacade.getInstance();
        projectManager = facade.retrieveProxy(ProjectManager.NAME);
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                //FILE
                Overlap2DMenuBar.NEW_PROJECT,
                Overlap2DMenuBar.OPEN_PROJECT,
                Overlap2DMenuBar.SAVE_PROJECT,
                Overlap2DMenuBar.IMPORT_TO_LIBRARY,
                Overlap2DMenuBar.EXPORT,
                Overlap2DMenuBar.EXPORT_SETTINGS,
                Overlap2DMenuBar.RECENT_PROJECTS,
                Overlap2DMenuBar.CLEAR_RECENTS,
                Overlap2DMenuBar.EXIT,
                Overlap2DMenuBar.NEW_SCENE,
                Overlap2DMenuBar.SELECT_SCENE,
                Overlap2DMenuBar.DELETE_CURRENT_SCENE,
                //EDIT
                Overlap2DMenuBar.CUT,
                Overlap2DMenuBar.COPY,
                Overlap2DMenuBar.PAST,
                Overlap2DMenuBar.UNDO,
                Overlap2DMenuBar.REDO,
                //General
                ProjectManager.PROJECT_OPENED,
                Overlap2DMenuBar.RECENT_LIST_MODIFIED
                //GameObject

        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        String type = notification.getType();

        if (notification.getName().equals(Overlap2DMenuBar.RECENT_LIST_MODIFIED)) {
            PreferencesManager prefs = PreferencesManager.getInstance();
            viewComponent.reInitRecent(prefs.getRecentHistory());
        }

        if (type == null || "".equals(type)) {
            handleGeneralNotification(notification);
            return;
        }
        if (Overlap2DMenuBar.FILE_MENU.equals(type)) {
            handleFileMenuNotification(notification);
        } else if (Overlap2DMenuBar.EDIT_MENU.equals(type)) {
            handleEditMenuNotification(notification);
        } else if (Overlap2DMenuBar.GAME_OBJECT_MENU.equals(type)) {
            handleGameObjectMenuNotification(notification);
        }
    }

    private void handleGameObjectMenuNotification(Notification notification) {
        if (Overlap2DMenuBar.CREATE_EMPTY.equals(notification.getName())) {
//            Entity entity = entityFactory.createEntity(sandbox.getCurrentViewingEntity(), vo);
//            Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_CREATE_ITEM, entity);
        } else if (Overlap2DMenuBar.CREATE_EMPTY_CHILD.equals(notification.getName())) {
//            Entity entity = entityFactory.createEntity(sandbox.getCurrentViewingEntity(), vo);
//            Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_CREATE_ITEM, entity);
        }
    }

    private void handleGeneralNotification(Notification notification) {
        if (ProjectManager.PROJECT_OPENED.equals(notification.getName())) {
            onProjectOpened();
        }
    }

    private void handleEditMenuNotification(Notification notification) {
        if (Overlap2DMenuBar.CUT.equals(notification.getName())) {
            facade.sendNotification(Sandbox.ACTION_CUT);
        } else if (Overlap2DMenuBar.COPY.equals(notification.getName())) {
            facade.sendNotification(Sandbox.ACTION_COPY);
        } else if (Overlap2DMenuBar.PAST.equals(notification.getName())) {
            facade.sendNotification(Sandbox.ACTION_PASTE);
        } else if (Overlap2DMenuBar.UNDO.equals(notification.getName())) {
            CommandManager commandManager = facade.retrieveProxy(CommandManager.NAME);
            if (commandManager != null) {
                commandManager.undoCommand();
            }
        } else if (Overlap2DMenuBar.REDO.equals(notification.getName())) {
            CommandManager commandManager = facade.retrieveProxy(CommandManager.NAME);
            if (commandManager != null) {
                commandManager.redoCommand();
            }
        }
    }

    private void handleFileMenuNotification(Notification notification) {
        Sandbox sandbox = Sandbox.getInstance();
        if (Overlap2DMenuBar.NEW_PROJECT.equals(notification.getName())) {
        } else if (Overlap2DMenuBar.OPEN_PROJECT.equals(notification.getName())) {
            showOpenProject();
        } else if (Overlap2DMenuBar.SAVE_PROJECT.equals(notification.getName())) {
            SceneVO vo = sandbox.sceneVoFromItems();
            projectManager.saveCurrentProject(vo);
        } else if (Overlap2DMenuBar.IMPORT_TO_LIBRARY.equals(notification.getName())) {
            //showDialog("showImportDialog");
        } else if (Overlap2DMenuBar.RECENT_PROJECTS.equals(notification.getName())) {
            recentProjectItemClicked(notification.getBody());
            //showDialog("showImportDialog");
        } else if (Overlap2DMenuBar.CLEAR_RECENTS.equals(notification.getName())) {
            clearRecents();
        } else if (Overlap2DMenuBar.EXPORT.equals(notification.getName())) {
            projectManager.exportProject();
        } else if (Overlap2DMenuBar.EXPORT_SETTINGS.equals(notification.getName())) {
            //showDialog("showExportDialog");
        } else if (Overlap2DMenuBar.EXIT.equals(notification.getName())) {
            Gdx.app.exit();
        } else if (Overlap2DMenuBar.NEW_SCENE.equals(notification.getName())) {
            DialogUtils.showInputDialog(sandbox.getUIStage(), "Create New Scene", "Scene Name : ", new InputDialogListener() {
                @Override
                public void finished(String input) {
                    if (input == null || input.equals("")) {
                        return;
                    }
                    SceneDataManager sceneDataManager = facade.retrieveProxy(SceneDataManager.NAME);
                    sceneDataManager.createNewScene(input);
                    sandbox.loadScene(input);
                    onScenesChanged();
                }

                @Override
                public void canceled() {

                }
            });
        } else if (Overlap2DMenuBar.SELECT_SCENE.equals(notification.getName())) {
            sceneMenuItemClicked(notification.getBody());
        } else if (Overlap2DMenuBar.DELETE_CURRENT_SCENE.equals(notification.getName())) {
            DialogUtils.showConfirmDialog(sandbox.getUIStage(),
                    "Delete Scene", "Do you realy want to delete '" + projectManager.currentProjectVO.lastOpenScene + "' scene?",
                    new String[]{"Delete", "Cancel"}, new Integer[]{0, 1}, result -> {
                        if (result == 0) {
                            SceneDataManager sceneDataManager = facade.retrieveProxy(SceneDataManager.NAME);
                            sceneDataManager.deleteCurrentScene();
                            sandbox.loadScene("MainScene");
                            onScenesChanged();
                        }
                    });
        }
    }

    private void onScenesChanged() {
        viewComponent.reInitScenes(projectManager.currentProjectInfoVO.scenes);
    }

    private void onProjectOpened() {
        viewComponent.reInitScenes(projectManager.currentProjectInfoVO.scenes);
        viewComponent.setProjectOpen(true);
    }

    public void showOpenProject() {
        Sandbox sandbox = Sandbox.getInstance();
        //chooser creation
        FileChooser fileChooser = new FileChooser(FileChooser.Mode.OPEN);

        // TODO: does not show folders on Windows
        //fileChooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        //fileChooser.setFileFilter(new SuffixFileFilter(".pit"));

        fileChooser.setMultiselectionEnabled(false);
        fileChooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(FileHandle file) {
                String path = file.file().getAbsolutePath();
                if (path.length() > 0) {
                    projectManager.openProjectFromPath(path);
                }
            }
        });
        sandbox.getUIStage().addActor(fileChooser.fadeIn());
    }

    public void recentProjectItemClicked(String path) {
        PreferencesManager prefs = PreferencesManager.getInstance();
        prefs.buildRecentHistory();
        prefs.pushHistory(path);
        if (projectManager != null) {
            projectManager.openProjectFromPath(path);
        }
    }

    public void clearRecents() {
        PreferencesManager prefs = PreferencesManager.getInstance();
        prefs.clearHistory();
        viewComponent.reInitRecent(prefs.getRecentHistory());
    }

    public void sceneMenuItemClicked(String sceneName) {
        Sandbox sandbox = Sandbox.getInstance();
        sandbox.loadScene(sceneName);
    }

    public void addMenuItem(String menu, String subMenuName, String notificationName) {
        viewComponent.addMenuItem(menu, subMenuName, notificationName);
    }
}
