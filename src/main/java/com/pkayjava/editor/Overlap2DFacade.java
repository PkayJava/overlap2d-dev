package com.pkayjava.editor;

import com.pkayjava.editor.controller.StartupCommand;
import com.puremvc.patterns.facade.SimpleFacade;
import com.puremvc.patterns.observer.BaseNotification;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class Overlap2DFacade extends SimpleFacade {

    private static final String CLASS_NAME;

    public static final String STARTUP;

    static {
        CLASS_NAME = Overlap2DFacade.class.getName();
        STARTUP = CLASS_NAME + "." + "STARTUP";
    }

    private static Overlap2DFacade instance = null;

    public synchronized static Overlap2DFacade getInstance() {
        if (instance == null) {
            instance = new Overlap2DFacade();
        }
        return instance;
    }

    private Overlap2D overlap2D;

    private Overlap2DFacade() {
    }

    public void startup(Overlap2D overlap2D) {
        this.overlap2D = overlap2D;
        registerProxy(this.overlap2D);
        notifyObservers(new BaseNotification(STARTUP, null, null));
    }

    @Override
    protected void initializeFacade() {
        super.initializeFacade();
    }

    @Override
    protected void initializeController() {
        super.initializeController();
        registerCommand(STARTUP, StartupCommand.class);
    }

    @Override
    protected void initializeModel() {
        super.initializeModel();
    }

    @Override
    protected void initializeView() {
        super.initializeView();
    }
}
