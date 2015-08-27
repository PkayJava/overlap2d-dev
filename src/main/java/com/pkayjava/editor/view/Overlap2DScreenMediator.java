package com.pkayjava.editor.view;

import com.puremvc.patterns.mediator.SimpleMediator;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class Overlap2DScreenMediator extends SimpleMediator<Overlap2DScreen> {

    private static final String CLASS_NAME;

    public static final String NAME;

    static {
        CLASS_NAME = Overlap2DScreenMediator.class.getName();
        NAME = CLASS_NAME;
    }

    public Overlap2DScreenMediator() {
        super(NAME, null);
    }
}
