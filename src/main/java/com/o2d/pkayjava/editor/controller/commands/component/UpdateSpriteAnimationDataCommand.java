package com.o2d.pkayjava.editor.controller.commands.component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.o2d.pkayjava.editor.controller.commands.EntityModifyRevertableCommand;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationComponent;
import com.o2d.pkayjava.runtime.components.sprite.SpriteAnimationStateComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;

/**
 * Created by CyberJoe on 6/18/2015.
 */
public class UpdateSpriteAnimationDataCommand extends EntityModifyRevertableCommand {

    private Integer entityId;

    private int previousFps;
    private String previousAnimationName;

    @Override
    public void doAction() {
        Object[] payload = getNotification().getBody();
        Entity entity = (Entity) payload[0];
        entityId = EntityUtils.getEntityId(entity);

        int fps = (int) payload[1];
        String animName = (String) payload[2];
        Animation.PlayMode playMode = (Animation.PlayMode) payload[3];

        SpriteAnimationComponent spriteAnimationComponent = ComponentRetriever.get(entity, SpriteAnimationComponent.class);
        SpriteAnimationStateComponent spriteAnimationStateComponent = ComponentRetriever.get(entity, SpriteAnimationStateComponent.class);
        previousFps = spriteAnimationComponent.fps;
        previousAnimationName = spriteAnimationComponent.currentAnimation;
        spriteAnimationComponent.fps = fps;
        spriteAnimationComponent.currentAnimation = animName;
        spriteAnimationComponent.playMode = playMode;
        spriteAnimationStateComponent.set(spriteAnimationComponent);
    }

    @Override
    public void undoAction() {
        Entity entity = EntityUtils.getByUniqueId(entityId);

        SpriteAnimationComponent spriteAnimationComponent = ComponentRetriever.get(entity, SpriteAnimationComponent.class);
        SpriteAnimationStateComponent spriteAnimationStateComponent = ComponentRetriever.get(entity, SpriteAnimationStateComponent.class);
        spriteAnimationComponent.fps = previousFps;
        spriteAnimationComponent.currentAnimation = previousAnimationName;
        spriteAnimationStateComponent.set(spriteAnimationComponent);
    }

    public static Object payload(Entity entity, int fps, String animName, Animation.PlayMode playMode) {
        Object[] payload = new Object[4];
        payload[0] = entity;
        payload[1] = fps;
        payload[2] = animName;
        payload[3] = playMode;

        return payload;
    }
}
