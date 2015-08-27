package com.o2d.pkayjava.runtime.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent;

public class SpriteAnimationSystem extends IteratingSystem {
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TextureRegionComponent> tm;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent> sm;
    private ComponentMapper<com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent> sa;

	public SpriteAnimationSystem() {
		super(Family.all(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent.class).get());

		tm = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TextureRegionComponent.class);
		sm = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent.class);
        sa = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		com.o2d.pkayjava.runtime.components.TextureRegionComponent tex = tm.get(entity);
		com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent state = sm.get(entity);
        state.currentAnimation.setFrameDuration(1f/sa.get(entity).fps);
		tex.region = state.currentAnimation.getKeyFrame(state.time);

        if(!state.paused) {
            state.time += deltaTime;
        }
	}
}
