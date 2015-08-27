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

import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.controller.commands.component.UpdateSpriteAnimationDataCommand;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UISpriteAnimationItemProperties;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.view.ui.properties.UIItemPropertiesMediator;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

/**
 * Created by azakhary on 4/16/2015.
 */
public class UISpriteAnimationItemPropertiesMediator extends UIItemPropertiesMediator<Entity, UISpriteAnimationItemProperties> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UISpriteAnimationItemPropertiesMediator.class.getName();
        NAME = TAG;
    }

    private SpriteAnimationComponent spriteAnimationComponent;

    public UISpriteAnimationItemPropertiesMediator() {
        super(NAME, new UISpriteAnimationItemProperties());
    }

    @Override
    public String[] listNotificationInterests() {
        String[] defaultNotifications = super.listNotificationInterests();
        String[] notificationInterests = new String[]{
                UISpriteAnimationItemProperties.EDIT_ANIMATIONS_CLICKED
        };

        return ArrayUtils.addAll(defaultNotifications, notificationInterests);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        if (UISpriteAnimationItemProperties.EDIT_ANIMATIONS_CLICKED.equals(notification.getName())) {
        }
    }

    @Override
    protected void translateObservableDataToView(Entity entity) {

        spriteAnimationComponent = ComponentRetriever.get(entity, SpriteAnimationComponent.class);
        Array<String> animations = new Array<>();
        spriteAnimationComponent.frameRangeMap.keySet().forEach(animations::add);

        viewComponent.setFPS(spriteAnimationComponent.fps);
        viewComponent.setAnimations(animations);
        viewComponent.setSelectedAnimation(spriteAnimationComponent.currentAnimation);
        viewComponent.setPlayMode(spriteAnimationComponent.playMode);
    }

    @Override
    protected void translateViewToItemData() {
        Object payload = UpdateSpriteAnimationDataCommand.payload(observableReference,
                viewComponent.getFPS(),
                viewComponent.getSelectedAnimation(),
                viewComponent.getPlayMode());

        facade.sendNotification(Sandbox.ACTION_UPDATE_SPRITE_ANIMATION_DATA, payload);
    }
}
