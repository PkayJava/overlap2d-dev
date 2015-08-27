package com.o2d.pkayjava.runtime.systems.render.logic;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.ShaderComponent;
import com.o2d.pkayjava.runtime.components.TintComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.label.LabelComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

public class LabelDrawableLogic implements com.o2d.pkayjava.runtime.systems.render.logic.Drawable {

	private ComponentMapper<com.o2d.pkayjava.runtime.components.label.LabelComponent> labelComponentMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TintComponent> tintComponentMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.DimensionsComponent> dimensionsComponentMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TransformComponent> transformMapper;

	private final Color tmpColor = new Color();

	public LabelDrawableLogic() {
		labelComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.label.LabelComponent.class);
		tintComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TintComponent.class);
		dimensionsComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
		transformMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class);
	}
	
	@Override
	public void draw(Batch batch, Entity entity) {
		com.o2d.pkayjava.runtime.components.TransformComponent entityTransformComponent = transformMapper.get(entity);
		com.o2d.pkayjava.runtime.components.label.LabelComponent labelComponent = labelComponentMapper.get(entity);
		com.o2d.pkayjava.runtime.components.DimensionsComponent dimenstionsComponent = dimensionsComponentMapper.get(entity);
		com.o2d.pkayjava.runtime.components.TintComponent tint = tintComponentMapper.get(entity);

		tmpColor.set(tint.color);

		if (labelComponent.style.background != null) {
			batch.setColor(tmpColor);
			labelComponent.style.background.draw(batch, entityTransformComponent.x, entityTransformComponent.y, dimenstionsComponent.width, dimenstionsComponent.height);
			//System.out.println("LAbel BG");
		}

		if(labelComponent.style.fontColor != null) tmpColor.mul(labelComponent.style.fontColor);
		//tmpColor.a *= TODO consider parent alpha

		labelComponent.cache.tint(tmpColor);
		labelComponent.cache.setPosition(entityTransformComponent.x, entityTransformComponent.y);
		labelComponent.cache.draw(batch);
	}

}
