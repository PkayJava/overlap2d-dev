package com.overlap2d.plugins.physics;

import com.badlogic.ashley.core.Engine;
import com.overlap2d.plugins.performance.PerformancePlugin;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.uwsoft.editor.Overlap2DFacade;
import com.uwsoft.editor.proxy.ProjectManager;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.data.CompositeVO;
import com.uwsoft.editor.renderer.data.ProjectInfoVO;
import com.uwsoft.editor.view.menu.Overlap2DMenuBar;
import com.uwsoft.editor.view.stage.Sandbox;

import java.util.Map;

public class PhysicsPanelMediator extends SimpleMediator<PhysicsPanel> {
    private static final String TAG = PhysicsPanelMediator.class.getCanonicalName();
    public static final String NAME = TAG;

    public static final String SCENE_LOADED = "com.uwsoft.editor.proxy.SceneDataManager.SCENE_LOADED";
    public static final String NEW_ITEM_ADDED = "com.uwsoft.editor.factory.ItemFactory.NEW_ITEM_ADDED";
    public static final String ACTION_DELETE = "com.uwsoft.editor.controller.commands.DeleteItemsCommandDONE";
    //    public static final String LINKING_CHANGED = prefix + ".LINKING_CHANGED";
    private ProjectManager projectManager;

    private PhysicsPlugin physicsPlugin;

    public PhysicsPanelMediator(PhysicsPlugin physicPlugin) {
        super(NAME, new PhysicsPanel());
        this.physicsPlugin = physicPlugin;

        viewComponent.initView();
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = Overlap2DFacade.getInstance();
        projectManager = facade.retrieveProxy(ProjectManager.NAME);
    }

    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                PhysicsPlugin.PANEL_OPEN,
                ProjectManager.PROJECT_OPENED,
                Overlap2DMenuBar.EXPORT
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        switch (notification.getName()) {
            case PhysicsPlugin.PANEL_OPEN:
                viewComponent.show(physicsPlugin.getStage());
                break;
            case Overlap2DMenuBar.EXPORT:
                ProjectManager projectManager = facade.retrieveProxy(ProjectManager.NAME);
                String projectMainExportPath = projectManager.getCurrentProjectVO().projectMainExportPath;

                for (Map.Entry<String, CompositeItemVO> item : projectManager.getCurrentProjectInfoVO().libraryItems.entrySet()) {
                }
                break;
        }
    }
}