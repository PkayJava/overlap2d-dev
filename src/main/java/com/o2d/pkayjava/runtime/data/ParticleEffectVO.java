package com.o2d.pkayjava.runtime.data;


import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.particle.ParticleComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;

public class ParticleEffectVO extends com.o2d.pkayjava.runtime.data.MainItemVO {
	public String particleName = "";
	public float particleWidth = 100;
	public float particleHeight = 100;
	//TODO add other ParticleEffect properties 
	
	public ParticleEffectVO() {
		super();
	}
	
	public ParticleEffectVO(ParticleEffectVO vo) {
		super(vo);
		particleName = new String(vo.particleName);
	}

	@Override
	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);

		com.o2d.pkayjava.runtime.components.particle.ParticleComponent particleComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.particle.ParticleComponent.class);
		particleName = particleComponent.particleName;
	}
}
