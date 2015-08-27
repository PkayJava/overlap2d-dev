package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.spriter.SpriterComponent;
import com.o2d.pkayjava.runtime.data.*;
import com.o2d.pkayjava.runtime.data.MainItemVO;

public class SpriterVO extends com.o2d.pkayjava.runtime.data.MainItemVO {

    public int 	entity;
    public int 	animation;
    public String animationName = "";

    //wtf is this?
    public float scale	=	1f;

    public SpriterVO() {

    }

    public SpriterVO(SpriterVO vo) {
        super(vo);
        entity 			= vo.entity;
        animation		= vo.animation;
        animationName 	= vo.animationName;
        scale 			= vo.scale;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        com.o2d.pkayjava.runtime.components.spriter.SpriterComponent spriterComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.spriter.SpriterComponent.class);
        animationName = spriterComponent.animationName;
        animation = spriterComponent.animation;
    }

}
