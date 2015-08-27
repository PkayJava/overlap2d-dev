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

package com.o2d.pkayjava.runtime.factory.component;

import box2dLight.RayHandler;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.PolygonComponent;
import com.o2d.pkayjava.runtime.components.TextureRegionComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.data.ProjectInfoVO;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.runtime.data.SimpleImageVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.factory.component.ComponentFactory;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

/**
 * Created by azakhary on 5/22/2015.
 */
public class SimpleImageComponentFactory extends com.o2d.pkayjava.runtime.factory.component.ComponentFactory {

   private com.o2d.pkayjava.runtime.components.TextureRegionComponent textureRegionComponent;

    public SimpleImageComponentFactory(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        super(rayHandler, world, rm);
    }

    public void createComponents(Entity root, Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
    	textureRegionComponent = createTextureRegionComponent(entity, (com.o2d.pkayjava.runtime.data.SimpleImageVO) vo);
        createCommonComponents( entity, vo, com.o2d.pkayjava.runtime.factory.EntityFactory.IMAGE_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        updatePolygons(entity);
    }

    private void updatePolygons(Entity entity) {
    	com.o2d.pkayjava.runtime.components.TextureRegionComponent textureRegionComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.TextureRegionComponent.class);
    	com.o2d.pkayjava.runtime.components.DimensionsComponent dimensionsComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.DimensionsComponent.class);
    	
        com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = rm.getProjectVO();
    	
    	com.o2d.pkayjava.runtime.components.PolygonComponent polygonComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.PolygonComponent.class);
    	if(textureRegionComponent.isPolygon && polygonComponent != null && polygonComponent.vertices != null) {
    		textureRegionComponent.setPolygonSprite(polygonComponent, projectInfoVO.pixelToWorld);
    		dimensionsComponent.setPolygon(polygonComponent);
    	}
	}

	@Override
    protected com.o2d.pkayjava.runtime.components.DimensionsComponent createDimensionsComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.DimensionsComponent component = new com.o2d.pkayjava.runtime.components.DimensionsComponent();

        com.o2d.pkayjava.runtime.components.TextureRegionComponent textureRegionComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.TextureRegionComponent.class);
        
        com.o2d.pkayjava.runtime.data.ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = rm.getProjectVO();
        float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);
        
        component.width = (float) textureRegionComponent.region.getRegionWidth() * multiplier / projectInfoVO.pixelToWorld;
        component.height = (float) textureRegionComponent.region.getRegionHeight() * multiplier / projectInfoVO.pixelToWorld;
        entity.add(component);

        return component;
    }

    protected com.o2d.pkayjava.runtime.components.TextureRegionComponent createTextureRegionComponent(Entity entity, com.o2d.pkayjava.runtime.data.SimpleImageVO vo) {
        com.o2d.pkayjava.runtime.components.TextureRegionComponent component = new com.o2d.pkayjava.runtime.components.TextureRegionComponent();
        component.regionName = vo.imageName;
        component.region = rm.getTextureRegion(vo.imageName);
        component.isRepeat = vo.isRepeat;
        component.isPolygon = vo.isPolygon;
        entity.add(component);

        return component;
    }
}
