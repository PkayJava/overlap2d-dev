package com.o2d.pkayjava.editor.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader;
import com.o2d.pkayjava.runtime.systems.PhysicsSystem;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

public class PhysicsAdjustSystem extends PhysicsSystem {

    private Vector2 transformVec = new Vector2();

    public PhysicsAdjustSystem(World world) {
        super(world);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        TransformComponent transformComponent = transformComponentMapper.get(entity);
        processBody(entity);

        PhysicsBodyComponent physicsBodyComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);

        if (physicsBodyComponent.body == null) return;

        transformVec.x = transformComponent.getX() * PhysicsBodyLoader.getScale();
        transformVec.y = transformComponent.getY() * PhysicsBodyLoader.getScale();
        physicsBodyComponent.body.setTransform(transformVec, transformComponent.getRotation() * MathUtils.degreesToRadians);

    }

}
