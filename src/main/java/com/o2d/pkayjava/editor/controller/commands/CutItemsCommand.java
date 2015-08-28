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

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.o2d.pkayjava.editor.controller.commands.*;
import com.o2d.pkayjava.editor.controller.commands.CopyItemsCommand;
import com.o2d.pkayjava.runtime.data.CompositeVO;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by azakhary on 4/28/2015.
 */
public class CutItemsCommand extends EntityModifyRevertableCommand {

    private String backup;

    @Override
    public void doAction() {
        backup = com.uwsoft.editor.controller.commands.CopyItemsCommand.getJsonStringFromEntities(sandbox.getSelector().getSelectedItems());
        String data = CopyItemsCommand.getJsonStringFromEntities(sandbox.getSelector().getSelectedItems());

        Object[] payload = new Object[2];
        payload[0] = new Vector2(Sandbox.getInstance().getCamera().position.x,Sandbox.getInstance().getCamera().position.y);
        payload[1] = data;
        Sandbox.getInstance().copyToClipboard(payload);
        sandbox.getSelector().removeCurrentSelectedItems();

        facade.sendNotification(DeleteItemsCommand.DONE);
    }

    @Override
    public void undoAction() {
        Json json =  new Json();
        CompositeVO compositeVO = json.fromJson(CompositeVO.class, backup);
        Set<Entity> newEntitiesList = PasteItemsCommand.createEntitiesFromVO(compositeVO);

        for (Entity entity : newEntitiesList) {
            Overlap2DFacade.getInstance().sendNotification(ItemFactory.NEW_ITEM_ADDED, entity);
        }

        sandbox.getSelector().setSelections(newEntitiesList, true);
    }
}