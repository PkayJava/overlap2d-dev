package com.pkayjava.editor.controller;

import com.pkayjava.editor.Overlap2DFacade;
import com.pkayjava.editor.proxy.CommandManager;
import com.pkayjava.editor.proxy.CursorManager;
import com.pkayjava.editor.proxy.EditorTextureManager;
import com.pkayjava.editor.proxy.FontManager;
import com.pkayjava.editor.proxy.ProjectManager;
import com.pkayjava.editor.proxy.ResolutionManager;
import com.pkayjava.editor.proxy.ResourceManager;
import com.pkayjava.editor.proxy.SceneDataManager;
import com.puremvc.patterns.command.SimpleCommand;
import com.puremvc.patterns.observer.Notification;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class BootstrapProxyCommand extends SimpleCommand {
    @Override
    public void execute(Notification notification) {
        super.execute(notification);
        facade = Overlap2DFacade.getInstance();
        facade.registerProxy(new FontManager());
        facade.registerProxy(new CommandManager());
        facade.registerProxy(new CursorManager());
        facade.registerProxy(new ProjectManager());
        facade.registerProxy(new ResolutionManager());
        facade.registerProxy(new SceneDataManager());
        facade.registerProxy(new EditorTextureManager());
        facade.registerProxy(new ResourceManager());
    }
}
