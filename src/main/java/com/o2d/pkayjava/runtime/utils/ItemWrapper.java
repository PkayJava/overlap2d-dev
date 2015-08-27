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

package com.o2d.pkayjava.runtime.utils;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.runtime.components.ScriptComponent;
import com.o2d.pkayjava.runtime.scripts.IScript;
import com.o2d.pkayjava.runtime.utils.*;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

import java.util.HashMap;

/**
 * Created by azakhary on 7/8/2015.
 */
public class ItemWrapper {

    private Entity entity;

    private com.o2d.pkayjava.runtime.components.NodeComponent nodeComponent;
    private HashMap<String, Entity> childMap = new HashMap<String, Entity>();

    public ItemWrapper() {
        // empty wrapper is better then null pointer
    }

    public ItemWrapper(Entity entity) {
        this.entity = entity;
        nodeComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, NodeComponent.class);
        if(nodeComponent != null) {
            for (Entity child : nodeComponent.children) {
                MainItemComponent mainItemComponent = com.uwsoft.editor.renderer.utils.ComponentRetriever.get(child, MainItemComponent.class);
                childMap.put(mainItemComponent.itemIdentifier, child);
            }
        }
    }

    public com.uwsoft.editor.renderer.utils.ItemWrapper getChild(String id) {
        Entity entity = childMap.get(id);
        if(entity == null) return new com.uwsoft.editor.renderer.utils.ItemWrapper();

        return new com.uwsoft.editor.renderer.utils.ItemWrapper(entity);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        return com.uwsoft.editor.renderer.utils.ComponentRetriever.get(entity, clazz);
    }

    public com.uwsoft.editor.renderer.utils.ItemWrapper addChild(Entity child) {
        if(nodeComponent != null) {
            ParentNodeComponent parentNodeComponent = child.getComponent(ParentNodeComponent.class);
            parentNodeComponent.parentEntity = entity;
            nodeComponent.children.add(child);

            return  new com.uwsoft.editor.renderer.utils.ItemWrapper(child);
        }

        return new com.uwsoft.editor.renderer.utils.ItemWrapper();
    }

    public int getType() {
        MainItemComponent mainItemComponent = com.uwsoft.editor.renderer.utils.ComponentRetriever.get(entity, MainItemComponent.class);
        return mainItemComponent.entityType;
    }

    public Entity getEntity() {
        return entity;
    }

    public IScript addScript(IScript script) {
        ScriptComponent component = ComponentRetriever.get(entity, ScriptComponent.class);
        if(component == null) {
            component = new ScriptComponent();
            entity.add(component);
        }
        component.addScript(script);
        script.init(entity);

        return script;
    }
}
