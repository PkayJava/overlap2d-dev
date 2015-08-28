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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.dialog.DialogUtils;
import com.kotcrab.vis.ui.util.dialog.InputDialogListener;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.DeleteLayerCommand;
import com.o2d.pkayjava.editor.controller.commands.LayerSwapCommand;
import com.o2d.pkayjava.editor.controller.commands.NewLayerCommand;
import com.o2d.pkayjava.runtime.components.ZIndexComponent;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.controller.commands.CompositeCameraChangeCommand;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.data.LayerItemVO;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.ui.box.*;
import com.o2d.pkayjava.editor.view.ui.box.PanelMediator;
import com.o2d.pkayjava.editor.view.ui.box.UILayerBox;


/**
 * Created by azakhary on 4/17/2015.
 */
public class UILayerBoxMediator extends PanelMediator<UILayerBox> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UILayerBoxMediator.class.getName();
        NAME = TAG;
    }

    private ArrayList<LayerItemVO> layers;

    public UILayerBoxMediator() {
        super(NAME, new UILayerBox());
        facade = Overlap2DFacade.getInstance();
    }

    @Override
    public String[] listNotificationInterests() {
        String[] parentNotifications = super.listNotificationInterests();
        return Stream.of(parentNotifications, new String[]{
                SceneDataManager.SCENE_LOADED,
                UILayerBox.LAYER_ROW_CLICKED,
                UILayerBox.CREATE_NEW_LAYER,
                UILayerBox.CHANGE_LAYER_NAME,
                UILayerBox.DELETE_LAYER,
                UILayerBox.LOCK_LAYER,
                UILayerBox.HIDE_LAYER,
                CompositeCameraChangeCommand.DONE,
                Overlap2D.ITEM_SELECTION_CHANGED,
                ItemFactory.NEW_ITEM_ADDED,
                UILayerBox.LAYER_DROPPED,
                DeleteLayerCommand.DONE,
                DeleteLayerCommand.UNDONE,
                NewLayerCommand.DONE,
                LayerSwapCommand.DONE


        }).flatMap(Stream::of).toArray(String[]::new);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        UILayerBox.UILayerItem layerItem;
        if (SceneDataManager.SCENE_LOADED.equals(notification.getName())) {
            initLayerData();
            int layerid = getFirstFreeLayer();
            viewComponent.setCurrentSelectedLayer(layerid);
            viewComponent.currentSelectedLayerIndex = layerid;
        } else if (CompositeCameraChangeCommand.DONE.equals(notification.getName())) {
            initLayerData();
            int layerid = getFirstFreeLayer();
            viewComponent.setCurrentSelectedLayer(layerid);
            viewComponent.currentSelectedLayerIndex = layerid;
        } else if (NewLayerCommand.DONE.equals(notification.getName())) {
            initLayerData();
            setSelectedByName(notification.getBody());
        } else if (DeleteLayerCommand.DONE.equals(notification.getName())) {
            initLayerData();
            if (notification != null && notification.getBody() != null) {
                int deletedIndex = (int) notification.getBody() - 1;
                if (deletedIndex == -1) {
                    deletedIndex = 0;
                }
                viewComponent.setCurrentSelectedLayer(deletedIndex);
                viewComponent.currentSelectedLayerIndex = deletedIndex;
            }
        } else if (DeleteLayerCommand.UNDONE.equals(notification.getName())) {
            initLayerData();
            setSelectedByName(notification.getBody());
        } else if (UILayerBox.LAYER_ROW_CLICKED.equals(notification.getName())) {
            layerItem = notification.getBody();
            selectEntitiesByLayerName(layerItem);
        } else if (UILayerBox.CREATE_NEW_LAYER.equals(notification.getName())) {
            DialogUtils.showInputDialog(Sandbox.getInstance().getUIStage(), "Please set unique name for your Layer", "Please set unique name for your Layer", new InputDialogListener() {
                @Override
                public void finished(String input) {
                    if (checkIfNameIsUnique(input)) {
                        Object[] payload = NewLayerCommand.payload(viewComponent.getCurrentSelectedLayerIndex() + 1, input);
                        facade.sendNotification(Sandbox.ACTION_NEW_LAYER, payload);
                    } else {
                        // show error dialog
                    }
                }

                @Override
                public void canceled() {

                }
            });
        } else if (UILayerBox.LAYER_DROPPED.equals(notification.getName())) {
            facade.sendNotification(Sandbox.ACTION_SWAP_LAYERS, notification.getBody());
        } else if (LayerSwapCommand.DONE.equals(notification.getName())) {
            int index = viewComponent.getCurrentSelectedLayerIndex();
            initLayerData();
            viewComponent.setCurrentSelectedLayer(index);
        } else if (UILayerBox.DELETE_LAYER.equals(notification.getName())) {
            if (layers == null) return;
            int deletingLayerIndex = viewComponent.getCurrentSelectedLayerIndex();
            if (deletingLayerIndex != -1) {
                String layerName = layers.get(deletingLayerIndex).layerName;
                facade.sendNotification(Sandbox.ACTION_DELETE_LAYER, layerName);
            }
        } else if (UILayerBox.LOCK_LAYER.equals(notification.getName())) {
            layerItem = notification.getBody();
            lockLayerByName(layerItem);
        } else if (UILayerBox.HIDE_LAYER.equals(notification.getName())) {
            layerItem = notification.getBody();
            hideEntitiesByLayerName(layerItem);
        } else if (Overlap2D.ITEM_SELECTION_CHANGED.equals(notification.getName())) {
            Set<Entity> selection = notification.getBody();
            if (selection.size() == 1) {
                int index = viewComponent.getCurrentSelectedLayerIndex();
                ZIndexComponent zIndexComponent = ComponentRetriever.get(selection.iterator().next(), ZIndexComponent.class);
                index = findLayerByName(zIndexComponent.layerName);
                if (index == -1) {
                    // handle this somehow
                } else {
                    viewComponent.setCurrentSelectedLayer(index);
                    viewComponent.currentSelectedLayerIndex = index;
                }
            } else if (selection.size() > 1) {
                // multi selection handling not yet clear
            }
        } else if (ItemFactory.NEW_ITEM_ADDED.equals(notification.getName())) {
            int index = viewComponent.getCurrentSelectedLayerIndex();
            Entity item = notification.getBody();
            ZIndexComponent zIndexComponent = ComponentRetriever.get(item, ZIndexComponent.class);
            if (zIndexComponent.layerName == null)
                zIndexComponent.layerName = layers.get(index).layerName;
        } else if (UILayerBox.CHANGE_LAYER_NAME.equals(notification.getName())) {
            // TODO: this needs to be command
            String layerName = notification.getBody();
            int layerIndex = viewComponent.getCurrentSelectedLayerIndex();
            if (layerIndex == -1) {
                return;
            }
            LayerItemVO layer_view = layers.get(layerIndex);
            layerItem = viewComponent.getCurrentSelectedLayer();
            VisTextField textField = layerItem.getNameField();

            if (layer_view.layerName.equals(layerName))  // Name didn't change
            {
                textField.clearSelection();
                textField.setDisabled(true);
                viewComponent.enableDraggingInEditedSlot();
            } else if (checkIfNameIsUnique(layerName)) // Name changed
            {
                textField.clearSelection();
                textField.setDisabled(true);
                viewComponent.enableDraggingInEditedSlot();
                String prevName = layer_view.layerName;
                layer_view.layerName = layerName;
                // update the map
                Entity viewEntity = Sandbox.getInstance().getCurrentViewingEntity();
                LayerMapComponent layerMapComponent = ComponentRetriever.get(viewEntity, LayerMapComponent.class);
                layerMapComponent.rename(prevName, layerName);
            } else {
                //Show error dialog
            }
        }
    }


    private void setSelectedByName(String name) {
        String deletedLayerName = name;
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).layerName.equals(deletedLayerName)) {
                viewComponent.setCurrentSelectedLayer(i);
                viewComponent.currentSelectedLayerIndex = i;
                break;
            }
        }
    }

    /*
    // Booboo madafaka funktione
    private void remakeLayersArray() {
        Array<UILayerBox.UILayerItemSlot> slots = viewComponent.getLayerSlots();
        layers = new ArrayList<>();
        for(UILayerBox.UILayerItemSlot slot: slots) {
            LayerItemVO vo = slot.getUiLayerItem().getData();
            layers.add(vo);
        }
        LayerMapComponent layerMapComponent = ComponentRetriever.get(Sandbox.getInstance().getCurrentViewingEntity(), LayerMapComponent.class);
        layerMapComponent.setLayers(layers);
    }*/

    private void addNewLayerToItemComposite(LayerItemVO layerVo) {
        LayerMapComponent layerMapComponent = ComponentRetriever.get(Sandbox.getInstance().getCurrentViewingEntity(), LayerMapComponent.class);
        layerMapComponent.addLayer(layerVo);
    }

    private void lockLayerByName(UILayerBox.UILayerItem layerItem) {
        String layerName = layerItem.getLayerName();
        boolean toLock = !layerItem.isLocked();
        if (toLock) {
            Sandbox.getInstance().getSelector().clearSelections();
        }
        Entity viewEntity = Sandbox.getInstance().getCurrentViewingEntity();
        LayerMapComponent layerMapComponent = ComponentRetriever.get(viewEntity, LayerMapComponent.class);

        layerMapComponent.getLayer(layerName).isLocked = toLock;
    }

    private void selectEntitiesByLayerName(UILayerBox.UILayerItem layerItem) {
        if (layerItem.isLocked()) {
            Sandbox.getInstance().getSelector().clearSelections();
            viewComponent.clearSelection();
            return;
        }
        String layerName = layerItem.getLayerName();
        Entity viewEntity = Sandbox.getInstance().getCurrentViewingEntity();

        NodeComponent nodeComponent = ComponentRetriever.get(viewEntity, NodeComponent.class);
        Set<Entity> items = new HashSet<>();
        for (int i = 0; i < nodeComponent.children.size; i++) {
            Entity entity = nodeComponent.children.get(i);
            ZIndexComponent childZComponent = ComponentRetriever.get(entity, ZIndexComponent.class);
            if (childZComponent.layerName.equals(layerName)) {
                items.add(entity);
            }
        }
        Sandbox.getInstance().getSelector().clearSelections();
        facade.sendNotification(Sandbox.ACTION_ADD_SELECTION, items);
    }

    private void hideEntitiesByLayerName(UILayerBox.UILayerItem layerItem) {
        String layerName = layerItem.getLayerName();
        boolean toHide = !layerItem.isLayerVisible();
        Entity viewEntity = Sandbox.getInstance().getCurrentViewingEntity();

        NodeComponent nodeComponent = ComponentRetriever.get(viewEntity, NodeComponent.class);
        for (int i = 0; i < nodeComponent.children.size; i++) {
            Entity entity = nodeComponent.children.get(i);
            ZIndexComponent childZComponent = ComponentRetriever.get(entity, ZIndexComponent.class);
            if (childZComponent.layerName.equals(layerName)) {
                EntityUtils.getEntityLayer(entity).isVisible = toHide;
            }
        }
    }

    private int findLayerByName(String name) {
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).layerName.equals(name)) {
                return i;
            }
        }

        return -1;
    }

    private boolean checkIfNameIsUnique(String name) {
        for (int i = 0; i < layers.size(); i++) {
            if (layers.get(i).layerName.equals(name)) {
                return false;
            }
        }

        return true;
    }

    private int getFirstFreeLayer() {
        for (int i = 0; i < layers.size(); i++) {
            if (!layers.get(i).isLocked) {
                return i;
            }
        }

        return -1;
    }

    private void initLayerData() {

        Entity viewEntity = Sandbox.getInstance().getCurrentViewingEntity();
        LayerMapComponent layerMapComponent = ComponentRetriever.get(viewEntity, LayerMapComponent.class);
        layers = layerMapComponent.getLayers();

        viewComponent.clearItems();

        for (int i = (layers.size() - 1); i >= 0; i--) {
            viewComponent.addItem(layers.get(i));
        }
    }

    public int getCurrentSelectedLayerIndex() {
        return viewComponent.getCurrentSelectedLayerIndex();
    }

    public String getCurrentSelectedLayerName() {
        if (viewComponent.getCurrentSelectedLayerIndex() == -1) return null;
        return layers.get(viewComponent.getCurrentSelectedLayerIndex()).layerName;
    }
}