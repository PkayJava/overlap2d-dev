package com.o2d.pkayjava.runtime.systems.render.logic;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.ShaderComponent;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.components.TintComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

public class TexturRegionDrawLogic implements com.o2d.pkayjava.runtime.systems.render.logic.Drawable {

	private ComponentMapper<com.o2d.pkayjava.runtime.components.TintComponent> tintComponentComponentMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TextureRegionComponent> textureRegionMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TransformComponent> transformMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.DimensionsComponent> dimensionsComponentComponentMapper;
	private ComponentMapper<com.o2d.pkayjava.runtime.components.ShaderComponent> shaderComponentMapper;

	public TexturRegionDrawLogic() {
		tintComponentComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TintComponent.class);
		textureRegionMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TextureRegionComponent.class);
		transformMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class);
		dimensionsComponentComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
		shaderComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ShaderComponent.class);
	}

	@Override
	public void draw(Batch batch, Entity entity) {
        com.o2d.pkayjava.runtime.components.TextureRegionComponent entityTextureRegionComponent = textureRegionMapper.get(entity);
        if(shaderComponentMapper.has(entity)){
			com.o2d.pkayjava.runtime.components.ShaderComponent shaderComponent = shaderComponentMapper.get(entity);
            if(shaderComponent.getShader() != null) {
                batch.setShader(shaderComponent.getShader());
                //System.out.println("asdasdsdas" + new Vector2(entityTextureRegionComponent.region.getRegionX(), entityTextureRegionComponent.region.getRegionY()));

                GL20 gl = Gdx.gl20;
                int error;
                if ((error = gl.glGetError()) != GL20.GL_NO_ERROR) {
                    Gdx.app.log("opengl", "Error: " + error);
                    Gdx.app.log("opengl", shaderComponent.getShader().getLog());
                    //throw new RuntimeException( ": glError " + error);
                }
            }
		}
        
        if(entityTextureRegionComponent.polygonSprite != null) {
//            if(entityTextureRegionComponent.isRepeat) {
            	drawTiledPolygonSprite(batch, entity);
//            } else {
//                drawPolygonSprite(batch, entity);
//            }
        } else {
            drawSprite(batch, entity);
        }

        if(shaderComponentMapper.has(entity)){
			batch.setShader(null);
		}
	}

    public void drawSprite(Batch batch, Entity entity) {
        com.o2d.pkayjava.runtime.components.TintComponent tintComponent = tintComponentComponentMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TransformComponent entityTransformComponent = transformMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TextureRegionComponent entityTextureRegionComponent = textureRegionMapper.get(entity);
        com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = dimensionsComponentComponentMapper.get(entity);
        batch.setColor(tintComponent.color);

        batch.draw(entityTextureRegionComponent.region,
                entityTransformComponent.x, entityTransformComponent.y,
                entityTransformComponent.originX, entityTransformComponent.originY,
                dimensionsComponent.width, dimensionsComponent.height,
                entityTransformComponent.scaleX, entityTransformComponent.scaleY,
                entityTransformComponent.rotation);
    }

    public void drawPolygonSprite(Batch batch, Entity entity) {
        com.o2d.pkayjava.runtime.components.TintComponent tintComponent = tintComponentComponentMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TransformComponent entityTransformComponent = transformMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TextureRegionComponent entityTextureRegionComponent = textureRegionMapper.get(entity);

        com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = dimensionsComponentComponentMapper.get(entity);

        entityTextureRegionComponent.polygonSprite.setPosition(entityTransformComponent.x, entityTransformComponent.y);
        entityTextureRegionComponent.polygonSprite.setRotation(entityTransformComponent.rotation);
        entityTextureRegionComponent.polygonSprite.setOrigin(entityTransformComponent.originX, entityTransformComponent.originY);
        entityTextureRegionComponent.polygonSprite.setColor(tintComponent.color);
        entityTextureRegionComponent.polygonSprite.draw((PolygonSpriteBatch) batch);
    }

    public void drawTiledPolygonSprite(Batch batch, Entity entity) {
    	batch.flush();
        com.o2d.pkayjava.runtime.components.TintComponent tintComponent = tintComponentComponentMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TransformComponent entityTransformComponent = transformMapper.get(entity);
        com.o2d.pkayjava.runtime.components.TextureRegionComponent entityTextureRegionComponent = textureRegionMapper.get(entity);
        
        com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = dimensionsComponentComponentMapper.get(entity);
        float ppwu = dimensionsComponent.width/entityTextureRegionComponent.region.getRegionWidth();

        Vector2 atlasCoordsVector = new Vector2(entityTextureRegionComponent.region.getU(), entityTextureRegionComponent.region.getV());
        Vector2 atlasSizeVector = new Vector2(entityTextureRegionComponent.region.getU2()-entityTextureRegionComponent.region.getU(), entityTextureRegionComponent.region.getV2()-entityTextureRegionComponent.region.getV());
        
        batch.getShader().setUniformi("isRepeat", 1);
        batch.getShader().setUniformf("atlasCoord", atlasCoordsVector);
    	batch.getShader().setUniformf("atlasSize", atlasSizeVector);
        
        batch.setColor(tintComponent.color);
        entityTextureRegionComponent.polygonSprite.setOrigin(entityTransformComponent.originX, entityTransformComponent.originY);
        entityTextureRegionComponent.polygonSprite.setPosition(entityTransformComponent.x, entityTransformComponent.y);
        entityTextureRegionComponent.polygonSprite.setRotation(entityTransformComponent.rotation);
        entityTextureRegionComponent.polygonSprite.setScale(ppwu);
        entityTextureRegionComponent.polygonSprite.draw((PolygonSpriteBatch) batch);
        batch.flush();
        batch.getShader().setUniformi("isRepeat", 0);
       
    }
}
