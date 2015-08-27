package com.pkayjava.editor.view.stage;

import com.puremvc.patterns.mediator.SimpleMediator;

/**
 * Created by socheatkhauv on 8/27/15.
 */
public class SandboxMediator extends SimpleMediator<Sandbox> {

    private static final String CLASS_NAME;

    public static final String NAME;

    static {
        CLASS_NAME = SandboxMediator.class.getName();
        NAME = CLASS_NAME;
    }

    public SandboxMediator(String mediatorName) {
        super(NAME, Sandbox.getInstance());
    }
}
