package com.pkayjava.runtime.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by socheatkhauv on 8/27/15.
 */
public class DimensionsComponent implements Component {
    public float width = 0;
    public float height = 0;

    public Rectangle boundBox;
    public Polygon polygon;
}
