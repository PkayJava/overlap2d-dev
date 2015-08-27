package com.o2d.pkayjava.runtime.systems.render.logic;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface Drawable {
	public abstract void draw(Batch batch, Entity entity);
}
