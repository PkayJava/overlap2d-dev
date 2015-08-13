package com.overlap2d.plugins.physics;

import com.badlogic.ashley.core.Engine;
import com.overlap2d.plugins.performance.PerformancePlugin;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;

public class PhysicsPanelMediator extends SimpleMediator<PhysicsPanel> {
    private static final String TAG = PhysicsPanelMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    public static final String SCENE_LOADED = "com.uwsoft.editor.proxy.SceneDataManager.SCENE_LOADED";
    public static final String NEW_ITEM_ADDED = "com.uwsoft.editor.factory.ItemFactory.NEW_ITEM_ADDED";
    public static final String ACTION_DELETE = "com.uwsoft.editor.controller.commands.DeleteItemsCommandDONE";

    private PhysicsPlugin physicsPlugin;

    public PhysicsPanelMediator(PhysicsPlugin physicPlugin) {
        super(NAME, new PhysicsPanel());
        this.physicsPlugin = physicPlugin;

        viewComponent.initLockView();
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                PhysicsPlugin.PANEL_OPEN
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        switch (notification.getName()) {
            case PhysicsPlugin.PANEL_OPEN:
                viewComponent.show(physicsPlugin.getUiStage());
                break;
        }
    }
}