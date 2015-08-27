package com.o2d.pkayjava.runtime.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.o2d.pkayjava.runtime.data.ResolutionEntryVO;
import com.o2d.pkayjava.runtime.utils.MySkin;
import com.o2d.pkayjava.runtime.data.ProjectInfoVO;
import com.o2d.pkayjava.runtime.data.SceneVO;

/**
 * Created by azakhary on 9/9/2014.
 */
public interface IResourceRetriever {

    public TextureRegion getTextureRegion(String name);
    public ParticleEffect getParticleEffect(String name);
    public TextureAtlas getSkeletonAtlas(String name);
    public FileHandle getSkeletonJSON(String name);
    public FileHandle getSCMLFile(String name);
    public TextureAtlas getSpriteAnimation(String name);
    public BitmapFont getBitmapFont(String name, int size);
    public com.o2d.pkayjava.runtime.utils.MySkin getSkin();

    public com.o2d.pkayjava.runtime.data.SceneVO getSceneVO(String sceneName);
    public com.o2d.pkayjava.runtime.data.ProjectInfoVO getProjectVO();

    public com.o2d.pkayjava.runtime.data.ResolutionEntryVO getLoadedResolution();
    public ShaderProgram getShaderProgram(String shaderName);
}
