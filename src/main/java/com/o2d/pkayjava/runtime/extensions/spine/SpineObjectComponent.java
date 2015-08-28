package com.o2d.pkayjava.runtime.extensions.spine;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.attachments.Attachment;
import com.esotericsoftware.spine.attachments.RegionAttachment;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;

public class SpineObjectComponent implements Component {
	public SkeletonData skeletonData;
	public Skeleton skeleton;
	public SkeletonJson skeletonJson;
    public AnimationState state;
    public float minX;
    public float minY;


    public Array<Animation> getAnimations() {
        return skeletonData.getAnimations();
    }

    public void setAnimation(String animName) {
        state.setAnimation(0, animName, true);
    }

    public AnimationState getState() {
        return state;
    }

    public void computeBoundBox(DimensionsComponent dimensionsComponent) {
        skeleton.updateWorldTransform();
        minX = Float.MAX_VALUE;
        minY = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
        for (int i = 0, n = skeleton.getSlots().size; i < n; i++) {
            Slot slot = skeleton.getSlots().get(i);
            Attachment attachment = slot.getAttachment();
            if (attachment == null) continue;
            if (!(attachment instanceof RegionAttachment)) continue;
            RegionAttachment imageRegion = (RegionAttachment) attachment;
            imageRegion.updateWorldVertices(slot, false);
            float[] vertices = imageRegion.getWorldVertices();
            for (int ii = 0, nn = vertices.length; ii < nn; ii += 5) {
                minX = Math.min(minX, vertices[ii]);
                minY = Math.min(minY, vertices[ii + 1]);
                maxX = Math.max(maxX, vertices[ii]);
                maxY = Math.max(maxY, vertices[ii + 1]);
            }
        }

        dimensionsComponent.width = (maxX - minX);
        dimensionsComponent.height = (maxY - minY);
    }
}