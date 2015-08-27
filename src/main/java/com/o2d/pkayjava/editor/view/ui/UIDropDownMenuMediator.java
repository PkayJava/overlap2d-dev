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

package com.o2d.pkayjava.editor.view.ui;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.proxy.PluginManager;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.view.ui.*;
import com.o2d.pkayjava.editor.view.ui.UIDropDownMenu;
import com.o2d.pkayjava.editor.view.ui.box.UIResourcesBoxMediator;

/**
 * Created by azakhary on 4/20/2015.
 */
public class UIDropDownMenuMediator extends SimpleMediator<UIDropDownMenu> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIDropDownMenuMediator.class.getName();
        NAME = TAG;
    }

    public static final Integer SCENE_ACTIONS_SET = 0;
    public static final Integer ITEMS_ACTIONS_SET = 1;
    public static final Integer RESOURCE_ACTION_SET = 2;
    public static final Integer IMAGE_RESOURCE_ACTION_SET = 3;

    private Sandbox sandbox;

    private Vector2 currentCoordinates;

    private Object currentObservable;

    public HashMap<Integer, Array<String>> actionSets = new HashMap<>();

    public UIDropDownMenuMediator() {
        super(NAME, new UIDropDownMenu());
    }

    @Override
    public void onRegister() {
        super.onRegister();

        sandbox = Sandbox.getInstance();

        actionSets.put(SCENE_ACTIONS_SET, new Array<>());
        actionSets.get(SCENE_ACTIONS_SET).add(Sandbox.ACTION_PASTE);

        actionSets.put(RESOURCE_ACTION_SET, new Array<>());
        actionSets.get(RESOURCE_ACTION_SET).add(Sandbox.ACTION_DELETE);

        actionSets.put(IMAGE_RESOURCE_ACTION_SET, new Array<>());
        //actionSets.get(IMAGE_RESOURCE_ACTION_SET).add(Sandbox.ACTION_DELETE_RESOURCE);

        actionSets.put(ITEMS_ACTIONS_SET, new Array<>());
        actionSets.get(ITEMS_ACTIONS_SET).add(Sandbox.ACTION_CUT);
        actionSets.get(ITEMS_ACTIONS_SET).add(Sandbox.ACTION_COPY);
        actionSets.get(ITEMS_ACTIONS_SET).add(Sandbox.ACTION_PASTE);
        actionSets.get(ITEMS_ACTIONS_SET).add(Sandbox.ACTION_DELETE);
        actionSets.get(ITEMS_ACTIONS_SET).add(Sandbox.ACTION_GROUP_ITEMS);
        actionSets.get(ITEMS_ACTIONS_SET).add(Sandbox.ACTION_CONVERT_TO_BUTTON);

        facade = Overlap2DFacade.getInstance();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                Overlap2D.SCENE_RIGHT_CLICK,
                Overlap2D.ITEM_RIGHT_CLICK,
                UIDropDownMenu.ITEM_CLICKED,
                UIResourcesBoxMediator.IMAGE_RIGHT_CLICK,
                UIResourcesBoxMediator.ANIMATION_RIGHT_CLICK,
                UIResourcesBoxMediator.LIBRARY_ITEM_RIGHT_CLICK,
                UIResourcesBoxMediator.PARTICLE_EFFECT_RIGHT_CLICK,
        };
    }

    private void applyItemTypeMutators(Array<String> actionsSet) {
        // generic mutators
        if (sandbox.getSelector().getCurrentSelection().size() == 1) {
            if (sandbox.getSelector().selectionIsComposite()) {
                actionsSet.add(Sandbox.SHOW_ADD_LIBRARY_DIALOG);
                actionsSet.add(Sandbox.ACTION_CAMERA_CHANGE_COMPOSITE);
            }
            actionsSet.add(Sandbox.ACTION_SET_GRID_SIZE_FROM_ITEM);
        }

        // external plugin mutators
        PluginManager pluginManager = facade.retrieveProxy(PluginManager.NAME);
        pluginManager.dropDownActionSets(sandbox.getSelector().getCurrentSelection(), actionsSet);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        if (Overlap2D.SCENE_RIGHT_CLICK.equals(notification.getName())) {
            Vector2 stageCoords = notification.getBody();
            showPopup(SCENE_ACTIONS_SET, stageCoords);
        } else if (Overlap2D.ITEM_RIGHT_CLICK.equals(notification.getName())) {
            Array<String> actionsSet = new Array<>(actionSets.get(ITEMS_ACTIONS_SET));
            applyItemTypeMutators(actionsSet);
            showPopup(actionsSet, sandbox.getSelector().getSelectedItem());
        } else if (UIResourcesBoxMediator.IMAGE_RIGHT_CLICK.equals(notification.getName())) {
            showPopup(IMAGE_RESOURCE_ACTION_SET, notification.getBody());
        } else if (UIResourcesBoxMediator.ANIMATION_RIGHT_CLICK.equals(notification.getName())) {
            showPopup(RESOURCE_ACTION_SET, notification.getBody());
        } else if (UIResourcesBoxMediator.LIBRARY_ITEM_RIGHT_CLICK.equals(notification.getName())) {
            showPopup(RESOURCE_ACTION_SET, notification.getBody());
        } else if (UIResourcesBoxMediator.PARTICLE_EFFECT_RIGHT_CLICK.equals(notification.getName())) {
            showPopup(RESOURCE_ACTION_SET, notification.getBody());
        } else if (UIDropDownMenu.ITEM_CLICKED.equals(notification.getName())) {
            facade.sendNotification(notification.getBody(), currentObservable);
        }
    }

    private void showPopup(Integer actionsSet, Object observable) {
        showPopup(actionSets.get(actionsSet), observable);
    }

    private void showPopup(Array<String> actionsSet, Object observable) {
        Vector2 coordinates = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

        sandbox.getUIStage().addActor(viewComponent);
        viewComponent.setActionList(actionsSet);
        viewComponent.setX(coordinates.x);
        viewComponent.setY(coordinates.y);

        currentCoordinates = new Vector2(coordinates);

        currentObservable = observable;
    }

}
