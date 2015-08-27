package com.o2d.pkayjava.runtime.extensions.spine;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

public class SpineDrawableLogic implements Drawable {

    private ComponentMapper<SpineObjectComponent> spineMapper;
    private SkeletonRenderer skeletonRenderer;

    public SpineDrawableLogic() {
        spineMapper = ComponentMapper.getFor(SpineObjectComponent.class);
        skeletonRenderer = new SkeletonRenderer();
    }

    @Override
    public void draw(Batch batch, Entity entity) {
        SpineObjectComponent spineObjectComponent = spineMapper.get(entity);
        //TODO parent alpha thing
        skeletonRenderer.draw(batch, spineObjectComponent.skeleton);
    }

}
