package com.o2d.pkayjava.editor.controller.commands;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.editor.controller.commands.DeleteItemsCommand;
import com.o2d.pkayjava.editor.controller.commands.DeleteLayerAtomCommand;
import com.o2d.pkayjava.editor.controller.commands.TransactiveCommand;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ZIndexComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.stage.Sandbox;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by CyberJoe on 7/25/2015.
 */
public class DeleteLayerCommand extends TransactiveCommand {
    private static final String TAG;
    public static final String NAME;
    public static final String DONE;
    public static final String UNDONE;

    static {
        TAG = DeleteItemsCommand.class.getName();
        NAME = TAG;
        DONE = NAME + "." + "DONE";
        UNDONE = NAME + "." + "UNDONE";
    }


    private String layerName;
    private DeleteLayerAtomCommand deleteLayerAtomCommand;

    @Override
    public void transaction() {
        layerName = getNotification().getBody();
        deleteLayerAtomCommand = new DeleteLayerAtomCommand(layerName);
        addInnerCommand(deleteLayerAtomCommand);
        DeleteItemsCommand deleteItemsCommand = new DeleteItemsCommand();
        deleteItemsCommand.setItemsToDelete(getItemsByLayerName(layerName));
        addInnerCommand(deleteItemsCommand);
    }

    @Override
    public void onFinish() {
        facade.sendNotification(DONE, deleteLayerAtomCommand.getLayerIndex());
    }

    @Override
    public void onFinishUndo() {
        facade.sendNotification(UNDONE, layerName);
    }

    public Set<Entity> getItemsByLayerName(String layerName) {
        Set<Entity> result = new HashSet<>();
        Entity viewingEntity = Sandbox.getInstance().getCurrentViewingEntity();
        NodeComponent nodeComponent = ComponentRetriever.get(viewingEntity, NodeComponent.class);
        for (int i = 0; i < nodeComponent.children.size; i++) {
            Entity child = nodeComponent.children.get(i);
            ZIndexComponent zIndexComponent = ComponentRetriever.get(child, ZIndexComponent.class);
            if (zIndexComponent.layerName.equals(layerName)) {
                result.add(child);
            }
        }

        return result;
    }

}
