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

package com.o2d.pkayjava.editor.view.ui.dialog;

import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.editor.proxy.ResolutionManager;
import com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar;
import com.o2d.pkayjava.editor.view.stage.UIStage;
import com.o2d.pkayjava.editor.view.ui.dialog.*;
import com.o2d.pkayjava.editor.view.ui.dialog.ExportSettingsDialog;

/**
 * Created by sargis on 4/6/15.
 */
public class ExportSettingsDialogMediator extends SimpleMediator<ExportSettingsDialog> {
    private static final String TAG;
    private static final String NAME;

    static {
        TAG = ExportSettingsDialogMediator.class.getName();
        NAME = TAG;
    }

    public ExportSettingsDialogMediator() {
        super(NAME, new ExportSettingsDialog());
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = Overlap2DFacade.getInstance();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                Overlap2DMenuBar.EXPORT_SETTINGS,
                ExportSettingsDialog.SAVE_SETTINGS_BTN_CLICKED,
                ExportSettingsDialog.SAVE_SETTINGS_AND_EXPORT_BTN_CLICKED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        Sandbox sandbox = Sandbox.getInstance();
        UIStage uiStage = sandbox.getUIStage();
        if (Overlap2DMenuBar.EXPORT_SETTINGS.equals(notification.getName())) {
            viewComponent.show(uiStage);
        } else if (ExportSettingsDialog.SAVE_SETTINGS_AND_EXPORT_BTN_CLICKED.equals(notification.getName())) {
            saveExportSettings(notification.getBody());
            exportProject(notification.getBody());
            viewComponent.hide();
        } else if (ExportSettingsDialog.SAVE_SETTINGS_BTN_CLICKED.equals(notification.getName())) {
            saveExportSettings(notification.getBody());
            viewComponent.hide();
        }
    }

    private void exportProject(ExportSettingsDialog.ExportSettingsVO settingsVO) {
        saveExportSettings(settingsVO);
        ProjectManager projectManager = facade.retrieveProxy(ProjectManager.NAME);
        projectManager.exportProject();
    }

    private void saveExportSettings(ExportSettingsDialog.ExportSettingsVO settingsVO) {
        ProjectManager projectManager = facade.retrieveProxy(ProjectManager.NAME);
        projectManager.setTexturePackerSizes(settingsVO.width, settingsVO.height);
        ResolutionManager resolutionManager = facade.retrieveProxy(ResolutionManager.NAME);
        resolutionManager.rePackProjectImagesForAllResolutions();
        projectManager.setExportPaths(settingsVO.fileHandle.file());
        projectManager.saveCurrentProject();

    }
}
