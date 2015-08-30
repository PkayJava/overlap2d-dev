package com.o2d.pkayjava.runtime.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.SnapshotArray;
import com.o2d.pkayjava.runtime.components.CompositeTransformComponent;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;

public class CompositeSystem extends IteratingSystem {

	private ComponentMapper<DimensionsComponent> dimensionsMapper;
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<NodeComponent> nodeMapper;
	
	private DimensionsComponent dimensionsComponent;
	private TransformComponent transformComponent;
	private NodeComponent nodeComponent;
	
	public CompositeSystem() {
		super(Family.all(CompositeTransformComponent.class).get());
		dimensionsMapper = ComponentMapper.getFor(DimensionsComponent.class);
		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		nodeMapper = ComponentMapper.getFor(NodeComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		dimensionsComponent = dimensionsMapper.get(entity);
		nodeComponent = nodeMapper.get(entity);
		recalculateSize();
	}
	
	public void recalculateSize() {
        float lowerX = 0, lowerY = 0, upperX = 0, upperY = 0;
        SnapshotArray<Entity> entities = nodeComponent.children;
        for (int i = 0; i < entities.size; i++) {
            Entity entity = entities.get(i);
            transformComponent = transformMapper.get(entity);
            DimensionsComponent childDimentionsComponent = dimensionsMapper.get(entity);
            if (i == 0) {
                if (transformComponent.getScaleX() > 0 && childDimentionsComponent.width * transformComponent.getScaleX() > 0) {
                    lowerX = transformComponent.getX();
                    upperX = transformComponent.getX() + childDimentionsComponent.width * transformComponent.getScaleX();
                } else {
                    upperX = transformComponent.getX();
                    lowerX = transformComponent.getX() + childDimentionsComponent.width * transformComponent.getScaleX();
                }

                if (transformComponent.getScaleY() > 0 && childDimentionsComponent.height * transformComponent.getScaleY() > 0) {
                    lowerY = transformComponent.getY();
                    upperY = transformComponent.getY() + childDimentionsComponent.height * transformComponent.getScaleY();
                } else {
                    upperY = transformComponent.getY();
                    lowerY = transformComponent.getY() + childDimentionsComponent.height * transformComponent.getScaleY();
                }
            }
            if (transformComponent.getScaleX() > 0 && childDimentionsComponent.width > 0) {
                if (lowerX > transformComponent.getX()) lowerX = transformComponent.getX();
                if (upperX < transformComponent.getX() + childDimentionsComponent.width * transformComponent.getScaleX()) upperX = transformComponent.getX() + childDimentionsComponent.width * transformComponent.getScaleX();
            } else {
                if (upperX < transformComponent.getX()) upperX = transformComponent.getX();
                if (lowerX > transformComponent.getX() + childDimentionsComponent.width * transformComponent.getScaleX()) lowerX = transformComponent.getX() + childDimentionsComponent.width * transformComponent.getScaleX();
            }
            if (transformComponent.getScaleY() > 0 && childDimentionsComponent.height * transformComponent.getScaleY() > 0) {
                if (lowerY > transformComponent.getY()) lowerY = transformComponent.getY();
                if (upperY < transformComponent.getY() + childDimentionsComponent.height * transformComponent.getScaleY()) upperY = transformComponent.getY() + childDimentionsComponent.height * transformComponent.getScaleY();
            } else {
                if (upperY < transformComponent.getY()) upperY = transformComponent.getY();
                if (lowerY > transformComponent.getY() + childDimentionsComponent.height * transformComponent.getScaleY()) lowerY = transformComponent.getY() + childDimentionsComponent.height * transformComponent.getScaleY();
            }

        }

        dimensionsComponent.width = (upperX - 0);
        dimensionsComponent.height = (upperY - 0);
        //System.out.println("AFTER RESIZE width=" + dimensionsComponent.width + " height=" + dimensionsComponent.height);
    }

}
