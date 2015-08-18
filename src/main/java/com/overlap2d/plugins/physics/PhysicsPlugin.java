package com.overlap2d.plugins.physics;

import com.commons.plugins.MenuConnector;
import com.commons.plugins.O2DPluginAdapter;
import net.mountainblade.modular.annotations.Implementation;

@Implementation(authors = "pkayjava", version = "0.0.1")
public class PhysicsPlugin extends O2DPluginAdapter {

    public static final String WINDOWS_MENU = "com.uwsoft.editor.view.Overlap2DMenuBar.WINDOW_MENU";

    public static final String CLASS_NAME = "com.overlap2d.plugins.physics";

    public static final String PANEL_OPEN = CLASS_NAME + ".PANEL_OPEN";

    private PhysicsPanelMediator physicsPanelMediator;

    public PhysicsPlugin() {
        physicsPanelMediator = new PhysicsPanelMediator(this);
    }

    @Override
    public void initPlugin() {
        facade.registerMediator(physicsPanelMediator);
    }

    @Override
    public void initMenuItems(MenuConnector menuConnector) {
        menuConnector.addMenuItem(WINDOWS_MENU, "Physics", PANEL_OPEN);
    }

}
