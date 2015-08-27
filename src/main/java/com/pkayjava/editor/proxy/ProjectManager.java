package com.pkayjava.editor.proxy;

import com.pkayjava.runtime.data.SceneVO;
import com.puremvc.patterns.proxy.BaseProxy;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class ProjectManager extends BaseProxy {

    private static final String CLASS_NAME;

    public static final String NAME;

    public static final String PROJECT_OPENED;

    static {
        CLASS_NAME = ProjectManager.class.getName();
        NAME = CLASS_NAME;
        PROJECT_OPENED = NAME + ".PROJECT_OPENED";
    }

    public ProjectManager() {
        super(NAME);
    }

    public String getRootPath() {
        return "";
    }

    public void openProjectFromPath(String s) {
    }

    public void saveCurrentProject(SceneVO sceneVO) {
    }

    public void exportProject() {
    }
}
