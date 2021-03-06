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
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Rectangle;
import com.brashmonkey.spriter.SCMLReader;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.data.SpriterVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.factory.component.*;
import com.o2d.pkayjava.runtime.factory.component.ComponentFactory;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.runtime.utils.LibGdxDrawer;
import com.o2d.pkayjava.runtime.utils.LibGdxLoader;

/**
 * Created by azakhary on 5/22/2015.
 */
public class SpriterComponentFactory extends com.o2d.pkayjava.runtime.factory.component.ComponentFactory {

    public SpriterComponentFactory(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        super(rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        createSpriterDataComponent(entity, (com.o2d.pkayjava.runtime.data.SpriterVO) vo);
        createCommonComponents(entity, vo, com.o2d.pkayjava.runtime.factory.EntityFactory.SPRITER_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
    }

    @Override
    protected com.o2d.pkayjava.runtime.components.DimensionsComponent createDimensionsComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.DimensionsComponent component = new com.o2d.pkayjava.runtime.components.DimensionsComponent();

        com.o2d.pkayjava.runtime.components.spriter.SpriterComponent spriterComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.spriter.SpriterComponent.class);

        Rectangle rect = spriterComponent.player.getBoundingRectangle(null);
        component.width = (int) rect.size.width;
        component.height = (int) rect.size.height;

        entity.add(component);
        return component;
    }

    protected com.o2d.pkayjava.runtime.components.spriter.SpriterComponent createSpriterDataComponent(Entity entity, com.o2d.pkayjava.runtime.data.SpriterVO vo) {
        com.o2d.pkayjava.runtime.components.spriter.SpriterComponent component = new com.o2d.pkayjava.runtime.components.spriter.SpriterComponent();
        component. entity = vo.entity;
        component.animation = vo.animation;
        component. animationName = vo.animationName;
        component.scale = vo.scale;

        FileHandle handle 	=	rm.getSCMLFile(vo.animationName);
        component.data = new SCMLReader(handle.read()).getData();
        com.o2d.pkayjava.runtime.utils.LibGdxLoader loader = 	new com.o2d.pkayjava.runtime.utils.LibGdxLoader(component.data);
        loader.load(handle.file());

        component.currentAnimationIndex	=	vo.animation;
        component.currentEntityIndex		=	vo.entity;

        component.player = new Player(component.data.getEntity(component.currentEntityIndex));

        component.player.setAnimation(component.currentAnimationIndex);
        component.player.setScale(component.scale);

        com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent spriterDrawer = new com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent();

        spriterDrawer.drawer = new com.o2d.pkayjava.runtime.utils.LibGdxDrawer(loader, null);

        entity.add(component);
        entity.add(spriterDrawer);

        return component;
    }
}
