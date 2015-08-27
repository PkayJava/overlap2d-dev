package com.pkayjava.editor.proxy;

import com.puremvc.patterns.proxy.BaseProxy;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class SceneDataManager extends BaseProxy {

    private static final String CLASS_NAME;

    public static final String NAME;

    static {
        CLASS_NAME = SceneDataManager.class.getName();
        NAME = CLASS_NAME;
    }

    public SceneDataManager() {
        super(NAME);
    }
}
