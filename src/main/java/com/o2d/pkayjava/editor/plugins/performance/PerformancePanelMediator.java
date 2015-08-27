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

package com.o2d.pkayjava.editor.plugins.performance;

import com.badlogic.ashley.core.Engine;
import com.o2d.pkayjava.editor.controller.commands.DeleteItemsCommand;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;

/**
 * Created by azakhary on 7/24/2015.
 */
public class PerformancePanelMediator extends SimpleMediator<PerformancePanel> {
    private static final String TAG;
    public static final String NAME;

    public static final String SCENE_LOADED = SceneDataManager.SCENE_LOADED;
    public static final String NEW_ITEM_ADDED = ItemFactory.NEW_ITEM_ADDED;
    public static final String ACTION_DELETE = DeleteItemsCommand.DONE;

    static {
        TAG = PerformancePanelMediator.class.getName();
        NAME = TAG;
    }

    private PerformancePlugin performancePlugin;

    public PerformancePanelMediator(PerformancePlugin performancePlugin) {
        super(NAME, new PerformancePanel());
        this.performancePlugin = performancePlugin;

        viewComponent.initLockView();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                SCENE_LOADED,
                NEW_ITEM_ADDED,
                ACTION_DELETE,
                PerformancePlugin.PANEL_OPEN
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        if (SCENE_LOADED.equals(notification.getName())) {
            viewComponent.initView();
            Engine engine = performancePlugin.getEngine();
            viewComponent.setEngine(engine);
        } else if (PerformancePlugin.PANEL_OPEN.equals(notification.getName())) {
            viewComponent.show(performancePlugin.getStage());
        }
    }
}
