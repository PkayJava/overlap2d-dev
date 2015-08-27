package com.o2d.pkayjava.runtime.data;

import box2dLight.RayHandler;

import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;

public class Essentials {

    public RayHandler rayHandler;
    //public SkeletonRenderer skeletonRenderer;
    public com.o2d.pkayjava.runtime.resources.IResourceRetriever rm;
    public World world;
    public boolean physicsStopped = false;

}
