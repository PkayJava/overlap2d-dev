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

package com.o2d.pkayjava.runtime.factory.component;

import box2dLight.RayHandler;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.CompositeTransformComponent;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.data.CompositeItemVO;
import com.o2d.pkayjava.runtime.data.CompositeVO;
import com.o2d.pkayjava.runtime.data.LayerItemVO;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.factory.component.*;
import com.o2d.pkayjava.runtime.factory.component.ComponentFactory;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;

/**
 * Created by azakhary on 5/22/2015.
 */
public class CompositeComponentFactory extends com.o2d.pkayjava.runtime.factory.component.ComponentFactory {

    public CompositeComponentFactory(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        super(rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        createCommonComponents(entity, vo, com.o2d.pkayjava.runtime.factory.EntityFactory.COMPOSITE_TYPE);
        if(root != null) {
            createParentNodeComponent(root, entity);
        }
        createNodeComponent(root, entity);
        createPhysicsComponents(entity, vo);
        createCompositeComponents(entity, (com.o2d.pkayjava.runtime.data.CompositeItemVO) vo);
    }

    @Override
    protected com.o2d.pkayjava.runtime.components.DimensionsComponent createDimensionsComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.DimensionsComponent component = new com.o2d.pkayjava.runtime.components.DimensionsComponent();
        component.width = ((com.o2d.pkayjava.runtime.data.CompositeItemVO) vo).width;
        component.height = ((com.o2d.pkayjava.runtime.data.CompositeItemVO) vo).height;

        entity.add(component);
        return component;
    }

    @Override
    protected void createNodeComponent(Entity root, Entity entity) {
        if(root != null) {
            super.createNodeComponent(root, entity);
        }

        com.o2d.pkayjava.runtime.components.NodeComponent node = new com.o2d.pkayjava.runtime.components.NodeComponent();
        entity.add(node);
    }

    protected void createCompositeComponents(Entity entity, com.o2d.pkayjava.runtime.data.CompositeItemVO vo) {
        com.o2d.pkayjava.runtime.components.CompositeTransformComponent compositeTransform = new com.o2d.pkayjava.runtime.components.CompositeTransformComponent();

        com.o2d.pkayjava.runtime.components.LayerMapComponent layerMap = new com.o2d.pkayjava.runtime.components.LayerMapComponent();
        if(vo.composite.layers.size() == 0) {
            vo.composite.layers.add(com.o2d.pkayjava.runtime.data.LayerItemVO.createDefault());
        }
        layerMap.setLayers(vo.composite.layers);

        entity.add(compositeTransform);
        entity.add(layerMap);
    }
}
