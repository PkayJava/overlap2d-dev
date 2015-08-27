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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.controller.commands.RevertableCommand;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by azakhary on 5/14/2015.
 */
public class SetSelectionCommand extends com.o2d.pkayjava.editor.controller.commands.RevertableCommand {

    private static final String TAG;
    public static final String NAME;
    public static final String DONE;

    static {
        TAG = SetSelectionCommand.class.getName();
        NAME = TAG;
        DONE = NAME + "." + "DONE";
    }

    private Array<Integer> previousSelectionIds;

    @Override
    public void doAction() {
        HashSet<Entity> previousSelection = new HashSet<>(com.o2d.pkayjava.editor.view.stage.Sandbox.getInstance().getSelector().getSelectedItems());
        previousSelectionIds = com.o2d.pkayjava.editor.utils.runtime.EntityUtils.getEntityId(previousSelection);

        Set<Entity> items = getNotification().getBody();

        if(items == null) {
            // deselect all
            sandbox.getSelector().setSelections(items, true);
            facade.sendNotification(DONE);
            return;
        }

        // check if items are in viewable element, if no - cancel
        NodeComponent nodeComponent = ComponentRetriever.get(sandbox.getCurrentViewingEntity(), NodeComponent.class);
        for (Iterator<Entity> iterator = items.iterator(); iterator.hasNext();) {
            Entity item = iterator.next();
            if(!nodeComponent.children.contains(item, true)) {
                iterator.remove();
            }
        }

        if(items.size() == 0) {
            cancel();
        } else {
            sandbox.getSelector().setSelections(items, true);
        }
        facade.sendNotification(DONE);
    }

    @Override
    public void undoAction() {
        com.o2d.pkayjava.editor.view.stage.Sandbox.getInstance().getSelector().setSelections(com.o2d.pkayjava.editor.utils.runtime.EntityUtils.getByUniqueId(previousSelectionIds), true);
        facade.sendNotification(DONE);
    }
}
