package com.overlap2d.plugins.physic;

import com.commons.plugins.MenuConnector;
import com.commons.plugins.O2DPluginAdapter;
import net.mountainblade.modular.annotations.Implementation;

/**
 * Created by socheat on 8/12/15.
 */
@Implementation(authors = "pkayjava", version = "0.0.1")
public class PhysicPlugin extends O2DPluginAdapter {

    public static final String CLASS_NAME = "com.uwsoft.editor.plugins.performance";

    public static final String PANEL_OPEN = CLASS_NAME + ".PANEL_OPEN";
    public static final String WINDOWS_MENU = "com.uwsoft.editor.view.Overlap2DMenuBar.WINDOW_MENU";

    private PhysicPanelMediator physicPanelMediator;

    public PhysicPlugin() {
        physicPanelMediator = new PhysicPanelMediator(this);
    }

    @Override
    public void initPlugin() {
        facade.registerMediator(physicPanelMediator);
    }

    @Override
    public void initMenuItems(MenuConnector menuConnector) {
        menuConnector.addMenuItem(WINDOWS_MENU, "Physic", PANEL_OPEN);
    }

}
