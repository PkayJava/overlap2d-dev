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
import java.util.Set;

import com.badlogic.ashley.core.Entity;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.BaseNotification;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.controller.commands.ConvertToCompositeCommand;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.CompositeCameraChangeCommand;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;
import com.o2d.pkayjava.editor.view.stage.SandboxMediator;
import com.o2d.pkayjava.editor.view.stage.tools.PanTool;
import com.o2d.pkayjava.editor.view.ui.FollowersUI;
import com.o2d.pkayjava.editor.view.ui.box.UIToolBoxMediator;
import com.o2d.pkayjava.editor.view.ui.followers.BasicFollower;
import com.o2d.pkayjava.editor.view.ui.followers.FollowerFactory;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower;

/**
 * Created by azakhary on 5/20/2015.
 */
public class FollowersUIMediator extends SimpleMediator<FollowersUI> {
    private static final String TAG = com.uwsoft.editor.view.ui.FollowersUIMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    private HashMap<Entity, BasicFollower> followers = new HashMap<>();

    public FollowersUIMediator() {
        super(NAME, new FollowersUI());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                SceneDataManager.SCENE_LOADED,
                Overlap2D.ITEM_DATA_UPDATED,
                Overlap2D.ITEM_SELECTION_CHANGED,
                Overlap2D.SHOW_SELECTIONS,
                Overlap2D.HIDE_SELECTIONS,
                ItemFactory.NEW_ITEM_ADDED,
                PanTool.SCENE_PANNED,
                UIToolBoxMediator.TOOL_SELECTED,
                Overlap2D.ITEM_PROPERTY_DATA_FINISHED_MODIFYING,
                CompositeCameraChangeCommand.DONE,
                Overlap2D.ZOOM_CHANGED,
                ConvertToCompositeCommand.DONE
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        switch (notification.getName()) {
            case CompositeCameraChangeCommand.DONE:
                createFollowersForAllVisible();
            case SceneDataManager.SCENE_LOADED:
                createFollowersForAllVisible();
                break;
            case ItemFactory.NEW_ITEM_ADDED:
                createFollower(notification.getBody());
                break;
            case Overlap2D.ITEM_PROPERTY_DATA_FINISHED_MODIFYING:
                BasicFollower follower = followers.get(notification.getBody());
                if(follower != null) {
                    follower.update();
                }
                break;
            case Overlap2D.ITEM_DATA_UPDATED:
                follower = followers.get(notification.getBody());
                if(follower != null) {
                    follower.update();
                }
                break;
            case PanTool.SCENE_PANNED:
                updateAllFollowers();
                break;
            case Overlap2D.ITEM_SELECTION_CHANGED:
                clearAllSubFollowersExceptNew(notification.getBody());
                setNewSelectionConfiguration(notification.getBody());
                break;
            case Overlap2D.HIDE_SELECTIONS:
                hideAllFollowers(notification.getBody());
                break;
            case Overlap2D.SHOW_SELECTIONS:
                showAllFollowers(notification.getBody());
                break;
            case UIToolBoxMediator.TOOL_SELECTED:
                pushNotificationToFollowers(notification);
                break;
            case Overlap2D.ZOOM_CHANGED:
                updateAllFollowers();
                break;
            case ConvertToCompositeCommand.DONE:
                // because entities changed their parent, it's better to re-make all followers
                removeAllfollowers();
                createFollowersForAllVisible();
                break;
        }
    }

    public void pushNotificationToFollowers(Notification notification) {
        for (BasicFollower follower : followers.values()) {
            follower.handleNotification(notification);
        }
    }

    private void clearAllSubFollowersExceptNew(Set<Entity> items) {
        for (BasicFollower follower : followers.values()) {
            if(!items.contains(follower)) {
                if(follower instanceof NormalSelectionFollower) {
                    ((NormalSelectionFollower)follower).clearSubFollowers();
                }
            }
        }
    }

    private void setNewSelectionConfiguration(Set<Entity> items) {
        followers.values().forEach(BasicFollower::hide);
        for (Entity item : items) {
            followers.get(item).show();
        }
    }

    private void createFollowersForAllVisible() {
        removeAllfollowers();
        Sandbox sandbox = Sandbox.getInstance();
        NodeComponent nodeComponent = ComponentRetriever.get(sandbox.getCurrentViewingEntity(), NodeComponent.class);

        for (Entity entity: nodeComponent.children) {
            createFollower(entity);
        }
    }

    private void removeAllfollowers() {
        followers.values().forEach(BasicFollower::remove);
        followers.clear();
    }

    private void hideAllFollowers(Set<Entity> items) {
        for (Entity item : items) {
            followers.get(item).hide();
        }
    }

    private void showAllFollowers(Set<Entity> items) {
        for (Entity item : items) {
            followers.get(item).show();
        }
    }

    private void updateAllFollowers() {
        followers.values().forEach(BasicFollower::update);
    }

    public void createFollower(Entity entity) {
        BasicFollower follower = FollowerFactory.createFollower(entity);
        viewComponent.addActor(follower);
        followers.put(entity, follower);

        SandboxMediator sandboxMediator = facade.retrieveMediator(SandboxMediator.NAME);
        follower.handleNotification(new BaseNotification(UIToolBoxMediator.TOOL_SELECTED, sandboxMediator.getCurrentSelectedToolName()));
    }

    public void removeFollower(Entity entity) {
        followers.get(entity).remove();
        followers.remove(entity);
    }

    public void clearAllListeners() {
        followers.values().forEach(BasicFollower::clearFollowerListener);
    }

    public BasicFollower getFollower(Entity entity) {
        return followers.get(entity);
    }
}
