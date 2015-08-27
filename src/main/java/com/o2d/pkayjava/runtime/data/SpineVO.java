package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.SpineDataComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;

public class SpineVO extends com.o2d.pkayjava.runtime.data.MainItemVO {

    public String animationName = "";
    public String currentAnimationName = "";

    public SpineVO() {

    }

    public SpineVO(SpineVO vo) {
        super(vo);
        animationName = vo.animationName;
        currentAnimationName = vo.currentAnimationName;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        com.o2d.pkayjava.runtime.components.SpineDataComponent spineDataComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.SpineDataComponent.class);
        animationName = spineDataComponent.animationName;
        currentAnimationName = spineDataComponent.currentAnimationName;
    }
}
