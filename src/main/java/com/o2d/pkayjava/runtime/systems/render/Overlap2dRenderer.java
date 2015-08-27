package com.o2d.pkayjava.runtime.systems.render;

import box2dLight.RayHandler;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.commons.IExternalItemType;
import com.o2d.pkayjava.runtime.components.CompositeTransformComponent;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.runtime.components.TransformComponent;
import com.o2d.pkayjava.runtime.components.ViewPortComponent;
import com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader;
import com.o2d.pkayjava.runtime.systems.render.logic.DrawableLogicMapper;


public class Overlap2dRenderer extends IteratingSystem {
	private final float TIME_STEP = 1f/60;
	
	private ComponentMapper<com.o2d.pkayjava.runtime.components.ViewPortComponent> viewPortMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ViewPortComponent.class);
	private ComponentMapper<com.o2d.pkayjava.runtime.components.CompositeTransformComponent> compositeTransformMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.CompositeTransformComponent.class);
	private ComponentMapper<com.o2d.pkayjava.runtime.components.NodeComponent> nodeMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.NodeComponent.class);
	private ComponentMapper<com.o2d.pkayjava.runtime.components.ParentNodeComponent> parentNodeMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.ParentNodeComponent.class);
	private ComponentMapper<com.o2d.pkayjava.runtime.components.TransformComponent> transformMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.TransformComponent.class);
	private ComponentMapper<com.o2d.pkayjava.runtime.components.MainItemComponent> mainItemComponentMapper = ComponentMapper.getFor(com.o2d.pkayjava.runtime.components.MainItemComponent.class);
	
	private com.o2d.pkayjava.runtime.systems.render.logic.DrawableLogicMapper drawableLogicMapper;
	private RayHandler rayHandler;
	private World world;
	private boolean isPhysicsOn = true;
	
	private float accumulator = 0;
	//private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	public Batch batch;

	public Overlap2dRenderer(Batch batch) {
		super(Family.all(com.o2d.pkayjava.runtime.components.ViewPortComponent.class).get());
		this.batch = batch;
		drawableLogicMapper = new com.o2d.pkayjava.runtime.systems.render.logic.DrawableLogicMapper();
	}

	public void addDrawableType(com.o2d.pkayjava.runtime.commons.IExternalItemType itemType) {
		drawableLogicMapper.addDrawableToMap(itemType.getTypeId(), itemType.getDrawable());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		com.o2d.pkayjava.runtime.components.ViewPortComponent ViewPortComponent = viewPortMapper.get(entity);
		Camera camera = ViewPortComponent.viewPort.getCamera();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawRecursively(entity);
		batch.end();

		
		//TODO kinda not cool (this should be done in separate lights renderer maybe?
		if(rayHandler != null) {
			rayHandler.setCulling(false);
			OrthographicCamera orthoCamera = (OrthographicCamera) camera;
			camera.combined.scl(1f/ com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader.getScale());
			rayHandler.setCombinedMatrix(orthoCamera); 
			rayHandler.updateAndRender();
		}
		
		if(world != null && isPhysicsOn) {
			doPhysicsStep(deltaTime);
        }

		//debugRenderer.render(world, camera.combined);
		//TODO Spine rendere thing
	}

	private void doPhysicsStep(float deltaTime) {
	    // fixed time step
	    // max frame time to avoid spiral of death (on slow devices)
	    float frameTime = Math.min(deltaTime, 0.25f);
	    accumulator += frameTime;
	    while (accumulator >= TIME_STEP) {
	        world.step(TIME_STEP, 6, 2);
	        accumulator -= TIME_STEP;
	    }
	}

	private void drawRecursively(Entity rootEntity) {
		
		
		//currentComposite = rootEntity;
		com.o2d.pkayjava.runtime.components.CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		
		
		if (curCompositeTransformComponent.transform){
			computeTransform(rootEntity);
			applyTransform(rootEntity, batch);
		}
		drawChildren(rootEntity, batch, curCompositeTransformComponent);
		if (curCompositeTransformComponent.transform) resetTransform(rootEntity, batch);
	}

	private void drawChildren(Entity rootEntity, Batch batch, com.o2d.pkayjava.runtime.components.CompositeTransformComponent curCompositeTransformComponent) {
		com.o2d.pkayjava.runtime.components.NodeComponent nodeComponent = nodeMapper.get(rootEntity);
		Entity[] children = nodeComponent.children.begin();
		if (curCompositeTransformComponent.transform) {
			for (int i = 0, n = nodeComponent.children.size; i < n; i++) {
				Entity child = children[i];
				
				com.o2d.pkayjava.runtime.components.MainItemComponent childMainItemComponent = mainItemComponentMapper.get(child);
				if(!childMainItemComponent.visible){
					continue;
				}
				
				int entityType = childMainItemComponent.entityType;
				
				//TODO Alpha thing
				
				com.o2d.pkayjava.runtime.components.NodeComponent childNodeComponent = nodeMapper.get(child);
				
				
				if(childNodeComponent ==null){
					//Find logic from the mapper and draw it
					drawableLogicMapper.getDrawable(entityType).draw(batch, child);
				}else{
					//Step into Composite
					drawRecursively(child);
				}
			}
		} else {
			// No transform for this group, offset each child.
			com.o2d.pkayjava.runtime.components.TransformComponent compositeTransform = transformMapper.get(rootEntity);
			
			float offsetX = compositeTransform.x, offsetY = compositeTransform.y;
			
			if(viewPortMapper.has(rootEntity)){
				offsetX = 0;
				offsetY = 0;
			}
			
			for (int i = 0, n = nodeComponent.children.size; i < n; i++) {
				Entity child = children[i];

				//TODO visibility and parent Alpha thing
				//if (!child.isVisible()) continue;
				//if (!child.isVisible()) continue;
				
				com.o2d.pkayjava.runtime.components.TransformComponent childTransformComponent = transformMapper.get(child);
				float cx = childTransformComponent.x, cy = childTransformComponent.y;
				childTransformComponent.x = cx + offsetX;
				childTransformComponent.y = cy + offsetY;
				
				com.o2d.pkayjava.runtime.components.NodeComponent childNodeComponent = nodeMapper.get(child);
				int entityType = mainItemComponentMapper.get(child).entityType;
				
				if(childNodeComponent ==null){
					//Finde the logic from mapper and draw it
					drawableLogicMapper.getDrawable(entityType).draw(batch, child);
				}else{
					//Step into Composite
					drawRecursively(child);
				}
				childTransformComponent.x = cx;
				childTransformComponent.y = cy;
				
				if(childNodeComponent !=null){
					drawRecursively(child);
				}
			}
		}
		nodeComponent.children.end();
	}

	/** Returns the transform for this group's coordinate system. 
	 * @param rootEntity */
	protected Matrix4 computeTransform (Entity rootEntity) {
		com.o2d.pkayjava.runtime.components.CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		//NodeComponent nodeComponent = nodeMapper.get(rootEntity);
		com.o2d.pkayjava.runtime.components.ParentNodeComponent parentNodeComponent = parentNodeMapper.get(rootEntity);
		com.o2d.pkayjava.runtime.components.TransformComponent curTransform = transformMapper.get(rootEntity);
		Affine2 worldTransform = curCompositeTransformComponent.worldTransform;
		//TODO origin thing
		float originX = 0;
		float originY = 0;
		float x = curTransform.x;
		float y = curTransform.y;
		float rotation = curTransform.rotation;
		float scaleX = curTransform.scaleX;
		float scaleY = curTransform.scaleY;

		worldTransform.setToTrnRotScl(x + originX, y + originY, rotation, scaleX, scaleY);
		if (originX != 0 || originY != 0) worldTransform.translate(-originX, -originY);

		// Find the first parent that transforms.
		
		com.o2d.pkayjava.runtime.components.CompositeTransformComponent parentTransformComponent = null;
		//NodeComponent parentNodeComponent;
		
		Entity parentEntity = null;
		if(parentNodeComponent != null){
			parentEntity = parentNodeComponent.parentEntity;
		}
//		if (parentEntity != null){
//			
//		}
		
//		while (parentEntity != null) {
//			parentNodeComponent = nodeMapper.get(parentEntity);
//			if (parentTransformComponent.transform) break;
//			System.out.println("Gand");
//			parentEntity = parentNodeComponent.parentEntity;
//			parentTransformComponent = compositeTransformMapper.get(parentEntity);
//			
//		}
		
		if (parentEntity != null){
			parentTransformComponent = compositeTransformMapper.get(parentEntity);
			worldTransform.preMul(parentTransformComponent.worldTransform);
			//MainItemComponent main = parentEntity.getComponent(MainItemComponent.class);
			//System.out.println("NAME " + main.itemIdentifier);
		}

		curCompositeTransformComponent.computedTransform.set(worldTransform);
		return curCompositeTransformComponent.computedTransform;
	}

	protected void applyTransform (Entity rootEntity, Batch batch) {
		com.o2d.pkayjava.runtime.components.CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		curCompositeTransformComponent.oldTransform.set(batch.getTransformMatrix());
		batch.setTransformMatrix(curCompositeTransformComponent.computedTransform);
	}

	protected void resetTransform (Entity rootEntity, Batch batch) {
		com.o2d.pkayjava.runtime.components.CompositeTransformComponent curCompositeTransformComponent = compositeTransformMapper.get(rootEntity);
		batch.setTransformMatrix(curCompositeTransformComponent.oldTransform);
	}
	
	public void setRayHandler(RayHandler rayHandler){
		this.rayHandler = rayHandler;
	}

	public void setBox2dWorld(World world) {
		this.world = world;
	}
	
	public void setPhysicsOn(boolean isPhysicsOn) {
		this.isPhysicsOn = isPhysicsOn;
	}

    public Batch getBatch() {
        return batch;
    }
}

