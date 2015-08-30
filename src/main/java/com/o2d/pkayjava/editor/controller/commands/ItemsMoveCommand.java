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

package com.o2d.pkayjava.editor.controller.commands;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.EntityModifyRevertableCommand;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by azakhary on 6/4/2015.
 */
public class ItemsMoveCommand extends EntityModifyRevertableCommand {

    private HashMap<Integer, Vector2> prevLocations = new HashMap<>();

    @Override
    public void doAction() {

        Array<Object[]> payload = getNotification().getBody();

        for (int i = 0; i < payload.size; i++) {
            Object[] itemData = payload.get(i);

            Entity entity = (Entity) itemData[0];
            Vector2 newLocation = (Vector2) itemData[1];

            TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);

            Vector2 prevLocation = new Vector2(transformComponent.getX(), transformComponent.getY());
            if (itemData.length > 2) {
                prevLocation = (Vector2) itemData[2];
            }
            prevLocations.put(EntityUtils.getEntityId(entity), prevLocation);

            transformComponent.setX(newLocation.x);
            transformComponent.setY(newLocation.y);

            // pining UI to update current item properties tools
            Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, entity);
        }
    }

    @Override
    public void undoAction() {
        for (Map.Entry<Integer, Vector2> entry : prevLocations.entrySet()) {
            Integer entityUniqueId = entry.getKey();
            Vector2 prevLocation = entry.getValue();

            Entity entity = EntityUtils.getByUniqueId(entityUniqueId);

            TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
            transformComponent.setX(prevLocation.x);
            transformComponent.setY(prevLocation.y);

            // pining UI to update current item properties tools
            Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, entity);
        }

    }
}
