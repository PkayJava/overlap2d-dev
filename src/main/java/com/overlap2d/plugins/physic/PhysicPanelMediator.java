package com.overlap2d.plugins.physic;

import com.badlogic.ashley.core.Engine;
import com.overlap2d.plugins.performance.PerformancePlugin;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;

/**
 * Created by socheat on 8/12/15.
 */
public class PhysicPanelMediator extends SimpleMediator<PhysicPanel> {
    private static final String TAG = PhysicPanelMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    public static final String SCENE_LOADED = "com.uwsoft.editor.proxy.SceneDataManager.SCENE_LOADED";
    public static final String NEW_ITEM_ADDED = "com.uwsoft.editor.factory.ItemFactory.NEW_ITEM_ADDED";
    public static final String ACTION_DELETE = "com.uwsoft.editor.controller.commands.DeleteItemsCommandDONE";

    private PhysicPlugin physicPlugin;

    public PhysicPanelMediator(PhysicPlugin physicPlugin) {
        super(NAME, new PhysicPanel());
        this.physicPlugin = physicPlugin;

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
        switch (notification.getName()) {
            case SCENE_LOADED:
                viewComponent.initView();
                Engine engine = physicPlugin.getEngine();
                break;
            case PerformancePlugin.PANEL_OPEN:
                viewComponent.show(physicPlugin.getUiStage());
                break;
        }
    }
}
