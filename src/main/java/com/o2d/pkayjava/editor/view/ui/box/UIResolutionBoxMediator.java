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

package com.o2d.pkayjava.editor.view.ui.box;

import com.badlogic.gdx.math.Vector3;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.editor.proxy.ResolutionManager;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.editor.view.ui.box.*;
import com.o2d.pkayjava.editor.view.ui.box.UIResolutionBox;

/**
 * Created by sargis on 4/8/15.
 */
public class UIResolutionBoxMediator extends SimpleMediator<UIResolutionBox> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIResolutionBoxMediator.class.getName();
        NAME = TAG;
    }

    private ProjectManager projectManager;

    public UIResolutionBoxMediator() {
        super(NAME, new UIResolutionBox());
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
                ProjectManager.PROJECT_OPENED,
                UIResolutionBox.CHANGE_RESOLUTION_BTN_CLICKED,
                UIResolutionBox.DELETE_RESOLUTION_BTN_CLICKED,
                UIResolutionBox.REPACK_BTN_CLICKED,
                ResolutionManager.RESOLUTION_LIST_CHANGED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        Sandbox sandbox = Sandbox.getInstance();
        ResolutionEntryVO resolutionEntryVO;
        if (ResolutionManager.RESOLUTION_LIST_CHANGED.equals(notification.getName())) {
            viewComponent.update();
        } else if (ProjectManager.PROJECT_OPENED.equals(notification.getName())) {
            viewComponent.update();
        } else if (UIResolutionBox.CHANGE_RESOLUTION_BTN_CLICKED.equals(notification.getName())) {
            resolutionEntryVO = notification.getBody();
            float zoom = sandbox.getZoomPercent();
            Vector3 cameraPos = new Vector3(sandbox.getCamera().position);
            String name = sandbox.sceneControl.getCurrentSceneVO().sceneName;
            projectManager.openProjectAndLoadAllData(projectManager.getCurrentProjectVO().projectName, resolutionEntryVO.name);
            sandbox.loadCurrentProject(name);
            sandbox.setZoomPercent(zoom);
            sandbox.getCamera().position.set(cameraPos);
        } else if (UIResolutionBox.DELETE_RESOLUTION_BTN_CLICKED.equals(notification.getName())) {
            resolutionEntryVO = notification.getBody();
            DialogUtils.showConfirmDialog(sandbox.getUIStage(),
                    "Delete Resolution",
                    "Are you sure you want to delete resolution: " + resolutionEntryVO.name + "?",
                    new String[]{"Delete", "Cancel"}, new Integer[]{0, 1},
                    result -> {
                        if (result == 0) {
                            ResolutionManager resolutionManager = facade.retrieveProxy(ResolutionManager.NAME);
                            resolutionManager.deleteResolution(resolutionEntryVO);
                            String sceneName = sandbox.sceneControl.getCurrentSceneVO().sceneName;
                            sandbox.loadCurrentProject(sceneName);
                        }
                    });
        } else if (UIResolutionBox.REPACK_BTN_CLICKED.equals(notification.getName())) {
            ResolutionManager resolutionManager = facade.retrieveProxy(ResolutionManager.NAME);
            resolutionManager.rePackProjectImagesForAllResolutions();
            String sceneName = sandbox.sceneControl.getCurrentSceneVO().sceneName;
            sandbox.loadCurrentProject(sceneName);
        }
    }
}
