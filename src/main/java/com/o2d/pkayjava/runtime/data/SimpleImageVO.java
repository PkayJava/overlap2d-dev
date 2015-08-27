package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.NinePatchComponent;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;

public class SimpleImageVO extends com.o2d.pkayjava.runtime.data.MainItemVO {
	public String imageName = "";
    public boolean isRepeat = false;
    public boolean isPolygon = false;
	
	public SimpleImageVO() {
		super();
	}
	
	public SimpleImageVO(SimpleImageVO vo) {
		super(vo);
		imageName = new String(vo.imageName);
	}

	@Override
	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);

		com.o2d.pkayjava.runtime.components.TextureRegionComponent textureRegionComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.TextureRegionComponent.class);
		imageName = textureRegionComponent.regionName;
        isRepeat = textureRegionComponent.isRepeat;
        isPolygon = textureRegionComponent.isPolygon;
	}
}
