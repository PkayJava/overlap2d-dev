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

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.runtime.components.PolygonComponent;
import com.o2d.pkayjava.runtime.components.ScriptComponent;
import com.o2d.pkayjava.runtime.components.ShaderComponent;
import com.o2d.pkayjava.runtime.components.TintComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.ZIndexComponent;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by azakhary on 5/22/2015.
 */
public abstract class ComponentFactory {

    protected com.o2d.pkayjava.runtime.resources.IResourceRetriever rm;
    protected RayHandler rayHandler;
    protected World world;

    protected ComponentMapper<com.o2d.pkayjava.runtime.components.NodeComponent> nodeComponentMapper;

    public ComponentFactory() {
        nodeComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.NodeComponent.class);
    }

    public ComponentFactory(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        this();
        injectDependencies(rayHandler, world, rm);
    }

    public void injectDependencies(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        this.rayHandler = rayHandler;
        this.world = world;
        this.rm = rm;
    }

    public abstract void createComponents(Entity root, Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo);

    protected void createCommonComponents(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo, int entityType) {
        com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = createDimensionsComponent(entity, vo);
        createMainItemComponent(entity, vo, entityType);
        createTransformComponent(entity, vo, dimensionsComponent);
        createTintComponent(entity, vo);
        createZIndexComponent(entity, vo);
        createScriptComponent(entity, vo);
        createMeshComponent(entity, vo);
        createPhysicsComponents(entity, vo);
        createShaderComponent(entity, vo);
    }

    protected com.o2d.pkayjava.runtime.components.ShaderComponent createShaderComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
    	if(vo.shaderName == null || vo.shaderName.isEmpty()){
    		return null;
    	}
		com.o2d.pkayjava.runtime.components.ShaderComponent component = new com.o2d.pkayjava.runtime.components.ShaderComponent();
		component.setShader(vo.shaderName, rm.getShaderProgram(vo.shaderName));
		entity.add(component);
		return component;
	}

	protected com.o2d.pkayjava.runtime.components.MainItemComponent createMainItemComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo, int entityType) {
        com.o2d.pkayjava.runtime.components.MainItemComponent component = new com.o2d.pkayjava.runtime.components.MainItemComponent();
        component.customVars = vo.customVars;
        component.uniqueId = vo.uniqueId;
        component.itemIdentifier = vo.itemIdentifier;
        component.libraryLink = vo.itemName;
        if(vo.tags != null) {
            component.tags = new HashSet<String>(Arrays.asList(vo.tags));
        }
        component.entityType = entityType;

        entity.add(component);

        return component;
    }

    protected com.o2d.pkayjava.runtime.components.TransformComponent createTransformComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo, com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent) {
        com.o2d.pkayjava.runtime.components.TransformComponent component = new com.o2d.pkayjava.runtime.components.TransformComponent();
        component.rotation = vo.rotation;
        component.scaleX = vo.scaleX;
        component.scaleY = vo.scaleY;
        component.x = vo.x;
        component.y = vo.y;

        if(Float.isNaN(vo.originX)) component.originX = dimensionsComponent.width/2f;
        else component.originX = vo.originX;

        if(Float.isNaN(vo.originY)) component.originY = dimensionsComponent.height/2f;
        else component.originY = vo.originY;

        entity.add(component);

        return component;
    }

    protected abstract com.o2d.pkayjava.runtime.components.DimensionsComponent createDimensionsComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo);

    protected com.o2d.pkayjava.runtime.components.TintComponent createTintComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.TintComponent component = new com.o2d.pkayjava.runtime.components.TintComponent();
        component.color.set(vo.tint[0], vo.tint[1], vo.tint[2], vo.tint[3]);

        entity.add(component);

        return component;
    }

    protected com.o2d.pkayjava.runtime.components.ZIndexComponent createZIndexComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.ZIndexComponent component = new com.o2d.pkayjava.runtime.components.ZIndexComponent();

        if(vo.layerName == "" || vo.layerName == null) vo.layerName = "Default";

        component.layerName = vo.layerName;
        component.setZIndex(vo.zIndex);
        component.needReOrder = false;
        entity.add(component);

        return component;
    }

    protected com.o2d.pkayjava.runtime.components.ScriptComponent createScriptComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.ScriptComponent component = new com.o2d.pkayjava.runtime.components.ScriptComponent();
        entity.add(component);
        return component;
    }

    protected com.o2d.pkayjava.runtime.components.ParentNodeComponent createParentNodeComponent(Entity root, Entity entity) {
        com.o2d.pkayjava.runtime.components.ParentNodeComponent component = new com.o2d.pkayjava.runtime.components.ParentNodeComponent();
        component.parentEntity = root;
        entity.add(component);

        //set visible to true depending on parent
        // TODO: I do not likes this part
        com.o2d.pkayjava.runtime.components.MainItemComponent mainItemComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.MainItemComponent.class);
        com.o2d.pkayjava.runtime.components.LayerMapComponent layerMapComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(root, com.o2d.pkayjava.runtime.components.LayerMapComponent.class);
        com.o2d.pkayjava.runtime.components.ZIndexComponent zIndexComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(root, com.o2d.pkayjava.runtime.components.ZIndexComponent.class);
        mainItemComponent.visible = layerMapComponent.isVisible(zIndexComponent.layerName);

        return component;
    }

    protected void createNodeComponent(Entity root, Entity entity) {
        com.o2d.pkayjava.runtime.components.NodeComponent component = nodeComponentMapper.get(root);
        component.children.add(entity);
    }

    protected void createPhysicsComponents(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        if(vo.physics == null){
            return;
        }

        createPhysicsBodyPropertiesComponent(entity, vo);
    }

    protected com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent createPhysicsBodyPropertiesComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent component = new com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent();
        component.allowSleep = vo.physics.allowSleep;
        component.awake = vo.physics.awake;
        component.bodyType = vo.physics.bodyType;
        component.bullet = vo.physics.bullet;
        component.centerOfMass = vo.physics.centerOfMass;
        component.damping = vo.physics.damping;
        component.density = vo.physics.density;
        component.friction = vo.physics.friction;
        component.gravityScale = vo.physics.gravityScale;
        component.mass = vo.physics.mass;
        component.restitution = vo.physics.restitution;
        component.rotationalInertia = vo.physics.rotationalInertia;

        entity.add(component);

        return component;
    }

    protected com.o2d.pkayjava.runtime.components.PolygonComponent createMeshComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.PolygonComponent component = new com.o2d.pkayjava.runtime.components.PolygonComponent();
        if(vo.shape != null) {
            component.vertices = vo.shape.polygons.clone();
            entity.add(component);

            return component;
        }
        return null;
    }

    public void setResourceManager(com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        this.rm = rm;
    }

}
