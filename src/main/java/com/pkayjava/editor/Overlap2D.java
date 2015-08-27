package com.pkayjava.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.puremvc.patterns.proxy.Proxy;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class Overlap2D extends ApplicationAdapter implements Proxy {

    private static final String CLASS_NAME;

    public static final String NAME;

    public static final String CREATE;
    public static final String PAUSE;
    public static final String RESUME;
    public static final String RENDER;
    public static final String RESIZE;
    public static final String DISPOSE;

    static {
        CLASS_NAME = Overlap2D.class.getName();
        NAME = CLASS_NAME;
        CREATE = CLASS_NAME + ".CREATE_BTN_CLICKED";
        PAUSE = CLASS_NAME + ".PAUSE";
        RESUME = CLASS_NAME + ".RESUME";
        RENDER = CLASS_NAME + ".RENDER";
        RESIZE = CLASS_NAME + ".RESIZE";
        DISPOSE = CLASS_NAME + ".DISPOSE";
    }

    private Overlap2DFacade facade;

    private Object data;

    @Override
    public void create() {
        VisUI.load();
        VisUI.setDefaultTitleAlign(Align.center);
        facade = Overlap2DFacade.getInstance();
        facade.startup(this);
        sendNotification(CREATE);
    }

    @Override
    public void pause() {
        sendNotification(PAUSE);
    }

    @Override
    public void resume() {
        sendNotification(RESUME);
    }

    @Override
    public void render() {
        sendNotification(RENDER, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        sendNotification(RESIZE, new int[]{width, height});
    }

    @Override
    public void dispose() {
        sendNotification(DISPOSE);
        VisUI.dispose();
    }

    @Override
    public void sendNotification(String notificationName, Object body, String type) {
        facade.sendNotification(notificationName, body, type);
    }

    @Override
    public void sendNotification(String notificationName, Object body) {
        facade.sendNotification(notificationName, body);
    }

    @Override
    public void sendNotification(String notificationName) {
        facade.sendNotification(notificationName);
    }

    @Override
    public String getProxyName() {
        return NAME;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onRemove() {

    }
}
