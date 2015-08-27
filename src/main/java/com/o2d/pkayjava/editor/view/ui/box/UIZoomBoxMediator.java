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

import com.o2d.pkayjava.editor.view.ui.box.*;
import com.o2d.pkayjava.editor.view.ui.box.UIZoomBox;

import org.apache.commons.lang3.math.NumberUtils;

import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.ProjectManager;

/**
 * Created by sargis on 4/9/15.
 */
public class UIZoomBoxMediator extends SimpleMediator<UIZoomBox> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIZoomBoxMediator.class.getName();
        NAME = TAG;
    }

    public UIZoomBoxMediator() {
        super(NAME, new UIZoomBox());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();
    }


    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                ProjectManager.PROJECT_OPENED,
                UIZoomBox.ZOOM_SHIFT_REQUESTED,
                UIZoomBox.ZOOM_VALUE_CHANGED,
                Overlap2D.ZOOM_CHANGED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        Sandbox sandbox = Sandbox.getInstance();
        if (ProjectManager.PROJECT_OPENED.equals(notification.getName())) {
            viewComponent.update();
            viewComponent.setCurrentZoom(sandbox.getZoomPercent() + "");
        } else if (UIZoomBox.ZOOM_SHIFT_REQUESTED.equals(notification.getName())) {
            float zoomDevider = notification.getBody();
            sandbox.zoomDevideBy(zoomDevider);
        } else if (UIZoomBox.ZOOM_VALUE_CHANGED.equals(notification.getName())) {
            sandbox.setZoomPercent(NumberUtils.toInt(viewComponent.getCurrentZoom()));
            facade.sendNotification(Overlap2D.ZOOM_CHANGED);
        } else if (Overlap2D.ZOOM_CHANGED.equals(notification.getName()))
            viewComponent.setCurrentZoom(sandbox.getZoomPercent() + "");
    }
}
