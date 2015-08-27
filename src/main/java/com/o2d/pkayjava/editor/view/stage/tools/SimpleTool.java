package com.o2d.pkayjava.editor.view.stage.tools;

import java.util.Set;

import com.badlogic.ashley.core.Entity;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.view.stage.tools.Tool;
import com.o2d.pkayjava.editor.view.ui.FollowersUIMediator;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.followers.BasicFollower;
import com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower;

/**
 * Created by CyberJoe on 5/2/2015.
 */
public abstract class SimpleTool implements com.o2d.pkayjava.editor.view.stage.tools.Tool {

    @Override
    public void initTool() {
        com.o2d.pkayjava.editor.view.stage.Sandbox sandbox = com.o2d.pkayjava.editor.view.stage.Sandbox.getInstance();
        Set<Entity> currSelection = sandbox.getSelector().getCurrentSelection();
        com.o2d.pkayjava.editor.view.ui.FollowersUIMediator followersUIMediator = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance().retrieveMediator(com.o2d.pkayjava.editor.view.ui.FollowersUIMediator.NAME);
        for(Entity entity: currSelection) {
            com.o2d.pkayjava.editor.view.ui.followers.BasicFollower follower = followersUIMediator.getFollower(entity);
            if(follower instanceof com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower) {
                com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower selectionFollower = (com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower) follower;
                selectionFollower.clearSubFollowers();
            }
        }
    }

    @Override
    public String getName() {
        return "SIMPLE_TOOL";
    }

    @Override
    public void stageMouseDragged(float x, float y) {

    }

    @Override
    public boolean stageMouseDown(float x, float y) {
        return false;
    }

    @Override
    public void stageMouseUp(float x, float y) {

    }

    @Override
    public void stageMouseDoubleClick(float x, float y) {

    }

    @Override
    public boolean itemMouseDown(Entity entity, float x, float y) {
        return false;
    }

    @Override
    public void itemMouseUp(Entity entity, float x, float y) {

    }

    @Override
    public void itemMouseDragged(Entity entity, float x, float y) {

    }

    @Override
    public void itemMouseDoubleClick(Entity entity, float x, float y) {

    }

    @Override
    public void handleNotification(Notification notification) {

    }

    @Override
    public void keyDown(Entity entity, int keycode) {

    }
}
