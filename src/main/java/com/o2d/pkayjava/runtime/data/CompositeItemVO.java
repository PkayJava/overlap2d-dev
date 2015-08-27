package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.data.*;
import com.o2d.pkayjava.runtime.data.CompositeVO;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

public class CompositeItemVO extends com.o2d.pkayjava.runtime.data.MainItemVO {

	public com.o2d.pkayjava.runtime.data.CompositeVO composite;

	public float scissorX;
	public float scissorY;
	public float scissorWidth;
	public float scissorHeight;

	public float width;
	public float height;

	public CompositeItemVO() {
		composite = new com.o2d.pkayjava.runtime.data.CompositeVO();
	}

	public CompositeItemVO(com.o2d.pkayjava.runtime.data.CompositeVO vo) {
		composite = new com.o2d.pkayjava.runtime.data.CompositeVO(vo);
	}

	public CompositeItemVO(CompositeItemVO vo) {
		super(vo);
		composite = new com.o2d.pkayjava.runtime.data.CompositeVO(vo.composite);
	}

	public void update(CompositeItemVO vo) {
		composite = new com.o2d.pkayjava.runtime.data.CompositeVO(vo.composite);
	}
	
	public CompositeItemVO clone() {
		CompositeItemVO tmp = new CompositeItemVO();
		tmp.composite = composite;
        tmp.itemName = itemName;
        tmp.layerName = layerName;
        tmp.rotation = rotation;
        tmp.tint = tint;
        tmp.x = x;
        tmp.y = y;
        tmp.zIndex = zIndex;

        tmp.scissorX = scissorX;
        tmp.scissorY = scissorY;
        tmp.scissorWidth = scissorWidth;
        tmp.scissorHeight = scissorHeight;

		tmp.width = width;
		tmp.height = height;
		
		return tmp;
	}

	@Override
	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);
		//scissorsX
		//scissorsY
		composite = new com.o2d.pkayjava.runtime.data.CompositeVO();
		composite.loadFromEntity(entity);

		com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.DimensionsComponent.class);

		width = dimensionsComponent.width;
		height = dimensionsComponent.height;
	}
}
