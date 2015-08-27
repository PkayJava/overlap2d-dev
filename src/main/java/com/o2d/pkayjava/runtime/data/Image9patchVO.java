package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.NinePatchComponent;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;

public class Image9patchVO extends com.o2d.pkayjava.runtime.data.MainItemVO {

    public String imageName = "";
    public float width = 0;
    public float height = 0;

    public Image9patchVO() {
        super();
    }

    public Image9patchVO(Image9patchVO vo) {
        super(vo);
        imageName = new String(vo.imageName);
        width = vo.width;
        height = vo.height;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        com.o2d.pkayjava.runtime.components.NinePatchComponent ninePatchComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.NinePatchComponent.class);
        com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
        imageName = ninePatchComponent.textureRegionName;

        width = dimensionsComponent.width;
        height = dimensionsComponent.height;
    }
}
