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

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.utils.Array;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.controller.commands.AddSelectionCommand;
import com.o2d.pkayjava.editor.controller.commands.DeleteItemsCommand;
import com.o2d.pkayjava.editor.controller.commands.ReleaseSelectionCommand;
import com.o2d.pkayjava.editor.controller.commands.SetSelectionCommand;
import com.o2d.pkayjava.runtime.data.LayerItemVO;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;
import com.o2d.pkayjava.editor.view.ui.box.*;
import com.o2d.pkayjava.editor.view.ui.box.UIItemsTreeBox;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by sargis on 4/10/15.
 */
public class UIItemsTreeBoxMediator extends PanelMediator<UIItemsTreeBox> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIItemsTreeBoxMediator.class.getName();
        NAME = TAG;
    }

    public UIItemsTreeBoxMediator() {
        super(NAME, new UIItemsTreeBox());
    }

    @Override
    public String[] listNotificationInterests() {
        String[] parentNotifications = super.listNotificationInterests();
        return Stream.of(parentNotifications, new String[]{
                SceneDataManager.SCENE_LOADED,
                ItemFactory.NEW_ITEM_ADDED,
                UIItemsTreeBox.ITEMS_SELECTED,
                SetSelectionCommand.DONE,
                AddSelectionCommand.DONE,
                ReleaseSelectionCommand.DONE,
                DeleteItemsCommand.DONE
        }).flatMap(Stream::of).toArray(String[]::new);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        Sandbox sandbox = Sandbox.getInstance();
        if (SceneDataManager.SCENE_LOADED.equals(notification.getName())) {
            Entity rootEntity = sandbox.getRootEntity();
            viewComponent.init(rootEntity);
        } else if (ItemFactory.NEW_ITEM_ADDED.equals(notification.getName())) {
            Entity rootEntity = sandbox.getRootEntity();
            rootEntity = sandbox.getRootEntity();
            viewComponent.init(rootEntity);
        } else if (DeleteItemsCommand.DONE.equals(notification.getName())) {
            Entity rootEntity = sandbox.getRootEntity();
            rootEntity = sandbox.getRootEntity();
            viewComponent.init(rootEntity);
        } else if (UIItemsTreeBox.ITEMS_SELECTED.equals(notification.getName())) {
            Selection<Tree.Node> selection = notification.getBody();
            Array<Tree.Node> nodes = selection.toArray();
            Set<Entity> items = new HashSet<>();

            for (Tree.Node node : nodes) {
                Integer entityId = (Integer) node.getObject();
                Entity item = EntityUtils.getByUniqueId(entityId);
                //layer lock thing
                LayerItemVO layerItemVO = EntityUtils.getEntityLayer(item);
                if (layerItemVO != null && layerItemVO.isLocked) {
                    continue;
                }
                if (item != null) {
                    items.add(item);
                }
            }
            sendSelectionNotification(items);
        } else if (SetSelectionCommand.DONE.equals(notification.getName())) {
            viewComponent.setSelection(sandbox.getSelector().getSelectedItems());
        } else if (AddSelectionCommand.DONE.equals(notification.getName())) {
            viewComponent.setSelection(sandbox.getSelector().getSelectedItems());
        } else if (ReleaseSelectionCommand.DONE.equals(notification.getName())) {
            viewComponent.setSelection(sandbox.getSelector().getSelectedItems());
        }
    }

    private void sendSelectionNotification(Set<Entity> items) {
        Set<Entity> ntfItems = (items.isEmpty()) ? null : items;
        Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_SET_SELECTION, ntfItems);
    }
}
