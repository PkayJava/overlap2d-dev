package com.pkayjava.editor.view.stage;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.pkayjava.editor.view.stage.input.InputListener;
import com.pkayjava.runtime.data.SceneVO;
import com.uwsoft.editor.view.stage.*;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class Sandbox {

    private static Sandbox instance = null;

    private UIStage uiStage;

    private Sandbox() {
    }

    public synchronized static Sandbox getInstance() {
        /*
         * The instance gets created only when it is called for first time.
		 * Lazy-loading
		 */
        if (instance == null) {
            instance = new Sandbox();
            instance.init();
        }

        return instance;
    }

    private void init() {
    }

    public UIStage getUIStage() {
        return uiStage;
    }

    public void loadCurrentProject() {
    }

    public Entity getCurrentViewingEntity() {
        return null;
    }

    public Viewport getViewport() {
        return null;
    }

    public Array<InputListener> getAllListeners() {
        return null;
    }

    public SceneVO sceneVoFromItems() {
        return null;
    }
}
