package com.o2d.pkayjava.editor.controller.commands;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.editor.controller.commands.EntityModifyRevertableCommand;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.data.LayerItemVO;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.stage.Sandbox;

/**
 * Created by CyberJoe on 7/25/2015.
 */
public class NewLayerCommand extends EntityModifyRevertableCommand {
    private static final String TAG;
    public static final String NAME;
    public static final String DONE;

    static {
        TAG = NewLayerCommand.class.getName();
        NAME = TAG;
        DONE = NAME + "." + "DONE";
    }

    private String layerName;

    @Override
    public void doAction() {
        Object[] payload = getNotification().getBody();
        int index = (int) payload[0];
        layerName = (String) payload[1];

        Entity viewingEntity = Sandbox.getInstance().getCurrentViewingEntity();
        LayerMapComponent layerMapComponent = ComponentRetriever.get(viewingEntity, LayerMapComponent.class);

        LayerItemVO vo = new LayerItemVO(layerName);
        vo.isVisible = true;
        layerMapComponent.addLayer(index, vo);

        facade.sendNotification(DONE, layerName);
    }

    @Override
    public void undoAction() {
        Entity viewingEntity = Sandbox.getInstance().getCurrentViewingEntity();
        LayerMapComponent layerMapComponent = ComponentRetriever.get(viewingEntity, LayerMapComponent.class);

        layerMapComponent.deleteLayer(layerName);

        facade.sendNotification(DONE, layerName);
    }

    public static Object[] payload(int index, String name) {
        Object[] payload = new Object[2];
        payload[0] = index;
        payload[1] = name;

        return payload;
    }
}
