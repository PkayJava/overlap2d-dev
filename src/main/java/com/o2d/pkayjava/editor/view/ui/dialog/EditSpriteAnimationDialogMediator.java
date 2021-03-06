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
import com.o2d.pkayjava.editor.view.ui.dialog.EditSpriteAnimationDialog;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UISpriteAnimationItemProperties;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent;
import com.o2d.pkayjava.runtime.data.FrameRange;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by azakhary on 5/12/2015.
 */
public class EditSpriteAnimationDialogMediator extends SimpleMediator<EditSpriteAnimationDialog> {
    private static final String TAG;
    private static final String NAME;

    static {
        TAG = EditSpriteAnimationDialogMediator.class.getName();
        NAME = TAG;
    }

    private Entity observable = null;

    public EditSpriteAnimationDialogMediator() {
        super(NAME, new EditSpriteAnimationDialog());
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = Overlap2DFacade.getInstance();
        viewComponent.setEmpty("No item selected");
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                Overlap2D.ITEM_SELECTION_CHANGED,
                Overlap2D.EMPTY_SPACE_CLICKED,
                UISpriteAnimationItemProperties.EDIT_ANIMATIONS_CLICKED,
                EditSpriteAnimationDialog.ADD_BUTTON_PRESSED,
                EditSpriteAnimationDialog.DELETE_BUTTON_PRESSED,
                Overlap2DMenuBar.SPRITE_ANIMATIONS_EDITOR_OPEN
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        Sandbox sandbox = Sandbox.getInstance();
        UIStage uiStage = sandbox.getUIStage();

        if (Overlap2DMenuBar.SPRITE_ANIMATIONS_EDITOR_OPEN.equals(notification.getName())) {
            viewComponent.show(uiStage);
        } else if (UISpriteAnimationItemProperties.EDIT_ANIMATIONS_CLICKED.equals(notification.getName())) {
            viewComponent.show(uiStage);
        } else if (Overlap2D.ITEM_SELECTION_CHANGED.equals(notification.getName())) {
            Set<Entity> selection = notification.getBody();
            if (selection.size() == 1) {
                Entity entity = selection.iterator().next();
                if (EntityUtils.getType(entity) == EntityFactory.SPRITE_TYPE) {
                    setObservable(entity);
                } else {
                    observable = null;
                    viewComponent.setEmpty("Selected item is not a sprite animation");
                }
            }
        } else if (Overlap2D.EMPTY_SPACE_CLICKED.equals(notification.getName())) {
            setObservable(null);
        } else if (EditSpriteAnimationDialog.ADD_BUTTON_PRESSED.equals(notification.getName())) {
            addAnimation();
            updateView();
        } else if (EditSpriteAnimationDialog.DELETE_BUTTON_PRESSED.equals(notification.getName())) {
            removeAnimation(notification.getBody());
            updateView();
        }
    }

    private void setObservable(Entity animation) {
        observable = animation;
        updateView();
        viewComponent.setName("");
        viewComponent.setFrameFrom(0);
        viewComponent.setFrameTo(0);
    }

    private void updateView() {
        if (observable == null) {
            viewComponent.setEmpty("No item selected");
        } else {
            SpriteAnimationComponent spriteAnimationComponent = ComponentRetriever.get(observable, SpriteAnimationComponent.class);
            viewComponent.updateView(spriteAnimationComponent.frameRangeMap);
        }
    }

    private void addAnimation() {
        String name = viewComponent.getName();
        int frameFrom = viewComponent.getFrameFrom();
        int frameTo = viewComponent.getFrameTo();

        SpriteAnimationComponent spriteAnimationComponent = ComponentRetriever.get(observable, SpriteAnimationComponent.class);
        spriteAnimationComponent.frameRangeMap.put(name, new FrameRange(name, frameFrom, frameTo));

        facade.sendNotification(Overlap2D.ITEM_DATA_UPDATED, observable);
    }

    private void removeAnimation(String name) {
        SpriteAnimationComponent spriteAnimationComponent = ComponentRetriever.get(observable, SpriteAnimationComponent.class);
        spriteAnimationComponent.frameRangeMap.remove(name);

        facade.sendNotification(Overlap2D.ITEM_DATA_UPDATED, observable);
    }
}
