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

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.AddComponentToItemCommand;
import com.o2d.pkayjava.editor.controller.commands.AddToLibraryCommand;
import com.o2d.pkayjava.editor.utils.runtime.ComponentCloner;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.properties.UIItemPropertiesMediator;
import com.o2d.pkayjava.editor.view.ui.widget.components.color.ColorPickerAdapter;
import com.o2d.pkayjava.editor.view.ui.widget.components.color.CustomColorPicker;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.puremvc.patterns.observer.Notification;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by azakhary on 4/15/2015.
 */
public class UIBasicItemPropertiesMediator extends UIItemPropertiesMediator<Entity, UIBasicItemProperties> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIBasicItemPropertiesMediator.class.getName();
        NAME = TAG;
    }

    private TransformComponent transformComponent;
    private MainItemComponent mainItemComponent;
    private DimensionsComponent dimensionComponent;
    private TintComponent tintComponent;

    private HashMap<String, UIBasicItemProperties.ItemType> itemTypeMap = new HashMap<>();

    private HashMap<String, Class> componentClassMap = new HashMap<>();

    public UIBasicItemPropertiesMediator() {
        super(NAME, new UIBasicItemProperties());
    }

    @Override
    public void onRegister() {
        itemTypeMap.put("ENTITY_" + EntityFactory.COMPOSITE_TYPE, UIBasicItemProperties.ItemType.composite);
        itemTypeMap.put("ENTITY_" + EntityFactory.IMAGE_TYPE, UIBasicItemProperties.ItemType.texture);
        itemTypeMap.put("ENTITY_" + EntityFactory.PARTICLE_TYPE, UIBasicItemProperties.ItemType.particle);
        itemTypeMap.put("ENTITY_" + EntityFactory.LABEL_TYPE, UIBasicItemProperties.ItemType.text);
        itemTypeMap.put("ENTITY_" + EntityFactory.SPRITE_TYPE, UIBasicItemProperties.ItemType.spriteAnimation);
        itemTypeMap.put("ENTITY_" + EntityFactory.SPRITER_TYPE, UIBasicItemProperties.ItemType.spriterAnimation);
        itemTypeMap.put("ENTITY_" + EntityFactory.SPINE_TYPE, UIBasicItemProperties.ItemType.spineAnimation);
        itemTypeMap.put("ENTITY_" + EntityFactory.LIGHT_TYPE, UIBasicItemProperties.ItemType.light);
        itemTypeMap.put("ENTITY_" + EntityFactory.NINE_PATCH, UIBasicItemProperties.ItemType.patchImage);

        componentClassMap.put("Polygon Component", PolygonComponent.class);
        componentClassMap.put("Physics Component", PhysicsBodyComponent.class);
        componentClassMap.put("Shader Component", ShaderComponent.class);
    }

    @Override
    public String[] listNotificationInterests() {
        String[] defaultNotifications = super.listNotificationInterests();
        String[] notificationInterests = new String[]{
                UIBasicItemProperties.TINT_COLOR_BUTTON_CLICKED,
                UIBasicItemProperties.LINKING_CHANGED,
                UIBasicItemProperties.ADD_COMPONENT_BUTTON_CLICKED
        };

        return ArrayUtils.addAll(defaultNotifications, notificationInterests);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        if (UIBasicItemProperties.TINT_COLOR_BUTTON_CLICKED.equals(notification.getName())) {
            CustomColorPicker picker = new CustomColorPicker(new ColorPickerAdapter() {
                @Override
                public void finished(Color newColor) {
                    viewComponent.setTintColor(newColor);
                    facade.sendNotification(viewComponent.getUpdateEventName());
                }

                @Override
                public void changed(Color newColor) {
                    viewComponent.setTintColor(newColor);
                    facade.sendNotification(viewComponent.getUpdateEventName());
                }
            });

            picker.setColor(viewComponent.getTintColor());
            Sandbox.getInstance().getUIStage().addActor(picker.fadeIn());

        } else if (UIBasicItemProperties.LINKING_CHANGED.equals(notification.getName())) {
            boolean isLinked = notification.getBody();
            if (!isLinked) {
                facade.sendNotification(Sandbox.ACTION_ADD_TO_LIBRARY, AddToLibraryCommand.payloadUnLink(observableReference));
            } else {
                facade.sendNotification(Sandbox.SHOW_ADD_LIBRARY_DIALOG, observableReference);
            }
        } else if (UIBasicItemProperties.ADD_COMPONENT_BUTTON_CLICKED.equals(notification.getName())) {
            try {
                Class componentClass = componentClassMap.get(viewComponent.getSelectedComponent());
                if (componentClass == null) {
                    return;
                }
                Component component = (Component) ClassReflection.newInstance(componentClass);
                facade.sendNotification(Sandbox.ACTION_ADD_COMPONENT, AddComponentToItemCommand.payload(observableReference, component));
            } catch (ReflectionException ignored) {
            }
        }
    }

    protected void translateObservableDataToView(Entity entity) {
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        mainItemComponent = ComponentRetriever.get(entity, MainItemComponent.class);
        dimensionComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        tintComponent = ComponentRetriever.get(entity, TintComponent.class);

        if (EntityUtils.getType(observableReference) == EntityFactory.COMPOSITE_TYPE) {
            if (mainItemComponent.libraryLink != null && mainItemComponent.libraryLink.length() > 0) {
                viewComponent.setLinkage(true, mainItemComponent.libraryLink);
            } else {
                viewComponent.setLinkage(false, "not in library");
            }
        }

        viewComponent.setItemType(itemTypeMap.get("ENTITY_" + EntityUtils.getType(entity)), mainItemComponent.uniqueId);
        viewComponent.setIdBoxValue(mainItemComponent.itemIdentifier);
        viewComponent.setXValue(String.format(Locale.ENGLISH, "%.2f", transformComponent.getX()));
        viewComponent.setYValue(String.format(Locale.ENGLISH, "%.2f", transformComponent.getY()));

        viewComponent.setWidthValue(String.format(Locale.ENGLISH, "%.2f", dimensionComponent.width));
        viewComponent.setHeightValue(String.format(Locale.ENGLISH, "%.2f", dimensionComponent.height));
        viewComponent.setRotationValue(transformComponent.getRotation() + "");
        viewComponent.setScaleXValue(transformComponent.getScaleX() + "");
        viewComponent.setScaleYValue(transformComponent.getScaleY() + "");
        viewComponent.setTintColor(tintComponent.color);

        // non components
        Array<String> componentsToAddList = new Array<>();
        for (Map.Entry<String, Class> entry : componentClassMap.entrySet()) {
            String componentName = entry.getKey();
            Class componentClass = entry.getValue();
            Component component = entity.getComponent(componentClass);
            if (component == null) {
                componentsToAddList.add(componentName);
            }
        }
        viewComponent.setNonExistantComponents(componentsToAddList);

    }

    @Override
    protected void translateViewToItemData() {
        Entity entity = observableReference;

        transformComponent = ComponentCloner.get(ComponentRetriever.get(entity, TransformComponent.class));
        mainItemComponent = ComponentCloner.get(ComponentRetriever.get(entity, MainItemComponent.class));
        dimensionComponent = ComponentCloner.get(ComponentRetriever.get(entity, DimensionsComponent.class));
        tintComponent = ComponentCloner.get(ComponentRetriever.get(entity, TintComponent.class));

        mainItemComponent.itemIdentifier = viewComponent.getIdBoxValue();
        transformComponent.setX(NumberUtils.toFloat(viewComponent.getXValue(), transformComponent.getX()));
        transformComponent.setY(NumberUtils.toFloat(viewComponent.getYValue(), transformComponent.getY()));

        dimensionComponent.width = NumberUtils.toFloat(viewComponent.getWidthValue());
        dimensionComponent.height = NumberUtils.toFloat(viewComponent.getHeightValue());

        // TODO: manage width and height
        transformComponent.setRotation(NumberUtils.toFloat(viewComponent.getRotationValue(), transformComponent.getRotation()));
        transformComponent.setScaleX((viewComponent.getFlipH() ? -1 : 1) * NumberUtils.toFloat(viewComponent.getScaleXValue(), transformComponent.getScaleX()));
        transformComponent.setScaleY((viewComponent.getFlipV() ? -1 : 1) * NumberUtils.toFloat(viewComponent.getScaleYValue(), transformComponent.getScaleY()));
        Color color = viewComponent.getTintColor();
        tintComponent.color.set(color);

        Array<Component> componentsToUpdate = new Array<>();
        componentsToUpdate.add(transformComponent);
        componentsToUpdate.add(mainItemComponent);
        componentsToUpdate.add(dimensionComponent);
        componentsToUpdate.add(tintComponent);
        Object[] payload = new Object[2];
        payload[0] = entity;
        payload[1] = componentsToUpdate;
        Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_UPDATE_ITEM_DATA, payload);
    }
}
