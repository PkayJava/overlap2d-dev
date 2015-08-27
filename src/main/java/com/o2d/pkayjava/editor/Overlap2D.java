/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.o2d.pkayjava.editor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.VisUI;
import com.o2d.pkayjava.editor.proxy.EditorTextureManager;
import com.puremvc.patterns.proxy.Proxy;

public class Overlap2D extends ApplicationAdapter implements Proxy {
    private static final String TAG;
    public static final String NAME;

    public static final String PAUSE;
    public static final String RESUME;
    public static final String RENDER;
    public static final String RESIZE;
    public static final String DISPOSE;
    public static final String CREATE;

    // tmp events
    public static final String ZOOM_CHANGED;
    public static final String GRID_SIZE_CHANGED;
    public static final String ITEM_DATA_UPDATED;
    public static final String ITEM_PROPERTY_DATA_FINISHED_MODIFYING;


    // this should move
    public static final String HIDE_SELECTIONS;
    public static final String SHOW_SELECTIONS;
    public static final String ITEM_SELECTION_CHANGED;
    public static final String EMPTY_SPACE_CLICKED;

    public static final String SCENE_RIGHT_CLICK;
    public static final String ITEM_RIGHT_CLICK;

    public static final String LIBRARY_LIST_UPDATED;

    static {
        TAG = Overlap2D.class.getName();
        NAME = TAG;
        PAUSE = NAME + "." + "PAUSE";
        RESUME = NAME + "." + "RESUME";
        RENDER = NAME + "." + "RENDER";
        RESIZE = NAME + "." + "RESIZE";
        DISPOSE = NAME + "." + "DISPOSE";
        CREATE = NAME + "." + "CREATE_BTN_CLICKED";

        // tmp events
        ZOOM_CHANGED = NAME + "." + "ZOOM_CHANGED";
        GRID_SIZE_CHANGED = NAME + "." + "GRID_SIZE_CHANGED";
        ITEM_DATA_UPDATED = NAME + "." + "ITEM_DATA_UPDATED";
        ITEM_PROPERTY_DATA_FINISHED_MODIFYING = NAME + "." + "ITEM_PROPERTY_DATA_FINISHED_MODIFYING";


        // this should move
        HIDE_SELECTIONS = NAME + "." + "HIDE_SELECTIONS";
        SHOW_SELECTIONS = NAME + "." + "SHOW_SELECTIONS";
        ITEM_SELECTION_CHANGED = NAME + "." + "ITEM_SELECTION_CHANGED";
        EMPTY_SPACE_CLICKED = NAME + "." + "EMPTY_SPACE_CLICKED";

        SCENE_RIGHT_CLICK = NAME + "." + "SCENE_RIGHT_CLICK";
        ITEM_RIGHT_CLICK = NAME + "." + "ITEM_RIGHT_CLICK";

        LIBRARY_LIST_UPDATED = NAME + "." + "LIBRARY_LIST_UPDATED";
    }

    //
    public EditorTextureManager textureManager;
    private Overlap2DFacade facade;
    private Object data;

    public Overlap2D() {
    }

    public void create() {
        VisUI.load(Gdx.files.internal("style/uiskin.json"));
        VisUI.setDefaultTitleAlign(Align.center);
        facade = Overlap2DFacade.getInstance();
        facade.startup(this);
        sendNotification(CREATE);
    }


    public void pause() {
        sendNotification(PAUSE);
    }

    public void resume() {
        sendNotification(RESUME);
    }

    public void render() {
        sendNotification(RENDER, Gdx.graphics.getDeltaTime());
    }

    public void resize(int width, int height) {
        sendNotification(RESIZE, new int[]{width, height});
    }

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
        return data;
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