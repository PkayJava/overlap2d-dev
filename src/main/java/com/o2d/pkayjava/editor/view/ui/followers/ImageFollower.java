package com.o2d.pkayjava.editor.view.ui.followers;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.ui.followers.*;
import com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower;

/**
 * Created by CyberJoe on 8/2/2015.
 */
public class ImageFollower extends NormalSelectionFollower {


    public ImageFollower(Entity entity) {
        super(entity);
    }

    @Override
    public void update() {
        TextureRegionComponent textureRegionComponent = ComponentRetriever.get(getEntity(), TextureRegionComponent.class);
        if(textureRegionComponent.isPolygon) {
            pixelRect.setVisible(false);
        } else {
            pixelRect.setVisible(true);
        }
        super.update();
    }
}
