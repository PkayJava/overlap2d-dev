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

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.RevertableCommand;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.data.CompositeItemVO;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

import java.util.HashMap;

/**
 * Created by azakhary on 4/28/2015.
 */
public class AddToLibraryCommand extends com.o2d.pkayjava.editor.controller.commands.RevertableCommand {

    private String createdLibraryItemName;
    private CompositeItemVO overwritten;
    private String prevName;
    private Integer entityId;

    @Override
    public void doAction() {
        Object[] payload = getNotification().getBody();

        Entity item = ((Entity) payload[0]);
        entityId = com.o2d.pkayjava.editor.utils.runtime.EntityUtils.getEntityId(item);
        createdLibraryItemName = (String) payload[1];

        MainItemComponent mainItemComponent = ComponentRetriever.get(item, MainItemComponent.class);

        if(createdLibraryItemName.length() > 0) {
            com.o2d.pkayjava.editor.proxy.ProjectManager projectManager = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance().retrieveProxy(com.o2d.pkayjava.editor.proxy.ProjectManager.NAME);
            HashMap<String, CompositeItemVO> libraryItems = projectManager.currentProjectInfoVO.libraryItems;

            if (libraryItems.containsKey(createdLibraryItemName)) {
                overwritten = libraryItems.get(createdLibraryItemName);
            }

            CompositeItemVO newVO = new CompositeItemVO();
            newVO.loadFromEntity(item);
            libraryItems.put(createdLibraryItemName, newVO);

            //mark this entity as belonging to library
            mainItemComponent.libraryLink = createdLibraryItemName;
            facade.sendNotification(com.o2d.pkayjava.editor.Overlap2D.LIBRARY_LIST_UPDATED);
        } else {
            prevName = mainItemComponent.libraryLink;
            // unlink it
            mainItemComponent.libraryLink = "";
        }
        facade.sendNotification(com.o2d.pkayjava.editor.Overlap2D.ITEM_DATA_UPDATED);
    }

    @Override
    public void undoAction() {
        com.o2d.pkayjava.editor.proxy.ProjectManager projectManager = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance().retrieveProxy(com.o2d.pkayjava.editor.proxy.ProjectManager.NAME);
        HashMap<String, CompositeItemVO> libraryItems = projectManager.currentProjectInfoVO.libraryItems;

        if(createdLibraryItemName.length() > 0) {
            libraryItems.remove(createdLibraryItemName);

            if (overwritten != null) {
                libraryItems.put(createdLibraryItemName, overwritten);
            }
            facade.sendNotification(com.o2d.pkayjava.editor.Overlap2D.LIBRARY_LIST_UPDATED);
        } else {
            Entity entity = com.o2d.pkayjava.editor.utils.runtime.EntityUtils.getByUniqueId(entityId);
            MainItemComponent mainItemComponent = ComponentRetriever.get(entity, MainItemComponent.class);
            mainItemComponent.libraryLink = prevName;
            facade.sendNotification(com.o2d.pkayjava.editor.Overlap2D.ITEM_DATA_UPDATED);
        }
    }

    public static Object payloadUnLink(Entity entity) {
        Object[] payload = new Object[2];
        payload[0] = entity;
        payload[1] = "";

        return payload;
    }

    public static Object payloadLink(Entity entity, String link) {
        Object[] payload = new Object[2];
        payload[0] = entity;
        payload[1] = link;

        return payload;
    }
}
