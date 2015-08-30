package com.o2d.pkayjava.runtime.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.brashmonkey.spriter.Dimension;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.particle.ParticleComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

public class ParticleSystem extends IteratingSystem {

    private ComponentMapper<com.o2d.pkayjava.runtime.components.particle.ParticleComponent> particleComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.particle.ParticleComponent.class);
    private ComponentMapper<com.o2d.pkayjava.runtime.components.TransformComponent> transformComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class);
    //private ComponentMapper<ParentNodeComponent> parentNodeComponentMapper = ComponentMapper.getFor(ParentNodeComponent.class);
    //private ComponentMapper<NodeComponent> nodeComponentMapper = ComponentMapper.getFor(NodeComponent.class);

    public ParticleSystem() {
        super(Family.all(com.o2d.pkayjava.runtime.components.particle.ParticleComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //System.out.println("DELTAING  " + entity.getId() +"  " +deltaTime);
        com.o2d.pkayjava.runtime.components.particle.ParticleComponent particleComponent = particleComponentMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TransformComponent transformComponent = transformComponentMapper.get(entity);
        com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
        ParticleEffect particleEffect = particleComponent.particleEffect;

        particleEffect.setPosition(transformComponent.getX() / particleComponent.worldMultiplyer, transformComponent.getY() / particleComponent.worldMultiplyer);
        particleEffect.update(deltaTime);
        //ParentNodeComponent parentNodeComponent = parentNodeComponentMapper.get(entity);

//		Entity parentEntity = parentNodeComponent.parentEntity;
//		while (parentEntity != null) {
//			parentNodeComponent = nodeComponentMapper.get(parentEntity);
//			parentEntity = parentNodeComponent.parentEntity;
//		}

    }

}
