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
import java.util.HashSet;
import java.util.Set;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.controller.commands.RevertableCommand;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by azakhary on 5/14/2015.
 */
public class ReleaseSelectionCommand extends RevertableCommand {

    private static final String TAG;
    public static final String NAME;
    public static final String DONE;

    static {
        TAG = ReleaseSelectionCommand.class.getName();
        NAME = TAG;
        DONE = NAME + "." + "DONE";
    }

    private Array<Integer> entityIds;

    @Override
    public void doAction() {
        Set<Entity> items = new HashSet<>(getNotification().<Collection<Entity>>getBody());
        Sandbox.getInstance().getSelector().releaseSelections(items);

        entityIds = EntityUtils.getEntityId(items);

        facade.sendNotification(DONE);
    }

    @Override
    public void undoAction() {
        Set<Entity> items = EntityUtils.getByUniqueId(entityIds);
        Sandbox.getInstance().getSelector().addSelections(items);

        facade.sendNotification(DONE);
    }
}
