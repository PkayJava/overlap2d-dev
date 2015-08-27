package com.o2d.pkayjava.editor.controller.commands;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.editor.controller.commands.EntityModifyRevertableCommand;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.data.LayerItemVO;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.stage.Sandbox;

/**
 * Created by CyberJoe on 7/25/2015.
 * This command marked as "atom" meaning it cannot be called outside the transaction
 * TODO: make this an annotation
 */
public class DeleteLayerAtomCommand extends EntityModifyRevertableCommand {

    private String layerName;

    private LayerItemVO layerItemVO;
    private int layerIndex;

    public DeleteLayerAtomCommand(String layerName) {
        this.layerName = layerName;
    }

    @Override
    public void doAction() {
        Entity viewingEntity = Sandbox.getInstance().getCurrentViewingEntity();
        LayerMapComponent layerMapComponent = ComponentRetriever.get(viewingEntity, LayerMapComponent.class);



        if(layerMapComponent.getLayers().size() > 1) {
            layerMapComponent.deleteLayer(layerName);
        } else {
            cancel();
        }
    }

    @Override
    public void undoAction() {
        Entity viewingEntity = Sandbox.getInstance().getCurrentViewingEntity();
        LayerMapComponent layerMapComponent = ComponentRetriever.get(viewingEntity, LayerMapComponent.class);

        layerMapComponent.addLayer(layerIndex, layerItemVO);
    }

    public int getLayerIndex() {
        return layerIndex;
    }
}
