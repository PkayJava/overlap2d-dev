package com.o2d.pkayjava.editor.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.stage.Sandbox;

/**
 * Created by Osman on 01.08.2015.
 */
public class TransformCommandBuilder {

    Array<Object> payload;

    public void begin(Entity forEntity) {

        TransformComponent transformComponent = ComponentRetriever.get(forEntity, TransformComponent.class);
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(forEntity, DimensionsComponent.class);
        Object[] prevData = new Object[4];

        payload = new Array<>(3);
        payload.add(forEntity);
        payload.add(prevData);
        payload.add(new Object[4]);

        setPos(1, transformComponent.getX(), transformComponent.getY());
        setSize(1, dimensionsComponent.width, dimensionsComponent.height);
        setScale(1, transformComponent.getScaleX(), transformComponent.getScaleY());
        setRotation(1, transformComponent.getRotation());

    }

    private void setPos(int pIndex, float x, float y) {
        ((Object[])payload.get(pIndex))[0] = new Vector2(x, y);
    }

    private void setSize(int pIndex, float width, float height) {
        ((Object[])payload.get(pIndex))[1] = new Vector2(width, height);
    }

    private void setScale(int pIndex, float x, float y) {
        ((Object[])payload.get(pIndex))[2] = new Vector2(x, y);
    }

    private void setRotation(int pIndex, float rotation) {
        ((Object[])payload.get(pIndex))[3] = rotation;
    }

    public void setPos(float x, float y) {
        setPos(2, x, y);
    }

    public void setSize(float width, float height) {
        setSize(2, width, height);
    }

    public void setScale(float x, float y) {
        setScale(2, x, y);
    }

    public void setRotation(float rotation) {
        setRotation(2, rotation);
    }

    public void execute() {
        // check if payload is worth sending
        Object[] newData = (Object[]) payload.get(2);
        for(Object o : newData) {
            if (o != null) {
                Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_ITEM_TRANSFORM_TO, payload);
                return;
            }
        }
    }
}
