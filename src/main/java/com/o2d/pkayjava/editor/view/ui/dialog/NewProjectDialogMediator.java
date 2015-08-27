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
import com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar;
import com.o2d.pkayjava.editor.view.stage.UIStage;
import com.o2d.pkayjava.editor.view.ui.dialog.NewProjectDialog;

/**
 * Created by sargis on 4/1/15.
 */
public class NewProjectDialogMediator extends SimpleMediator<com.o2d.pkayjava.editor.view.ui.dialog.NewProjectDialog> {
    private static final String TAG = NewProjectDialogMediator.class.getCanonicalName();
    private static final String NAME = TAG;

    public NewProjectDialogMediator() {
        super(NAME, new com.o2d.pkayjava.editor.view.ui.dialog.NewProjectDialog());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar.NEW_PROJECT,
                com.o2d.pkayjava.editor.view.ui.dialog.NewProjectDialog.CREATE_BTN_CLICKED
        };
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance();

        com.o2d.pkayjava.editor.proxy.ProjectManager projectManager = facade.retrieveProxy(com.o2d.pkayjava.editor.proxy.ProjectManager.NAME);
        viewComponent.setDefaultWorkspacePath(projectManager.getWorkspacePath());
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        com.o2d.pkayjava.editor.view.stage.Sandbox sandbox = com.o2d.pkayjava.editor.view.stage.Sandbox.getInstance();
        com.o2d.pkayjava.editor.view.stage.UIStage uiStage = sandbox.getUIStage();
        switch (notification.getName()) {
            case com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar.NEW_PROJECT:
                viewComponent.show(uiStage);
                break;
            case com.o2d.pkayjava.editor.view.ui.dialog.NewProjectDialog.CREATE_BTN_CLICKED:
                com.o2d.pkayjava.editor.proxy.ProjectManager projectManager = facade.retrieveProxy(com.o2d.pkayjava.editor.proxy.ProjectManager.NAME);
                int originWidth = Integer.parseInt(viewComponent.getOriginWidth());
                int originHeight = Integer.parseInt(viewComponent.getOriginHeight());
                int pixelPerWorldUnit = Integer.parseInt(viewComponent.getPixelPerWorldUnit());
                projectManager.createNewProject(notification.getBody(), originWidth, originHeight, pixelPerWorldUnit);
                //TODO: this should be not here
                sandbox.loadCurrentProject();
                viewComponent.hide();
                break;
        }

    }
}
