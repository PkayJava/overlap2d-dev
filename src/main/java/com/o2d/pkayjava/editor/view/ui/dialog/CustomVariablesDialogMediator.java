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

import java.util.Set;

import com.badlogic.ashley.core.Entity;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar;
import com.o2d.pkayjava.editor.view.stage.UIStage;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UIBasicItemProperties;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.utils.CustomVariables;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

/**
 * Created by azakhary on 5/12/2015.
 */
public class CustomVariablesDialogMediator extends SimpleMediator<CustomVariablesDialog> {
    private static final String TAG;
    private static final String NAME;

    static {
        TAG = CustomVariablesDialogMediator.class.getName();
        NAME = TAG;
    }

    private Entity observable = null;

    public CustomVariablesDialogMediator() {
        super(NAME, new CustomVariablesDialog());
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance();
        viewComponent.setEmpty();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                Overlap2D.ITEM_SELECTION_CHANGED,
                Overlap2D.EMPTY_SPACE_CLICKED,
                UIBasicItemProperties.CUSTOM_VARS_BUTTON_CLICKED,
                CustomVariablesDialog.ADD_BUTTON_PRESSED,
                CustomVariablesDialog.DELETE_BUTTON_PRESSED,
                Overlap2DMenuBar.CUSTOM_VARIABLES_EDITOR_OPEN
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        Sandbox sandbox = Sandbox.getInstance();
        UIStage uiStage = sandbox.getUIStage();

        if (Overlap2DMenuBar.CUSTOM_VARIABLES_EDITOR_OPEN.equals(notification.getName())) {
            viewComponent.show(uiStage);
        } else if (UIBasicItemProperties.CUSTOM_VARS_BUTTON_CLICKED.equals(notification.getName())) {
            viewComponent.show(uiStage);
        } else if (Overlap2D.ITEM_SELECTION_CHANGED.equals(notification.getName())) {
            Set<Entity> selection = notification.getBody();
            if (selection.size() == 1) {
                setObservable(selection.iterator().next());
            }
        } else if (Overlap2D.EMPTY_SPACE_CLICKED.equals(notification.getName())) {
            setObservable(null);
        } else if (CustomVariablesDialog.ADD_BUTTON_PRESSED.equals(notification.getName())) {
            setVariable();
            updateView();
        } else if (CustomVariablesDialog.DELETE_BUTTON_PRESSED.equals(notification.getName())) {
            removeVariable(notification.getBody());
            updateView();
        }
    }

    private void setVariable() {
        MainItemComponent mainItemComponent = ComponentRetriever.get(observable, MainItemComponent.class);
        CustomVariables vars = new CustomVariables();
        vars.loadFromString(mainItemComponent.customVars);
        String key = viewComponent.getKey();
        String value = viewComponent.getValue();
        vars.setVariable(key, value);
        mainItemComponent.customVars = vars.saveAsString();
    }

    private void removeVariable(String key) {
        MainItemComponent mainItemComponent = ComponentRetriever.get(observable, MainItemComponent.class);
        CustomVariables vars = new CustomVariables();
        vars.loadFromString(mainItemComponent.customVars);
        vars.removeVariable(key);
        mainItemComponent.customVars = vars.saveAsString();
    }

    private void setObservable(Entity item) {
        observable = item;
        updateView();
        viewComponent.setKeyFieldValue("");
        viewComponent.setValueFieldValue("");
    }

    private void updateView() {
        if (observable == null) {
            viewComponent.setEmpty();
        } else {
            CustomVariables vars = new CustomVariables();
            MainItemComponent mainItemComponent = ComponentRetriever.get(observable, MainItemComponent.class);
            vars.loadFromString(mainItemComponent.customVars);
            viewComponent.updateView(vars);
        }
    }
}
