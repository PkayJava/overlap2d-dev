package com.o2d.pkayjava.editor.view.stage.tools;

import java.util.Set;

import com.badlogic.ashley.core.Entity;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.view.ui.FollowersUIMediator;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.followers.BasicFollower;
import com.o2d.pkayjava.editor.view.ui.followers.NormalSelectionFollower;

/**
 * Created by CyberJoe on 5/2/2015.
 */
public abstract class SimpleTool implements Tool {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = SimpleTool.class.getName();
        NAME = TAG;
    }

    @Override
    public void initTool() {
        Sandbox sandbox = Sandbox.getInstance();
        Set<Entity> currSelection = sandbox.getSelector().getCurrentSelection();
        FollowersUIMediator followersUIMediator = Overlap2DFacade.getInstance().retrieveMediator(FollowersUIMediator.NAME);
        for (Entity entity : currSelection) {
            BasicFollower follower = followersUIMediator.getFollower(entity);
            if (follower instanceof NormalSelectionFollower) {
                NormalSelectionFollower selectionFollower = (NormalSelectionFollower) follower;
                selectionFollower.clearSubFollowers();
            }
        }
    }

    @Override
    public String getName() {
        return NAME;
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
