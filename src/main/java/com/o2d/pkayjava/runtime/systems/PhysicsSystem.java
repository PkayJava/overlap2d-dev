package com.o2d.pkayjava.runtime.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.PolygonComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

public class PhysicsSystem extends IteratingSystem {

    protected ComponentMapper<TransformComponent> transformComponentMapper = ComponentMapper.getFor(TransformComponent.class);

    private World world;

    public PhysicsSystem(World world) {
        super(Family.all(PhysicsBodyComponent.class).get());
        this.world = world;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformComponentMapper.get(entity);

        processBody(entity);

        PhysicsBodyComponent physicsBodyComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);
        Body body = physicsBodyComponent.body;
        transformComponent.setX(body.getPosition().x / PhysicsBodyLoader.getScale());
        transformComponent.setY(body.getPosition().y / PhysicsBodyLoader.getScale());
        transformComponent.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }

    protected void processBody(Entity entity) {
        PhysicsBodyComponent physicsBodyComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);
        PolygonComponent polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);
        TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);

        if (polygonComponent == null && physicsBodyComponent.body != null) {
            world.destroyBody(physicsBodyComponent.body);
            physicsBodyComponent.body = null;
        }

        if (physicsBodyComponent.body == null && polygonComponent != null) {
            if (polygonComponent.vertices == null) return;

            PhysicsBodyComponent bodyPropertiesComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);
            physicsBodyComponent.body = PhysicsBodyLoader.getInstance().createBody(world, bodyPropertiesComponent, polygonComponent.vertices, new Vector2(1, 1));

            physicsBodyComponent.body.setTransform(new Vector2(transformComponent.getX() * PhysicsBodyLoader.getScale(), transformComponent.getY() * PhysicsBodyLoader.getScale()), physicsBodyComponent.body.getAngle());
            physicsBodyComponent.body.setUserData(entity);
        }
    }

}
