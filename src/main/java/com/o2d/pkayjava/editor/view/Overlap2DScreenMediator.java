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

package com.o2d.pkayjava.editor.view;

import com.badlogic.ashley.core.Engine;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2D;
import com.o2d.pkayjava.editor.view.Overlap2DScreen;
import com.o2d.pkayjava.editor.view.ui.widget.actors.basic.SandboxBackUI;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;
import com.o2d.pkayjava.editor.view.stage.SandboxMediator;
import com.o2d.pkayjava.runtime.systems.render.Overlap2dRenderer;

/**
 * Created by sargis on 3/30/15.
 */
public class Overlap2DScreenMediator extends SimpleMediator<Overlap2DScreen> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = Overlap2DScreenMediator.class.getName();
        NAME = TAG;
    }

    public Overlap2DScreenMediator() {
        super(NAME, null);
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                Overlap2D.CREATE,
                Overlap2D.PAUSE,
                Overlap2D.RESUME,
                Overlap2D.RENDER,
                Overlap2D.RESIZE,
                Overlap2D.DISPOSE,
                SceneDataManager.SCENE_LOADED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);

        if (Overlap2D.CREATE.equals(notification.getName())) {
            setViewComponent(new Overlap2DScreen());
            //TODO this must be changed to Command
            facade = Overlap2DFacade.getInstance();
            SandboxMediator sandboxMediator = facade.retrieveMediator(SandboxMediator.NAME);

            Engine engine = null;
            if (sandboxMediator != null) {
                engine = sandboxMediator.getViewComponent().getEngine();
            }
            if (engine != null) {
                getViewComponent().setEngine(engine);
            }
            viewComponent.show();
        } else if (SceneDataManager.SCENE_LOADED.equals(notification.getName())) {
            SandboxMediator sandboxMediator = facade.retrieveMediator(SandboxMediator.NAME);
            Engine engine = null;
            if (sandboxMediator != null) {
                engine = sandboxMediator.getViewComponent().getEngine();
            }
            facade = Overlap2DFacade.getInstance();
            sandboxMediator = facade.retrieveMediator(SandboxMediator.NAME);
            if (sandboxMediator != null) {
                engine = sandboxMediator.getViewComponent().getEngine();
            }
            SandboxBackUI sandboxBackUI = null;
            if (engine != null) {
                sandboxBackUI = new SandboxBackUI(engine.getSystem(Overlap2dRenderer.class).batch);
            }
            if (sandboxBackUI != null) {
                getViewComponent().setBackUI(sandboxBackUI);
            }
            getViewComponent().disableDrawingBgLogo();
        } else if (Overlap2D.PAUSE.equals(notification.getName())) {
            viewComponent.pause();
        } else if (Overlap2D.RESUME.equals(notification.getName())) {
            viewComponent.resume();
        } else if (Overlap2D.RENDER.equals(notification.getName())) {
            viewComponent.render(notification.getBody());
        } else if (Overlap2D.RESIZE.equals(notification.getName())) {
            int[] data = notification.getBody();
            viewComponent.resize(data[0], data[1]);
        } else if (Overlap2D.DISPOSE.equals(notification.getName())) {
        }
    }
}
