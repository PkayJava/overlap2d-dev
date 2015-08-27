package com.o2d.pkayjava.runtime.resources;

import com.o2d.pkayjava.runtime.data.ProjectInfoVO;
import com.o2d.pkayjava.runtime.data.SceneVO;

/**
 * Created by azakhary on 9/9/2014.
 */
public interface IDataLoader {

    public com.o2d.pkayjava.runtime.data.SceneVO loadSceneVO(String sceneName);
    public com.o2d.pkayjava.runtime.data.ProjectInfoVO loadProjectVO();

}
