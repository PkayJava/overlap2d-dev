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
import com.badlogic.gdx.math.Vector2;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.RemoveComponentFromItemCommand;
import com.o2d.pkayjava.editor.controller.commands.component.UpdatePolygonComponentCommand;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.PolygonComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.properties.UIItemPropertiesMediator;
import com.o2d.pkayjava.editor.view.ui.properties.panels.*;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UIPolygonComponentProperties;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by azakhary on 7/2/2015.
 */
public class UIPolygonComponentPropertiesMediator extends UIItemPropertiesMediator<Entity, UIPolygonComponentProperties> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIPolygonComponentPropertiesMediator.class.getName();
        NAME = TAG;
    }

    private PolygonComponent polygonComponent;

    public UIPolygonComponentPropertiesMediator() {
        super(NAME, new UIPolygonComponentProperties());
    }

    @Override
    public String[] listNotificationInterests() {
        String[] defaultNotifications = super.listNotificationInterests();
        String[] notificationInterests = new String[]{
                UIPolygonComponentProperties.ADD_DEFAULT_MESH_BUTTON_CLICKED,
                UIPolygonComponentProperties.COPY_BUTTON_CLICKED,
                UIPolygonComponentProperties.PASTE_BUTTON_CLICKED,
                UIPolygonComponentProperties.CLOSE_CLICKED
        };
        return ArrayUtils.addAll(defaultNotifications, notificationInterests);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        if (UIPolygonComponentProperties.ADD_DEFAULT_MESH_BUTTON_CLICKED.equals(notification.getName())) {
            addDefaultMesh();
        } else if (UIPolygonComponentProperties.COPY_BUTTON_CLICKED.equals(notification.getName())) {
            copyMesh();
        } else if (UIPolygonComponentProperties.PASTE_BUTTON_CLICKED.equals(notification.getName())) {
            pasteMesh();
        } else if (UIPolygonComponentProperties.CLOSE_CLICKED.equals(notification.getName())) {
            Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_REMOVE_COMPONENT, RemoveComponentFromItemCommand.payload(observableReference, PolygonComponent.class));
        }
    }

    @Override
    protected void translateObservableDataToView(Entity item) {
        polygonComponent = item.getComponent(PolygonComponent.class);
        if (polygonComponent.vertices != null) {
            viewComponent.initView();
            int verticesCount = 0;
            for (int i = 0; i < polygonComponent.vertices.length; i++) {
                for (int j = 0; j < polygonComponent.vertices[i].length; j++) {
                    verticesCount++;
                }
            }
            viewComponent.setVerticesCount(verticesCount);

        } else {
            viewComponent.initEmptyView();
        }
    }

    @Override
    protected void translateViewToItemData() {

    }

    private void addDefaultMesh() {
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(observableReference, DimensionsComponent.class);
        polygonComponent.makeRectangle(dimensionsComponent.width, dimensionsComponent.height);

        Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, observableReference);
    }

    private void copyMesh() {
        polygonComponent = observableReference.getComponent(PolygonComponent.class);
        Sandbox.getInstance().copyToLocalClipboard("meshData", polygonComponent.vertices);
    }

    private void pasteMesh() {
        Vector2[][] vertices = (Vector2[][]) Sandbox.getInstance().retrieveFromLocalClipboard("meshData");
        if (vertices == null) return;
        Object[] payload = UpdatePolygonComponentCommand.payloadInitialState(observableReference);
        payload = UpdatePolygonComponentCommand.payload(payload, vertices);
        Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_UPDATE_MESH_DATA, payload);
    }
}
