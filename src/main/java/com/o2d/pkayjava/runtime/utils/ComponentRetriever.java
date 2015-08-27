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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.CompositeTransformComponent;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.LayerMapComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NinePatchComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.runtime.components.PolygonComponent;
import com.o2d.pkayjava.runtime.components.ScissorComponent;
import com.o2d.pkayjava.runtime.components.ScriptComponent;
import com.o2d.pkayjava.runtime.components.ShaderComponent;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.components.TintComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.ViewPortComponent;
import com.o2d.pkayjava.runtime.components.ZIndexComponent;
import com.o2d.pkayjava.runtime.components.label.LabelComponent;
import com.o2d.pkayjava.runtime.components.light.LightObjectComponent;
import com.o2d.pkayjava.runtime.components.particle.ParticleComponent;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.components.sprite.AnimationComponent;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent;


/**
 * Component Retriever is a singleton single instance class that initialises list of
 * all component mappers on first access, and provides a retrieval methods to get {@link Component}
 * with provided class from provided {@link Entity} object
 *
 * @author azakhary on 5/19/2015.
 */
public class ComponentRetriever {

    /**
     * single static instance of this class
     */
    private static ComponentRetriever instance;

    /**
     * Unique map of mappers that can be accessed by component class
     */
    private Map<Class, ComponentMapper<? extends Component>> mappers = new HashMap<Class, ComponentMapper<? extends Component>>();

    /**
     * Private constructor
     */
    private ComponentRetriever() {

    }

    /**
     * This is called only during first initialisation and populates map of mappers of all known Component mappers
     * it might be a good idea to use Reflections library later to create this list from all classes in components package of runtime, all in favour?
     */
    private void init() {
    	mappers.put(com.o2d.pkayjava.runtime.components.light.LightObjectComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.light.LightObjectComponent.class));
    	
    	mappers.put(com.o2d.pkayjava.runtime.components.particle.ParticleComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.particle.ParticleComponent.class));

        mappers.put(com.o2d.pkayjava.runtime.components.label.LabelComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.label.LabelComponent.class));

    	mappers.put(com.o2d.pkayjava.runtime.components.PolygonComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.PolygonComponent.class));
    	mappers.put(com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent.class));

        mappers.put(com.o2d.pkayjava.runtime.components.sprite.AnimationComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.sprite.AnimationComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent.class));
        
        mappers.put(com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.spriter.SpriterComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.spriter.SpriterComponent.class));
        
        mappers.put(com.o2d.pkayjava.runtime.components.CompositeTransformComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.CompositeTransformComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.DimensionsComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.DimensionsComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.LayerMapComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.LayerMapComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.MainItemComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.MainItemComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.NinePatchComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.NinePatchComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.NodeComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.NodeComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.ParentNodeComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ParentNodeComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.ScissorComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ScissorComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.TextureRegionComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TextureRegionComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.TintComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TintComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.TransformComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.ViewPortComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ViewPortComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.ZIndexComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ZIndexComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.ScriptComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ScriptComponent.class));

        mappers.put(com.o2d.pkayjava.runtime.components.PolygonComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.PolygonComponent.class));
        mappers.put(com.o2d.pkayjava.runtime.components.ShaderComponent.class, ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ShaderComponent.class));
    }

    /**
     * Short version of getInstance singleton variation, but with private access,
     * as there is no reason to get instance of this class, but only use it's public methods
     *
     * @return ComponentRetriever only instance
     */
    private static synchronized ComponentRetriever self() {
        if(instance == null) {
            instance = new ComponentRetriever();

            // Important to initialize during first creation, to populate mappers map
            instance.init();
        }

        return instance;
    }

    /**
     * @return returns Map of mappers, for internal use only
     */
    private Map<Class, ComponentMapper<? extends Component>> getMappers() {
        return mappers;
    }

    /**
     * Retrieves Component of provided type from a provided entity
     * @param entity of type Entity to retrieve component from
     * @param type of the component
     * @param <T>
     *
     * @return Component subclass instance
     */
    @SuppressWarnings("unchecked")
    public static <T extends Component> T get(Entity entity, Class<T> type) {
        return (T)self().getMappers().get(type).get(entity);
    }


    @SuppressWarnings("unchecked")
    public static  Collection<Component> getComponents(Entity entity) {
        Collection<Component> components = new ArrayList<Component>();
        for (ComponentMapper<? extends Component> mapper : self().getMappers().values()) {
            if(mapper.get(entity) != null) components.add(mapper.get(entity));
        }

        return components;
    }

    /**
     * This is to add a new mapper type externally, in case of for example implementing the plugin system,
     * where components might be initialized on the fly
     *
     * @param type
     */
    @SuppressWarnings("unchecked")
    public static void addMapper(Class type) {
        self().getMappers().put(type, ComponentMapper.getFor(type));
    }
}
