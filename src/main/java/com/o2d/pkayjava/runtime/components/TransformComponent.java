package com.o2d.pkayjava.runtime.components;

import com.badlogic.ashley.core.Component;

public class TransformComponent implements Component {
    private float x;
    private float y;
    private float scaleX = 1f;
    private float scaleY = 1f;
    private float rotation;
    private float originX;
    private float originY;

    TransformComponent backup = null;

    public TransformComponent() {
    }

    public TransformComponent(TransformComponent component) {
        setX(component.getX());
        setY(component.getY());
        setScaleX(component.getScaleX());
        setScaleY(component.getScaleY());
        setRotation(component.getRotation());
        setOriginX(component.getOriginX());
        setOriginY(component.getOriginY());
    }

    public void disableTransform() {
        backup = new TransformComponent(this);
        setX(0);
        setY(0);
        setScaleX(1f);
        setScaleY(1f);
        setRotation(0);
    }

    public void enableTransform() {
        if (backup == null) return;
        setX(backup.getX());
        setY(backup.getY());
        setScaleX(backup.getScaleX());
        setScaleY(backup.getScaleY());
        setRotation(backup.getRotation());
        setOriginX(backup.getOriginX());
        setOriginY(backup.getOriginY());
        backup = null;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        this.originX = originX;
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        this.originY = originY;
    }
}
