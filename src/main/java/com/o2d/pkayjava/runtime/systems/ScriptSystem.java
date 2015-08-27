package com.o2d.pkayjava.runtime.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.o2d.pkayjava.runtime.components.ScriptComponent;
import com.o2d.pkayjava.runtime.scripts.IScript;

/**
 * Created by azakhary on 6/19/2015.
 */
public class ScriptSystem extends IteratingSystem {

    private ComponentMapper<ScriptComponent> scriptComponentComponentMapper = ComponentMapper.getFor(ScriptComponent.class);

    public ScriptSystem() {
        super(Family.all(ScriptComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        for(IScript script: scriptComponentComponentMapper.get(entity).scripts) {
            script.act(deltaTime);
        }
    }
}
