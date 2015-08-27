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

package com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable;

import java.util.function.BiFunction;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.ResourceManager;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.*;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.DraggableResourceView;
import com.o2d.pkayjava.editor.view.ui.box.resourcespanel.draggable.payloads.ResourcePayloadObject;

/**
 * Created by azakhary on 7/3/2014.
 */
public class DraggableResource extends DragAndDrop {

    protected final Sandbox sandbox;
    private final DraggableResourceView viewComponent;
    private BiFunction<String, Vector2, Boolean> factoryFunction;

    public DraggableResource(DraggableResourceView viewComponent) {
        this.viewComponent = viewComponent;
        sandbox = Sandbox.getInstance();
    }

    public void initDragDrop() {
        addSource(new Source((Actor) viewComponent) {
            public Payload dragStart(InputEvent event, float x, float y, int pointer) {
                Payload payload = new Payload();
                Actor dragActor = viewComponent.getDragActor();

                OrthographicCamera runtimeCamera = Sandbox.getInstance().getCamera();
                dragActor.setScale(1f/runtimeCamera.zoom);

                ResourcePayloadObject payloadData = viewComponent.getPayloadData();
                payloadData.xOffset = runtimeCamera.zoom * dragActor.getWidth() / 2f;
                payloadData.yOffset = runtimeCamera.zoom * dragActor.getHeight() / 2f;
                payload.setDragActor(dragActor);
                payload.setObject(payloadData);
                payload.setInvalidDragActor(null);
                setDragActorPosition(-dragActor.getWidth() / 2f, dragActor.getHeight() / 2f);
                return payload;
            }
        });

        addTarget(new Target(sandbox.getUIStage().dummyTarget) {
            @Override
            public boolean drag(Source source, Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(Source source, Payload payload, float x, float y, int pointer) {
                Vector2 vector = sandbox.screenToWorld(x, y);
                DraggableResource.this.drop(payload, vector);
            }
        });
    }

    private void drop(Payload payload, Vector2 vector2) {
        ResourcePayloadObject resourcePayloadObject = (ResourcePayloadObject) payload.getObject();
        ResourceManager resourceManager = Overlap2DFacade.getInstance().retrieveProxy(ResourceManager.NAME);

        vector2.sub(resourcePayloadObject.xOffset/resourceManager.getProjectVO().pixelToWorld, resourcePayloadObject.yOffset/resourceManager.getProjectVO().pixelToWorld);
        factoryFunction.apply(resourcePayloadObject.name, vector2);
    }

    public DraggableResourceView getViewComponent() {
        return viewComponent;
    }

    public void setFactoryFunction(BiFunction<String, Vector2, Boolean> factoryFunction) {
        this.factoryFunction = factoryFunction;
    }
}
