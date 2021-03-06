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

import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.proxy.FontManager;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.view.stage.tools.*;
import com.o2d.pkayjava.editor.view.ui.box.UIToolBox;

/**
 * Created by sargis on 4/9/15.
 */
public class UIToolBoxMediator extends SimpleMediator<UIToolBox> {
    private static final String TAG;
    public static final String NAME;

    public static final String TOOL_SELECTED;

    static {
        TAG = UIToolBoxMediator.class.getName();
        NAME = TAG;
        TOOL_SELECTED = NAME + "." + "TOOL_CHANGED";
    }


    private String currentTool;
    private Array<String> toolList;


    public UIToolBoxMediator() {
        super(NAME, new UIToolBox());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();

        toolList = getToolNameList();
        currentTool = SelectionTool.NAME;

        viewComponent.createToolButtons(toolList);
    }


    public Array<String> getToolNameList() {
        Array<String> toolNames = new Array();
        toolNames.add(SelectionTool.NAME);
        toolNames.add(TransformTool.NAME);
        toolNames.add(TextTool.NAME);
        toolNames.add(PointLightTool.NAME);
        toolNames.add(ConeLightTool.NAME);
        toolNames.add(PolygonTool.NAME);
        return toolNames;
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                UIToolBox.TOOL_CLICKED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        FontManager fontManager = facade.retrieveProxy(FontManager.NAME);
        if (UIToolBox.TOOL_CLICKED.equals(notification.getName())) {
            currentTool = notification.getBody();
            if (TextTool.NAME.equals(currentTool)) {
                if (fontManager != null) {
                    facade.sendNotification(TOOL_SELECTED, currentTool);
                }
            } else {
                facade.sendNotification(TOOL_SELECTED, currentTool);
            }
        }
    }

    public void setCurrentTool(String tool) {
        viewComponent.setCurrentTool(tool);
        currentTool = tool;
    }
}
