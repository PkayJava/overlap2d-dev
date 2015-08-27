package com.o2d.pkayjava.runtime.scripts;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.scripts.IScript;

/**
 * Created by CyberJoe on 6/19/2015.
 */
public abstract class BasicScript implements IScript {

    protected Entity entity;

    @Override
    public void init(Entity item) {
        entity = item;
    }

    public Entity getEntity() {
        return entity;
    }
}
