package com.o2d.pkayjava.runtime.systems.render.logic;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.brashmonkey.spriter.Player;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterComponent;
import com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

public class SpriterDrawableLogic implements com.o2d.pkayjava.runtime.systems.render.logic.Drawable {

    private ComponentMapper<com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent> spriterDrawerMapper;
    private ComponentMapper<com.o2d.pkayjava.runtime.components.spriter.SpriterComponent> spriterMapper;
    private ComponentMapper<com.o2d.pkayjava.runtime.components.TransformComponent> transformMapper;

    public SpriterDrawableLogic() {
        spriterDrawerMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent.class);
        spriterMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.spriter.SpriterComponent.class);
        transformMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class);
    }

    @Override
    public void draw(Batch batch, Entity entity) {
        com.o2d.pkayjava.runtime.components.TransformComponent entityTransformComponent = transformMapper.get(entity);
        com.o2d.pkayjava.runtime.components.spriter.SpriterDrawerComponent spriterDrawerComponent = spriterDrawerMapper.get(entity);
        com.o2d.pkayjava.runtime.components.spriter.SpriterComponent spriter = spriterMapper.get(entity);
        Player player = spriter.player;

        player.setPosition(entityTransformComponent.getX(), entityTransformComponent.getY());
        //TODO dimentions
        //player.setPivot(getWidth() / 2, getHeight() / 2);
        player.setScale(spriter.scale);
        player.rotate(entityTransformComponent.getRotation() - player.getAngle());
        player.update();
        spriterDrawerComponent.drawer.beforeDraw(player, batch);
    }

}
