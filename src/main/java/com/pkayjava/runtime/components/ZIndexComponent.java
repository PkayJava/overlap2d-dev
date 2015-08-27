package com.pkayjava.runtime.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by socheatkhauv on 8/27/15.
 */
public class ZIndexComponent implements Component {
    private int zIndex = 0;
    public boolean needReOrder = false;
    public String layerName = "";
    public int layerIndex;

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
        needReOrder = true;
    }
}
