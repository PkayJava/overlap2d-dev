package com.o2d.pkayjava.runtime.data;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.PolygonComponent;
import com.o2d.pkayjava.runtime.components.ShaderComponent;
import com.o2d.pkayjava.runtime.components.TintComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.ZIndexComponent;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.data.PhysicsBodyDataVO;
import com.o2d.pkayjava.runtime.data.ShapeVO;

import java.util.Arrays;


public class MainItemVO {
    public int uniqueId = -1;
    public String itemIdentifier = "";
    public String itemName = "";
    public String[] tags = null;
    public String customVars = "";
    public float x;
    public float y;
    public float scaleX = 1f;
    public float scaleY = 1f;
    public float originX = Float.NaN;
    public float originY = Float.NaN;
    public float rotation;
    public int zIndex = 0;
    public String layerName = "";
    public float[] tint = {1, 1, 1, 1};

    public String shaderName = "";

    public com.o2d.pkayjava.runtime.data.ShapeVO shape = null;
    public com.o2d.pkayjava.runtime.data.PhysicsBodyDataVO physics = null;

    public MainItemVO() {

    }

    public MainItemVO(MainItemVO vo) {
        uniqueId = vo.uniqueId;
        itemIdentifier = new String(vo.itemIdentifier);
        itemName = new String(vo.itemName);
        if (tags != null) tags = Arrays.copyOf(vo.tags, vo.tags.length);
        customVars = new String(vo.customVars);
        x = vo.x;
        y = vo.y;
        rotation = vo.rotation;
        zIndex = vo.zIndex;
        layerName = new String(vo.layerName);
        if (vo.tint != null) tint = Arrays.copyOf(vo.tint, vo.tint.length);
        scaleX = vo.scaleX;
        scaleY = vo.scaleY;
        originX = vo.originX;
        originY = vo.originY;

        if (vo.shape != null) {
            shape = vo.shape.clone();
        }

        if (vo.physics != null) {
            physics = new com.o2d.pkayjava.runtime.data.PhysicsBodyDataVO(vo.physics);
        }
    }

    public void loadFromEntity(Entity entity) {
        com.o2d.pkayjava.runtime.components.MainItemComponent mainItemComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.MainItemComponent.class);
        com.o2d.pkayjava.runtime.components.TransformComponent transformComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.TransformComponent.class);
        com.o2d.pkayjava.runtime.components.TintComponent tintComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.TintComponent.class);
        com.o2d.pkayjava.runtime.components.ZIndexComponent zindexComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.ZIndexComponent.class);

        uniqueId = mainItemComponent.uniqueId;
        itemIdentifier = mainItemComponent.itemIdentifier;
        itemName = mainItemComponent.libraryLink;
        tags = new String[mainItemComponent.tags.size()];
        tags = mainItemComponent.tags.toArray(tags);
        customVars = mainItemComponent.customVars;

        x = transformComponent.getX();
        y = transformComponent.getY();
        scaleX = transformComponent.getScaleX();
        scaleY = transformComponent.getScaleY();
        originX = transformComponent.getOriginX();
        originY = transformComponent.getOriginY();
        rotation = transformComponent.getRotation();

        layerName = zindexComponent.layerName;

        tint = new float[4];
        tint[0] = tintComponent.color.r;
        tint[1] = tintComponent.color.g;
        tint[2] = tintComponent.color.b;
        tint[3] = tintComponent.color.a;

        zIndex = zindexComponent.getZIndex();

        /**
         * Secondary components
         */
        com.o2d.pkayjava.runtime.components.PolygonComponent polygonComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.PolygonComponent.class);
        if (polygonComponent != null && polygonComponent.vertices != null) {
            shape = new com.o2d.pkayjava.runtime.data.ShapeVO();
            shape.polygons = polygonComponent.vertices;
        }
        com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent physicsComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent.class);
        if (physicsComponent != null) {
            physics = new com.o2d.pkayjava.runtime.data.PhysicsBodyDataVO();
            physics.loadFromComponent(physicsComponent);
        }

        com.o2d.pkayjava.runtime.components.ShaderComponent shaderComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.ShaderComponent.class);
        if (shaderComponent != null && shaderComponent.shaderName != null) {
            shaderName = shaderComponent.shaderName;
        }
    }
}
