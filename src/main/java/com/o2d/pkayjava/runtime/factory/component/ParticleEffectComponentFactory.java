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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.particle.ParticleComponent;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.data.ParticleEffectVO;
import com.o2d.pkayjava.runtime.data.ProjectInfoVO;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.factory.component.*;
import com.o2d.pkayjava.runtime.factory.component.ComponentFactory;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

/**
 * Created by azakhary on 5/22/2015.
 */
public class ParticleEffectComponentFactory extends com.o2d.pkayjava.runtime.factory.component.ComponentFactory {


    public ParticleEffectComponentFactory(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        super(rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        createCommonComponents(entity, vo, com.o2d.pkayjava.runtime.factory.EntityFactory.PARTICLE_TYPE);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        createParticleComponent(entity, (com.o2d.pkayjava.runtime.data.ParticleEffectVO) vo);
    }

    @Override
    protected com.o2d.pkayjava.runtime.components.DimensionsComponent createDimensionsComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
        com.o2d.pkayjava.runtime.components.DimensionsComponent component = new com.o2d.pkayjava.runtime.components.DimensionsComponent();

        com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = rm.getProjectVO();
        float boundBoxSize = 70f;
        component.boundBox = new Rectangle((-boundBoxSize/2f)/projectInfoVO.pixelToWorld, (-boundBoxSize/2f)/projectInfoVO.pixelToWorld, boundBoxSize/projectInfoVO.pixelToWorld, boundBoxSize/projectInfoVO.pixelToWorld);

        entity.add(component);
        return component;
    }

    protected com.o2d.pkayjava.runtime.components.particle.ParticleComponent createParticleComponent(Entity entity, com.o2d.pkayjava.runtime.data.ParticleEffectVO vo) {
        com.o2d.pkayjava.runtime.components.particle.ParticleComponent component = new com.o2d.pkayjava.runtime.components.particle.ParticleComponent();
        component.particleName = vo.particleName;
		ParticleEffect particleEffect = new ParticleEffect(rm.getParticleEffect(vo.particleName));
        component.particleEffect = particleEffect;

        com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = rm.getProjectVO();

        component.worldMultiplyer = 1f/projectInfoVO.pixelToWorld;

        entity.add(component);
        return component;
    }
}
