package com.o2d.pkayjava.runtime.factory.component;

import box2dLight.RayHandler;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.components.DimensionsComponent;
import com.o2d.pkayjava.runtime.components.NinePatchComponent;
import com.o2d.pkayjava.runtime.data.Image9patchVO;
import com.o2d.pkayjava.runtime.data.MainItemVO;
import com.o2d.pkayjava.runtime.data.ProjectInfoVO;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.factory.component.*;
import com.o2d.pkayjava.runtime.factory.component.ComponentFactory;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;

public class NinePatchComponentFactory extends com.o2d.pkayjava.runtime.factory.component.ComponentFactory {

	com.o2d.pkayjava.runtime.components.NinePatchComponent ninePatchComponent;

	public NinePatchComponentFactory(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
		super(rayHandler, world, rm);
	}

	@Override
	public void createComponents(Entity root, Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
		ninePatchComponent = createNinePatchComponent(entity, (com.o2d.pkayjava.runtime.data.Image9patchVO) vo);
		createCommonComponents(entity, vo, com.o2d.pkayjava.runtime.factory.EntityFactory.NINE_PATCH);
		createParentNodeComponent(root, entity);
		createNodeComponent(root, entity);
	}

	@Override
	protected com.o2d.pkayjava.runtime.components.DimensionsComponent createDimensionsComponent(Entity entity, com.o2d.pkayjava.runtime.data.MainItemVO vo) {
		com.o2d.pkayjava.runtime.components.DimensionsComponent component = new com.o2d.pkayjava.runtime.components.DimensionsComponent();
		component.height = ((com.o2d.pkayjava.runtime.data.Image9patchVO) vo).height;
		component.width = ((com.o2d.pkayjava.runtime.data.Image9patchVO) vo).width;
		if(component.width == 0) {
			component.width = ninePatchComponent.ninePatch.getTotalWidth();
		}

		if(component.height == 0) {
			component.height = ninePatchComponent.ninePatch.getTotalHeight();
		}

		entity.add(component);
		return component;
	}

	private com.o2d.pkayjava.runtime.components.NinePatchComponent createNinePatchComponent(Entity entity, com.o2d.pkayjava.runtime.data.Image9patchVO vo) {
		com.o2d.pkayjava.runtime.components.NinePatchComponent ninePatchComponent = new com.o2d.pkayjava.runtime.components.NinePatchComponent();
		AtlasRegion atlasRegion = (AtlasRegion) rm.getTextureRegion(vo.imageName);
		ninePatchComponent.ninePatch = new NinePatch(atlasRegion, atlasRegion.splits[0], atlasRegion.splits[1], atlasRegion.splits[2], atlasRegion.splits[3]);

		com.o2d.pkayjava.runtime.data.ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
		com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = rm.getProjectVO();
		float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);

		ninePatchComponent.ninePatch.scale(multiplier/projectInfoVO.pixelToWorld, multiplier/projectInfoVO.pixelToWorld);
		ninePatchComponent.ninePatch.setMiddleWidth(ninePatchComponent.ninePatch.getMiddleWidth()*multiplier/projectInfoVO.pixelToWorld);
		ninePatchComponent.ninePatch.setMiddleHeight(ninePatchComponent.ninePatch.getMiddleHeight()*multiplier/projectInfoVO.pixelToWorld);

		ninePatchComponent.textureRegionName = vo.imageName;
		entity.add(ninePatchComponent);

		return ninePatchComponent;
	}

}
