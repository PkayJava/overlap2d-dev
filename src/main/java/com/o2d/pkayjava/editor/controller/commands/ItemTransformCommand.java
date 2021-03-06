package com.o2d.pkayjava.editor.controller.commands;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.EntityModifyRevertableCommand;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by Osman on 01.08.2015.
 */
public class ItemTransformCommand extends EntityModifyRevertableCommand {

    private Array<Object> payload;

    private Integer entityId;

    @Override
    public void doAction() {
        payload = getNotification().getBody();
        Entity entity = (Entity) payload.get(0);
        Object[] newData = (Object[]) payload.get(2);

        entityId = EntityUtils.getEntityId(entity);

        Vector2 newPos = (Vector2) newData[0];
        Vector2 newSize = (Vector2) newData[1];
        Vector2 newScale = (Vector2) newData[2];
        Float newRotation = (Float) newData[3];

        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        if (newPos != null) {
            transformComponent.setX(newPos.x);
        }
        if (newPos != null) {
            transformComponent.setY(newPos.y);
        }
        if (newSize != null) dimensionsComponent.width = newSize.x;
        if (newSize != null) dimensionsComponent.height = newSize.y;
        if (newScale != null) transformComponent.setScaleX(newScale.x);
        if (newScale != null) transformComponent.setScaleY(newScale.y);
        if (newRotation != null) transformComponent.setRotation(newRotation);

        Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, entity);
    }

    @Override
    public void undoAction() {

        Entity entity = EntityUtils.getByUniqueId(entityId);
        Object[] prevData = (Object[]) payload.get(1);

        Vector2 prevPos = (Vector2) prevData[0];
        Vector2 prevSize = (Vector2) prevData[1];
        Vector2 prevScale = (Vector2) prevData[2];
        Float prevRotation = (Float) prevData[3];

        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);

        transformComponent.setX(prevPos.x);
        transformComponent.setY(prevPos.y);
        dimensionsComponent.width = prevSize.x;
        dimensionsComponent.height = prevSize.y;
        transformComponent.setScaleX(prevScale.x);
        transformComponent.setScaleY(prevScale.y);
        transformComponent.setRotation(prevRotation);

        Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, entity);
    }
}
