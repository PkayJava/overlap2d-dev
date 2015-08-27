package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.light.LightObjectComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader;

public class LightVO extends com.o2d.pkayjava.runtime.data.MainItemVO {
	//public int itemId = -1;
	public static enum LightType {POINT, CONE}
	public LightType type;
	public int rays = 12;
	public float distance = 300;
	public float directionDegree = 0;
	public float coneDegree = 30;
	public float softnessLength = -1f;
	public boolean isStatic = true;
	public boolean isXRay = true;
	
	public LightVO() {
		tint = new float[4];
		tint[0] = 1f;
		tint[1] = 1f;
		tint[2] = 1f;
		tint[3] = 1f;
	}
	
	public LightVO(LightVO vo) {
		super(vo);
		type = vo.type;
		rays = vo.rays;
		distance = vo.distance;
		directionDegree = vo.directionDegree;
		coneDegree = vo.coneDegree;
		isStatic = vo.isStatic;
		isXRay = vo.isXRay;
		softnessLength = vo.softnessLength;
	}

	@Override
	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);

		com.o2d.pkayjava.runtime.components.light.LightObjectComponent lightObjectComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.light.LightObjectComponent.class);
		type = lightObjectComponent.getType();
		rays = lightObjectComponent.rays;
		distance = lightObjectComponent.distance;
		directionDegree = lightObjectComponent.directionDegree;
		coneDegree = lightObjectComponent.coneDegree;
		isStatic = lightObjectComponent.isStatic;
		isXRay = lightObjectComponent.isXRay;
		softnessLength = lightObjectComponent.softnessLength;
	}
}
