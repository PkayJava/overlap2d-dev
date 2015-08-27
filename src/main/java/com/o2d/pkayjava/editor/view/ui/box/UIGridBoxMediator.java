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

import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.editor.view.ui.box.UIGridBox;

/**
 * Created by azakhary on 4/15/2015.
 */
public class UIGridBoxMediator extends SimpleMediator<UIGridBox> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIGridBoxMediator.class.getName();
        NAME = TAG;
    }

    public UIGridBoxMediator() {
        super(NAME, new UIGridBox());
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                ProjectManager.PROJECT_OPENED,
                Overlap2D.GRID_SIZE_CHANGED,
                UIGridBox.GRID_SIZE_TEXT_FIELD_UPDATED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        Sandbox sandbox = Sandbox.getInstance();
        if (ProjectManager.PROJECT_OPENED.equals(notification.getName())) {
            viewComponent.update();
            viewComponent.setGridSize(sandbox.getGridSize());
        } else if (Overlap2D.GRID_SIZE_CHANGED.equals(notification.getName())) {
            viewComponent.setGridSize(sandbox.getGridSize());
        } else if (UIGridBox.GRID_SIZE_TEXT_FIELD_UPDATED.equals(notification.getName())) {
            String body = notification.getBody();
            sandbox.setGridSize(Integer.parseInt(body));
        }
    }
}
