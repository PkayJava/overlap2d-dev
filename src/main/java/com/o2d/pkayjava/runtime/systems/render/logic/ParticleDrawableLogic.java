package com.o2d.pkayjava.runtime.systems.render.logic;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.o2d.pkayjava.runtime.components.particle.ParticleComponent;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

public class ParticleDrawableLogic implements com.o2d.pkayjava.runtime.systems.render.logic.Drawable {

	private ComponentMapper<com.o2d.pkayjava.runtime.components.particle.ParticleComponent> particleMapper ;

	public ParticleDrawableLogic() {
		particleMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.particle.ParticleComponent.class);
	}
	
	@Override
	public void draw(Batch batch, Entity entity) {
		com.o2d.pkayjava.runtime.components.particle.ParticleComponent particleComponent = particleMapper.get(entity);
		Matrix4 matrix = batch.getTransformMatrix().scl(particleComponent.worldMultiplyer);
		batch.setTransformMatrix(matrix);
		particleComponent.particleEffect.draw(batch);
		batch.setTransformMatrix(batch.getTransformMatrix().scl(1f/particleComponent.worldMultiplyer));
	}

}
