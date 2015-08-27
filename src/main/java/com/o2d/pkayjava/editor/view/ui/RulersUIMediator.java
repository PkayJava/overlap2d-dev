package com.o2d.pkayjava.editor.view.ui;

import com.badlogic.gdx.utils.Array;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.data.vo.SceneConfigVO;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.editor.proxy.SceneDataManager;

import com.o2d.pkayjava.runtime.data.SceneVO;
import com.o2d.pkayjava.editor.utils.Guide;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.view.ui.RulersUI;

/**
 * Created by azakhary on 7/18/2015.
 */
public class RulersUIMediator extends SimpleMediator<RulersUI> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = RulersUIMediator.class.getName();
        NAME = TAG;
    }

    /**
     * Constructor.
     */
    public RulersUIMediator() {
        super(NAME, new RulersUI());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();
        viewComponent.setVisible(false);
    }


    @Override
    public String[] listNotificationInterests() {
        return new String[]{
                SceneDataManager.SCENE_LOADED,
                RulersUI.ACTION_GUIDES_MODIFIED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        SceneVO sceneVO = Sandbox.getInstance().getSceneControl().getCurrentSceneVO();
        if (SceneDataManager.SCENE_LOADED.equals(notification.getName())) {
            Array<Guide> guides = new Array<>();
            for (int i = 0; i < sceneVO.verticalGuides.size(); i++) {
                Guide tmp = new Guide(true);
                tmp.pos = sceneVO.verticalGuides.get(i);
                guides.add(tmp);
            }
            for (int i = 0; i < sceneVO.horizontalGuides.size(); i++) {
                Guide tmp = new Guide(false);
                tmp.pos = sceneVO.horizontalGuides.get(i);
                guides.add(tmp);
            }
            viewComponent.setGuides(guides);
            viewComponent.setVisible(true);
        } else if (RulersUI.ACTION_GUIDES_MODIFIED.equals(notification.getName())) {
            Array<Guide> guides = new Array<>();
            guides = viewComponent.getGuides();
            sceneVO.verticalGuides.clear();
            sceneVO.horizontalGuides.clear();
            for (int i = 0; i < guides.size; i++) {
                if (guides.get(i).isVertical) {
                    sceneVO.verticalGuides.add(guides.get(i).pos);
                } else {
                    sceneVO.horizontalGuides.add(guides.get(i).pos);
                }
            }
        }
    }
}
