package com.pkayjava.runtime.resources;


import com.pkayjava.runtime.data.ProjectInfoVO;
import com.pkayjava.runtime.data.SceneVO;

/**
 * Created by azakhary on 9/9/2014.
 */
public interface IDataLoader {

    public SceneVO loadSceneVO(String sceneName);

    public ProjectInfoVO loadProjectVO();

}
