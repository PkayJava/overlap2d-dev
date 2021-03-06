package com.o2d.pkayjava.runtime;

import box2dLight.RayHandler;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.data.*;
import com.o2d.pkayjava.runtime.systems.*;
import com.o2d.pkayjava.runtime.commons.IExternalItemType;
import com.o2d.pkayjava.runtime.components.*;
import com.o2d.pkayjava.runtime.components.MainItemComponent;
import com.o2d.pkayjava.runtime.components.NodeComponent;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.runtime.components.ScriptComponent;
import com.o2d.pkayjava.runtime.components.light.LightObjectComponent;
import com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent;
import com.o2d.pkayjava.runtime.data.*;
import com.o2d.pkayjava.runtime.data.CompositeItemVO;
import com.o2d.pkayjava.runtime.data.CompositeVO;
import com.o2d.pkayjava.runtime.data.ProjectInfoVO;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.runtime.data.SceneVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.resources.ResourceManager;
import com.o2d.pkayjava.runtime.scripts.IScript;
import com.o2d.pkayjava.runtime.systems.*;
import com.o2d.pkayjava.runtime.systems.ButtonSystem;
import com.o2d.pkayjava.runtime.systems.CompositeSystem;
import com.o2d.pkayjava.runtime.systems.LabelSystem;
import com.o2d.pkayjava.runtime.systems.LayerSystem;
import com.o2d.pkayjava.runtime.systems.LightSystem;
import com.o2d.pkayjava.runtime.systems.ParticleSystem;
import com.o2d.pkayjava.runtime.systems.PhysicsSystem;
import com.o2d.pkayjava.runtime.systems.ScriptSystem;
import com.o2d.pkayjava.runtime.systems.SpriteAnimationSystem;
import com.o2d.pkayjava.runtime.systems.render.Overlap2dRenderer;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;

/**
 * SceneLoader is important part of runtime that utilizes provided
 * IResourceRetriever (or creates default one shipped with runtime) in order to
 * load entire scene data into viewable actors provides the functionality to get
 * root actor of scene and load scenes.
 */
public class SceneLoader {

	private String curResolution = "orig";
	private com.o2d.pkayjava.runtime.data.SceneVO sceneVO;
	private com.o2d.pkayjava.runtime.resources.IResourceRetriever rm = null;

    public Engine engine = null;
	public RayHandler rayHandler;
	public World world;
	public Entity rootEntity;

	public com.o2d.pkayjava.runtime.factory.EntityFactory entityFactory;

	private float pixelsPerWU = 1;

	private com.o2d.pkayjava.runtime.systems.render.Overlap2dRenderer renderer;
	private Entity root;

	public SceneLoader() {
		com.o2d.pkayjava.runtime.resources.ResourceManager rm = new com.o2d.pkayjava.runtime.resources.ResourceManager();
        rm.initAllResources();

		this.rm = rm;

		this.engine = new Engine();
		initSceneLoader();
    }

    public SceneLoader(com.o2d.pkayjava.runtime.resources.IResourceRetriever rm) {
        this.engine = new Engine();
		this.rm = rm;
		initSceneLoader();
    }

	/**
	 * this method is called when rm has loaded all data
	 */
    private void initSceneLoader() {
        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);
        world = new World(new Vector2(0,-10), true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(1f, 1f, 1f, 1f);
        rayHandler.setCulling(true);
        rayHandler.setBlur(true);
        rayHandler.setBlurNum(3);
        rayHandler.setShadows(true);
        
        addSystems();

        entityFactory = new com.o2d.pkayjava.runtime.factory.EntityFactory(rayHandler, world, rm);
    }

	public void setResolution(String resolutionName) {
		com.o2d.pkayjava.runtime.data.ResolutionEntryVO resolution = getRm().getProjectVO().getResolution(resolutionName);
		if(resolution != null) {
			curResolution = resolutionName;
		}
	}


	public com.o2d.pkayjava.runtime.data.SceneVO getSceneVO() {
		return sceneVO;
	}

	public com.o2d.pkayjava.runtime.data.SceneVO loadScene(String sceneName, Viewport viewport) {

		// this has to be done differently.
		engine.removeAllEntities();

		pixelsPerWU = rm.getProjectVO().pixelToWorld;

		sceneVO = rm.getSceneVO(sceneName);
		world.setGravity(new Vector2(sceneVO.physicsPropertiesVO.gravityX, sceneVO.physicsPropertiesVO.gravityY));
		if(sceneVO.composite == null) {
			sceneVO.composite = new com.o2d.pkayjava.runtime.data.CompositeVO();
		}
		rootEntity = entityFactory.createRootEntity(sceneVO.composite, viewport);
		engine.addEntity(rootEntity);

		if(sceneVO.composite != null) {
			entityFactory.initAllChildren(engine, rootEntity, sceneVO.composite);
		}

		setAmbienceInfo(sceneVO);
		rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());

		return sceneVO;
	}

	public com.o2d.pkayjava.runtime.data.SceneVO loadScene(String sceneName) {
		com.o2d.pkayjava.runtime.data.ProjectInfoVO projectVO = rm.getProjectVO();
		Viewport viewport = new ScalingViewport(Scaling.stretch, (float)projectVO.originalResolution.width/ pixelsPerWU, (float)projectVO.originalResolution.height/ pixelsPerWU, new OrthographicCamera());
		return loadScene(sceneName, viewport);
	}

	public void injectExternalItemType(com.o2d.pkayjava.runtime.commons.IExternalItemType itemType) {
		itemType.injectDependencies(rayHandler, world, rm);
		itemType.injectMappers();
		entityFactory.addExternalFactory(itemType);
		engine.addSystem(itemType.getSystem());
		renderer.addDrawableType(itemType);
	}

	private void addSystems() {
        com.o2d.pkayjava.runtime.physics.PhysicsBodyLoader.getInstance().setScaleFromPPWU(pixelsPerWU);

		com.o2d.pkayjava.runtime.systems.ParticleSystem particleSystem = new com.o2d.pkayjava.runtime.systems.ParticleSystem();
		com.o2d.pkayjava.runtime.systems.LightSystem lightSystem = new com.o2d.pkayjava.runtime.systems.LightSystem();
		com.o2d.pkayjava.runtime.systems.SpriteAnimationSystem animationSystem = new com.o2d.pkayjava.runtime.systems.SpriteAnimationSystem();
		com.o2d.pkayjava.runtime.systems.LayerSystem layerSystem = new com.o2d.pkayjava.runtime.systems.LayerSystem();
		com.o2d.pkayjava.runtime.systems.PhysicsSystem physicsSystem = new com.o2d.pkayjava.runtime.systems.PhysicsSystem(world);
		com.o2d.pkayjava.runtime.systems.CompositeSystem compositeSystem = new com.o2d.pkayjava.runtime.systems.CompositeSystem();
		com.o2d.pkayjava.runtime.systems.LabelSystem labelSystem = new com.o2d.pkayjava.runtime.systems.LabelSystem();
        com.o2d.pkayjava.runtime.systems.ScriptSystem scriptSystem = new com.o2d.pkayjava.runtime.systems.ScriptSystem();
		renderer = new com.o2d.pkayjava.runtime.systems.render.Overlap2dRenderer(new PolygonSpriteBatch(2000, createDefaultShader()));
		renderer.setRayHandler(rayHandler);
		renderer.setBox2dWorld(world);
		
		engine.addSystem(animationSystem);
		engine.addSystem(particleSystem);
		engine.addSystem(lightSystem);
		engine.addSystem(layerSystem);
		engine.addSystem(physicsSystem);
		engine.addSystem(compositeSystem);
		engine.addSystem(labelSystem);
        engine.addSystem(scriptSystem);
		engine.addSystem(renderer);

        // additional
        engine.addSystem(new com.o2d.pkayjava.runtime.systems.ButtonSystem());

		addEntityRemoveListener();
	}

	private void addEntityRemoveListener() {
		engine.addEntityListener(new EntityListener() {
			@Override
			public void entityAdded(Entity entity) {
				// TODO: Gev knows what to do. (do this for all entities)

				// mae sure we assign correct z-index here
				/*
				ZindexComponent zindexComponent = ComponentRetriever.get(entity, ZindexComponent.class);
				ParentNodeComponent parentNodeComponent = ComponentRetriever.get(entity, ParentNodeComponent.class);
				if (parentNodeComponent != null) {
					NodeComponent nodeComponent = parentNodeComponent.parentEntity.getComponent(NodeComponent.class);
					zindexComponent.setZIndex(nodeComponent.children.size);
					zindexComponent.needReOrder = false;
				}*/

				// call init for a system
				com.o2d.pkayjava.runtime.components.ScriptComponent scriptComponent = entity.getComponent(com.o2d.pkayjava.runtime.components.ScriptComponent.class);
				if (scriptComponent != null) {
					for (com.o2d.pkayjava.runtime.scripts.IScript script : scriptComponent.scripts) {
						script.init(entity);
					}
				}
			}

			@Override
			public void entityRemoved(Entity entity) {
				com.o2d.pkayjava.runtime.components.ParentNodeComponent parentComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.ParentNodeComponent.class);

				if (parentComponent == null) {
					return;
				}

				Entity parentEntity = parentComponent.parentEntity;
				com.o2d.pkayjava.runtime.components.NodeComponent parentNodeComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(parentEntity, com.o2d.pkayjava.runtime.components.NodeComponent.class);
				parentNodeComponent.removeChild(entity);

				// check if composite and remove all children
				com.o2d.pkayjava.runtime.components.NodeComponent nodeComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.NodeComponent.class);
				if (nodeComponent != null) {
					// it is composite
					for (Entity node : nodeComponent.children) {
						engine.removeEntity(node);
					}
				}

				//check for physics
				com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent physicsBodyComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.physics.PhysicsBodyComponent.class);
				if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
					world.destroyBody(physicsBodyComponent.body);
				}

                // check if it is light
                com.o2d.pkayjava.runtime.components.light.LightObjectComponent lightObjectComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.light.LightObjectComponent.class);
                if(lightObjectComponent != null) {
                    lightObjectComponent.lightObject.remove(true);
                }
			}
		});
	}

	public Entity loadFromLibrary(String libraryName) {
		com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = getRm().getProjectVO();
		com.o2d.pkayjava.runtime.data.CompositeItemVO compositeItemVO = projectInfoVO.libraryItems.get(libraryName);

		if(compositeItemVO != null) {
			Entity entity = entityFactory.createEntity(null, compositeItemVO);
			return entity;
		}

		return null;
	}

    public com.o2d.pkayjava.runtime.data.CompositeItemVO loadVoFromLibrary(String libraryName) {
        com.o2d.pkayjava.runtime.data.ProjectInfoVO projectInfoVO = getRm().getProjectVO();
        com.o2d.pkayjava.runtime.data.CompositeItemVO compositeItemVO = projectInfoVO.libraryItems.get(libraryName);

       return compositeItemVO;
    }

    public void addComponentsByTagName(String tagName, Class componentClass) {
        ImmutableArray<Entity> entities = engine.getEntities();
        for(Entity entity: entities) {
            com.o2d.pkayjava.runtime.components.MainItemComponent mainItemComponent = com.o2d.pkayjava.runtime.utils.ComponentRetriever.get(entity, com.o2d.pkayjava.runtime.components.MainItemComponent.class);
            if(mainItemComponent.tags.contains(tagName)) {
                try {
                    entity.add(ClassReflection.<Component>newInstance(componentClass));
                } catch (ReflectionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	/**
	 * Sets ambient light to the one specified in scene from editor
	 *
	 * @param vo
	 *            - Scene data file to invalidate
	 */
	public void setAmbienceInfo(com.o2d.pkayjava.runtime.data.SceneVO vo) {
        if(!vo.lightSystemEnabled) {
            rayHandler.setAmbientLight(1f, 1f, 1f, 1f);
            return;
        }
		if (vo.ambientColor != null) {
			Color clr = new Color(vo.ambientColor[0], vo.ambientColor[1],
					vo.ambientColor[2], vo.ambientColor[3]);
			rayHandler.setAmbientLight(clr);
		}
	}


	public com.o2d.pkayjava.runtime.factory.EntityFactory getEntityFactory() {
		return entityFactory;
	}

	 public com.o2d.pkayjava.runtime.resources.IResourceRetriever getRm() {
	 	return rm;
	 }

    public Engine getEngine() {
        return engine;
    }

	public Entity getRoot() {
		return rootEntity;
	}
	
	/** Returns a new instance of the default shader used by SpriteBatch for GL2 when no shader is specified. */
	static public ShaderProgram createDefaultShader () {
		String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "uniform mat4 u_projTrans;\n" //
			+ "varying vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "\n" //
			+ "void main()\n" //
			+ "{\n" //
			+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
			+ "   v_color.a = v_color.a * (255.0/254.0);\n" //
			+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
			+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
			+ "}\n";
		String fragmentShader = "#ifdef GL_ES\n" //
			+ "#define LOWP lowp\n" //
			+ "precision mediump float;\n" //
			+ "#else\n" //
			+ "#define LOWP \n" //
			+ "#endif\n" //
			+ "varying LOWP vec4 v_color;\n" //
			+ "varying vec2 v_texCoords;\n" //
			+ "uniform sampler2D u_texture;\n" //
			+ "uniform vec2 atlasCoord;\n" //
			+ "uniform vec2 atlasSize;\n" //
			+ "uniform int isRepeat;\n" //
			+ "void main()\n"//
			+ "{\n" //
			+ "vec4 textureSample = vec4(0.0,0.0,0.0,0.0);\n"//
			+ "if(isRepeat == 1)\n"//
			+ "{\n"//
			+ "textureSample = v_color * texture2D(u_texture, atlasCoord+mod(v_texCoords, atlasSize));\n"//
			+ "}\n"//
			+ "else\n"//
			+ "{\n"//
			+ "textureSample = v_color * texture2D(u_texture, v_texCoords);\n"//
			+ "}\n"//
			+ "  gl_FragColor = textureSample;\n" //
			+ "}";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
		return shader;
	}


    public Batch getBatch() {
        return renderer.getBatch();
    }
}
