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

package com.o2d.pkayjava.editor.view.ui.properties.panels;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.RemoveComponentFromItemCommand;
import com.o2d.pkayjava.editor.proxy.ResourceManager;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.properties.UIItemPropertiesMediator;
import com.o2d.pkayjava.runtime.components.ShaderComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.puremvc.patterns.observer.Notification;

import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by azakhary on 8/12/2015.
 */
public class UIShaderPropertiesMediator extends UIItemPropertiesMediator<Entity, UIShaderProperties> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIShaderPropertiesMediator.class.getName();
        NAME = TAG;
    }

    public UIShaderPropertiesMediator() {
        super(NAME, new UIShaderProperties());

        ResourceManager resourceManager = Overlap2DFacade.getInstance().retrieveProxy(ResourceManager.NAME);
        viewComponent.initView(resourceManager.getShaders());
    }

    @Override
    public String[] listNotificationInterests() {
        String[] defaultNotifications = super.listNotificationInterests();
        String[] notificationInterests = new String[]{
                UIShaderProperties.CLOSE_CLICKED
        };

        return ArrayUtils.addAll(defaultNotifications, notificationInterests);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        if (UIPhysicsProperties.CLOSE_CLICKED.equals(notification.getName())) {
            Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_REMOVE_COMPONENT, RemoveComponentFromItemCommand.payload(observableReference, ShaderComponent.class));
        }
    }

    @Override
    protected void translateObservableDataToView(Entity item) {
        ResourceManager resourceManager = Overlap2DFacade.getInstance().retrieveProxy(ResourceManager.NAME);

        ShaderComponent shaderComponent = ComponentRetriever.get(item, ShaderComponent.class);
        String currShaderName = shaderComponent.shaderName;
        viewComponent.setSelected(currShaderName);
    }

    @Override
    protected void translateViewToItemData() {
        ResourceManager resourceManager = Overlap2DFacade.getInstance().retrieveProxy(ResourceManager.NAME);
        ShaderComponent shaderComponent = ComponentRetriever.get(observableReference, ShaderComponent.class);
        String shaderName = viewComponent.getShader();
        if (shaderName.equals("Default")) {
            shaderComponent.clear();
        } else {
            shaderComponent.setShader(shaderName, resourceManager.getShaderProgram(shaderName));
        }
    }

    private String findShaderProgramName(HashMap<String, ShaderProgram> list, ShaderProgram object) {
        for (Map.Entry<String, ShaderProgram> entry : list.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == object) {
                return key;
            }
        }
        return null;
    }
}
