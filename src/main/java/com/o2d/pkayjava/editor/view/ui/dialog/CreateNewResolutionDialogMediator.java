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
import com.o2d.pkayjava.editor.proxy.ResolutionManager;
import com.o2d.pkayjava.editor.view.stage.UIStage;
import com.o2d.pkayjava.editor.view.ui.box.UIResolutionBox;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.editor.view.ui.dialog.CreateNewResolutionDialog;

/**
 * Created by sargis on 4/9/15.
 */
public class CreateNewResolutionDialogMediator extends SimpleMediator<CreateNewResolutionDialog> {
    private static final String TAG;
    private static final String NAME;

    static {
        TAG = CreateNewResolutionDialogMediator.class.getName();
        NAME = TAG;
    }

    public CreateNewResolutionDialogMediator() {
        super(NAME, new CreateNewResolutionDialog());
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = Overlap2DFacade.getInstance();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                UIResolutionBox.CREATE_NEW_RESOLUTION_BTN_CLICKED,
                CreateNewResolutionDialog.CREATE_BTN_CLICKED,
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        Sandbox sandbox = Sandbox.getInstance();
        UIStage uiStage = sandbox.getUIStage();
        if (UIResolutionBox.CREATE_NEW_RESOLUTION_BTN_CLICKED.equals(notification.getName())) {
            viewComponent.show(uiStage);
        } else if (CreateNewResolutionDialog.CREATE_BTN_CLICKED.equals(notification.getName())) {
            ResolutionEntryVO resolutionEntryVO = notification.getBody();
            ResolutionManager resolutionManager = facade.retrieveProxy(ResolutionManager.NAME);
            resolutionManager.createNewResolution(resolutionEntryVO);
            viewComponent.hide();
        }
    }
}
