package com.o2d.pkayjava.runtime.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ZIndexComponent;
import com.o2d.pkayjava.runtime.components.additional.ButtonComponent;import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.runtime.utils.TransformMathUtils;

/**
 * Created by azakhary on 8/1/2015.
 */
public class ButtonSystem extends IteratingSystem {


    public ButtonSystem() {
        super(Family.all(com.o2d.pkayjava.runtime.components.additional.ButtonComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        com.o2d.pkayjava.runtime.components.NodeComponent nodeComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.NodeComponent.class);

        if(nodeComponent == null) return;

        if(isTouched(entity)) {
            for (int i = 0; i < nodeComponent.children.size; i++) {
                Entity childEntity = nodeComponent.children.get(i);
                com.o2d.pkayjava.runtime.components.MainItemComponent childMainItemComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(childEntity, com.o2d.pkayjava.runtime.components.MainItemComponent.class);
                com.o2d.pkayjava.runtime.components.ZIndexComponent childZComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(childEntity, com.o2d.pkayjava.runtime.components.ZIndexComponent.class);
                if(childZComponent.layerName.equals("normal")) {
                    childMainItemComponent.visible = false;
                }
                if(childZComponent.layerName.equals("pressed")) {
                    childMainItemComponent.visible = true;
                }
            }
        } else {
            for (int i = 0; i < nodeComponent.children.size; i++) {
                Entity childEntity = nodeComponent.children.get(i);
                com.o2d.pkayjava.runtime.components.MainItemComponent childMainItemComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(childEntity, com.o2d.pkayjava.runtime.components.MainItemComponent.class);
                com.o2d.pkayjava.runtime.components.ZIndexComponent childZComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(childEntity, com.o2d.pkayjava.runtime.components.ZIndexComponent.class);
                if(childZComponent.layerName.equals("normal")) {
                    childMainItemComponent.visible = true;
                }
                if(childZComponent.layerName.equals("pressed")) {
                    childMainItemComponent.visible = false;
                }
            }
        }
    }

    private boolean isTouched(Entity entity) {
        com.o2d.pkayjava.runtime.components.additional.ButtonComponent buttonComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.additional.ButtonComponent.class);
        if(Gdx.input.isTouched()) {
            com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
            Vector2 localCoordinates  = new Vector2(Gdx.input.getX(), Gdx.input.getY());

            com.o2d.pkayjava.runtime.utils.TransformMathUtils.globalToLocalCoordinates(entity, localCoordinates);

            if(dimensionsComponent.hit(localCoordinates.x, localCoordinates.y)) {
                buttonComponent.setTouchState(true);
                return true;
            }
        }
        buttonComponent.setTouchState(false);
        return false;
    }
}
