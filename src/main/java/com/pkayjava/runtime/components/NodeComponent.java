package com.pkayjava.runtime.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by socheatkhauv on 8/27/15.
 */
public class NodeComponent implements Component {

    public SnapshotArray<Entity> children;
}
