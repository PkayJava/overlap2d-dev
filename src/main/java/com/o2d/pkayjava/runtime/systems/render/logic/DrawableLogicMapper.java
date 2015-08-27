package com.o2d.pkayjava.runtime.systems.render.logic;

import java.util.HashMap;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.systems.render.logic.*;
import com.o2d.pkayjava.runtime.systems.render.logic.SpriteDrawableLogic;
import com.o2d.pkayjava.runtime.systems.render.logic.SpriterDrawableLogic;
import com.o2d.pkayjava.runtime.systems.render.logic.TexturRegionDrawLogic;

public class DrawableLogicMapper {

	private HashMap<Integer, Drawable> logicClassMap;

	public DrawableLogicMapper() {
		logicClassMap = new HashMap<Integer, Drawable>(6);
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.IMAGE_TYPE, 	new com.o2d.pkayjava.runtime.systems.render.logic.TexturRegionDrawLogic());
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.LABEL_TYPE, 	new LabelDrawableLogic());
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.NINE_PATCH, 	new NinePatchDrawableLogic());
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.PARTICLE_TYPE, 	new ParticleDrawableLogic());
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.SPRITE_TYPE, 	new com.o2d.pkayjava.runtime.systems.render.logic.SpriteDrawableLogic());
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.SPRITER_TYPE, 	new com.o2d.pkayjava.runtime.systems.render.logic.SpriterDrawableLogic());
		//TODO
		logicClassMap.put(com.o2d.pkayjava.runtime.factory.EntityFactory.LIGHT_TYPE, 	new Drawable() {@Override public void draw(Batch batch, Entity entity) {}}); //Empty drawable for not checking on null
	}

	public void addDrawableToMap(int type, Drawable drawable) {
		logicClassMap.put(type, drawable);
	}

	public Drawable getDrawable(int type){
		return logicClassMap.get(type);
	}
}
