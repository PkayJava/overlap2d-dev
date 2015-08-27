package com.pkayjava.editor.proxy;

import com.pkayjava.runtime.resources.IResourceRetriever;
import com.puremvc.patterns.proxy.BaseProxy;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class ResourceManager extends BaseProxy implements IResourceRetriever {

    private static final String CLASS_NAME;

    public static final String NAME;

    static {
        CLASS_NAME = ResourceManager.class.getName();
        NAME = CLASS_NAME;
    }

    public ResourceManager() {
        super(NAME);
    }
}
