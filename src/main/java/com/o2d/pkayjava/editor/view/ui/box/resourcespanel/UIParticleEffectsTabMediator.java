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

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.editor.proxy.ResourceManager;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.*;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.UIParticleEffectsTab;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.DraggableResource;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.list.ParticleEffectResource;

/**
 * Created by azakhary on 4/17/2015.
 */
public class UIParticleEffectsTabMediator extends UIResourcesTabMediator<UIParticleEffectsTab> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UIParticleEffectsTabMediator.class.getName();
        NAME = TAG;
    }

    public UIParticleEffectsTabMediator() {
        super(NAME, new UIParticleEffectsTab());
    }

    @Override
    protected void initList(String searchText) {
        Sandbox sandbox = Sandbox.getInstance();
        //Overlap2DFacade facade = Overlap2DFacade.getInstance();
        ResourceManager resourceManager = facade.retrieveProxy(ResourceManager.NAME);

        HashMap<String, ParticleEffect> particles = resourceManager.getProjectParticleList();
        Array<DraggableResource> itemArray = new Array<>();
        for (String name : particles.keySet()) {
            if (!name.contains(searchText)) continue;
            DraggableResource draggableResource = new DraggableResource(new ParticleEffectResource(name));
            draggableResource.setFactoryFunction(ItemFactory.get()::tryCreateParticleItem);
            itemArray.add(draggableResource);
        }
        viewComponent.setItems(itemArray);
    }
}
