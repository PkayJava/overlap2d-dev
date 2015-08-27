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
import com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UIBasicItemProperties;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.utils.CustomVariables;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

/**
 * Created by azakhary on 5/12/2015.
 */
public class CustomVariablesDialogMediator extends SimpleMediator<com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog> {
    private static final String TAG = CustomVariablesDialogMediator.class.getCanonicalName();
    private static final String NAME = TAG;

    private Entity observable = null;

    public CustomVariablesDialogMediator() {
        super(NAME, new com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog());
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
                com.o2d.pkayjava.editor.Overlap2D.ITEM_SELECTION_CHANGED,
                com.o2d.pkayjava.editor.Overlap2D.EMPTY_SPACE_CLICKED,
                com.o2d.pkayjava.editor.view.ui.properties.panels.UIBasicItemProperties.CUSTOM_VARS_BUTTON_CLICKED,
                com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog.ADD_BUTTON_PRESSED,
                com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog.DELETE_BUTTON_PRESSED,
                com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar.CUSTOM_VARIABLES_EDITOR_OPEN
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        com.o2d.pkayjava.editor.view.stage.Sandbox sandbox = com.o2d.pkayjava.editor.view.stage.Sandbox.getInstance();
        com.o2d.pkayjava.editor.view.stage.UIStage uiStage = sandbox.getUIStage();

        switch (notification.getName()) {
            case com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBar.CUSTOM_VARIABLES_EDITOR_OPEN:
                viewComponent.show(uiStage);
                break;
            case com.o2d.pkayjava.editor.view.ui.properties.panels.UIBasicItemProperties.CUSTOM_VARS_BUTTON_CLICKED:
                viewComponent.show(uiStage);
                break;
            case com.o2d.pkayjava.editor.Overlap2D.ITEM_SELECTION_CHANGED:
                Set<Entity> selection = notification.getBody();
                if(selection.size() == 1) {
                    setObservable(selection.iterator().next());
                }
                break;
            case com.o2d.pkayjava.editor.Overlap2D.EMPTY_SPACE_CLICKED:
                setObservable(null);
                break;
            case com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog.ADD_BUTTON_PRESSED:
                setVariable();
                updateView();
                break;
            case com.o2d.pkayjava.editor.view.ui.dialog.CustomVariablesDialog.DELETE_BUTTON_PRESSED:
                removeVariable(notification.getBody());
                updateView();
                break;
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
        if(observable == null) {
            viewComponent.setEmpty();
        } else {
            CustomVariables vars = new CustomVariables();
            MainItemComponent mainItemComponent = ComponentRetriever.get(observable, MainItemComponent.class);
            vars.loadFromString(mainItemComponent.customVars);
            viewComponent.updateView(vars);
        }
    }
}
