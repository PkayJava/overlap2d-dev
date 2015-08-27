package com.o2d.pkayjava.runtime.systems.render.logic;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.NinePatchComponent;
import com.o2d.pkayjava.runtime.components.TintComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

public class NinePatchDrawableLogic implements com.o2d.pkayjava.runtime.systems.render.logic.Drawable {

	private ComponentMapper<com.o2d.pkayjava.runtime.components.TintComponent> tintComponentComponentMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TransformComponent> transformMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.DimensionsComponent> dimensionsMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.NinePatchComponent> ninePatchMapper;


	public NinePatchDrawableLogic() {
		tintComponentComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TintComponent.class);
		transformMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class);
		dimensionsMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
		ninePatchMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.NinePatchComponent.class);
	}

	@Override
	public void draw(Batch batch, Entity entity) {
		com.o2d.pkayjava.runtime.components.TintComponent tintComponent = tintComponentComponentMapper.get(entity);
		com.o2d.pkayjava.runtime.components.TransformComponent entityTransformComponent = transformMapper.get(entity);
		com.o2d.pkayjava.runtime.components.DimensionsComponent entityDimensionsComponent = dimensionsMapper.get(entity);
		com.o2d.pkayjava.runtime.components.NinePatchComponent entityNinePatchComponent = ninePatchMapper.get(entity);
		batch.setColor(tintComponent.color);

		entityNinePatchComponent.ninePatch.draw(batch, entityTransformComponent.x, entityTransformComponent.y, entityDimensionsComponent.width, entityDimensionsComponent.height);
	}

}
