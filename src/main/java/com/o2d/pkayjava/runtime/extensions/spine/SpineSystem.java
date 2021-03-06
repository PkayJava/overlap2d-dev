package com.o2d.pkayjava.runtime.extensions.spine;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.o2d.pkayjava.runtime.components.TransformComponent;

public class SpineSystem extends IteratingSystem {

    private ComponentMapper<SpineObjectComponent> spineObjectComponentMapper = ComponentMapper.getFor(SpineObjectComponent.class);
    private ComponentMapper<TransformComponent> transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);

    public SpineSystem() {
        super(Family.all(SpineObjectComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComponent = transformComponentMapper.get(entity);
        SpineObjectComponent spineObjectComponent = spineObjectComponentMapper.get(entity);

        spineObjectComponent.skeleton.updateWorldTransform(); //
        spineObjectComponent.state.update(deltaTime); // Update the animation time.
        spineObjectComponent.state.apply(spineObjectComponent.skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
        spineObjectComponent.skeleton.setPosition(transformComponent.getX() - spineObjectComponent.minX, transformComponent.getY() - spineObjectComponent.minY);
    }
}
