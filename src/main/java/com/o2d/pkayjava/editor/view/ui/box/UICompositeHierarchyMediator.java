package com.o2d.pkayjava.editor.view.ui.box;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Array;
import com.puremvc.patterns.mediator.SimpleMediator;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.controller.commands.CompositeCameraChangeCommand;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.runtime.components.ParentNodeComponent;
import com.o2d.pkayjava.runtime.data.CompositeItemVO;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.utils.runtime.EntityUtils;
import com.o2d.pkayjava.editor.view.ui.box.UICompositeHierarchy;

/**
 * Created by CyberJoe on 4/22/2015.
 */
public class UICompositeHierarchyMediator extends SimpleMediator<UICompositeHierarchy> {
    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UICompositeHierarchyMediator.class.getName();
        NAME = TAG;
    }

    private Sandbox sandbox;

    public UICompositeHierarchyMediator() {
        super(NAME, new UICompositeHierarchy());
    }

    public String[] listNotificationInterests() {
        return new String[]{
                ProjectManager.PROJECT_OPENED,
                CompositeCameraChangeCommand.DONE,
                UICompositeHierarchy.SWITCH_VIEW_COMPOSITE_CLICKED
        };
    }

    @Override
    public void handleNotification(Notification notification) {
        sandbox = Sandbox.getInstance();
        super.handleNotification(notification);
        if (ProjectManager.PROJECT_OPENED.equals(notification.getName())) {
            buildCompositeTree(sandbox.getRootEntity());
        } else if (CompositeCameraChangeCommand.DONE.equals(notification.getName())) {
            Integer entityId = notification.getBody();
            changeComposite(entityId);
        } else if (UICompositeHierarchy.SWITCH_VIEW_COMPOSITE_CLICKED.equals(notification.getName())) {
            Integer entityId = notification.getBody();
            entityId = notification.getBody();
            Overlap2DFacade.getInstance().sendNotification(Sandbox.ACTION_CAMERA_CHANGE_COMPOSITE, EntityUtils.getByUniqueId(entityId));
        }
    }

    private void buildCompositeTree(Entity entity) {
        Array<Integer> composites = new Array<>();
        viewComponent.clearItems();

        while (true) {
            Integer entityId = EntityUtils.getEntityId(entity);
            composites.add(entityId);
            ParentNodeComponent parentNodeComponent = ComponentRetriever.get(entity, ParentNodeComponent.class);
            if (parentNodeComponent == null) {
                break;
            }
            entity = parentNodeComponent.parentEntity;
        }

        for (int i = composites.size - 1; i >= 0; i--) {
            if (i == composites.size - 1) {
                viewComponent.addItem("root", composites.get(i));
            } else {
                viewComponent.addItem("composite", composites.get(i));
            }
        }
    }

    private void changeComposite(Integer entityId) {
        buildCompositeTree(EntityUtils.getByUniqueId(entityId));
    }

    public void updateOriginalItem() {
        //TODO fix and uncomment
        //updateOriginalItem(scenes.get(scenes.size() - 1), commands.sceneControl.getCurrentScene());
    }

    private void updateOriginalItem(CompositeItemVO updatableVo, Entity currItem) {
        //TODO fix and uncomment
//        updatableVo.update(new CompositeItemVO(currItem.getDataVO().composite));
//
//        String libName = currItem.getDataVO().libraryLink;
//        CompositeItemVO libItem = commands.sceneControl.getCurrentSceneVO().libraryItems.get(libName);
//
//        if (libItem != null) {
//            libItem.update(currItem.getDataVO());
//
//
//            //TODO: update other items with same name
//            revursiveUpdateLibraryVO(libName, commands.sceneControl.getRootSceneVO(), currItem.getDataVO());
//        }
    }

    /*
    private void revursiveUpdateLibraryVO(String libName, CompositeItemVO initialVO, CompositeItemVO updatingWith) {
        for (int i = 0; i < initialVO.composite.sComposites.size(); i++) {
            if (initialVO.composite.sComposites.get(i).libraryLink.equals(libName)) {
                initialVO.composite.sComposites.get(i).update(updatingWith);
            } else {
                revursiveUpdateLibraryVO(libName, initialVO.composite.sComposites.get(i), updatingWith);
            }
        }
    }*/
}
