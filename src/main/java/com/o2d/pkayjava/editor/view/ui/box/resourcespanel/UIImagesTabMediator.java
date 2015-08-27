/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.o2d.pkayjava.editor.view.ui.box.resourcespanel;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.editor.proxy.ResourceManager;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.*;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.UIImagesTab;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.DraggableResource;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.box.ImageResource;

/**
 * Created by azakhary on 4/17/2015.
 */
public class UIImagesTabMediator extends UIResourcesTabMediator<UIImagesTab> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIImagesTabMediator.class.getName();
        NAME = TAG;
    }


    public UIImagesTabMediator() {
        super(NAME, new UIImagesTab());
    }

    @Override
    protected void initList(String searchText) {
        Sandbox sandbox = Sandbox.getInstance();
        ResourceManager resourceManager = facade.retrieveProxy(ResourceManager.NAME);

        TextureAtlas atlas = resourceManager.getProjectAssetsList();

        Array<DraggableResource> thumbnailBoxes = new Array<>();
        Array<TextureAtlas.AtlasRegion> atlasRegions = atlas.getRegions();
        for (TextureAtlas.AtlasRegion region : atlasRegions) {
            if (!region.name.contains(searchText)) continue;
            boolean is9patch = region.splits != null;
            DraggableResource draggableResource = new DraggableResource(new ImageResource(region));
            if (is9patch) {
                draggableResource.setFactoryFunction(ItemFactory.get()::create9Patch);
            } else {
                draggableResource.setFactoryFunction(ItemFactory.get()::createSimpleImage);
            }
            draggableResource.initDragDrop();
            thumbnailBoxes.add(draggableResource);
        }

        viewComponent.setThumbnailBoxes(thumbnailBoxes);
    }
}
