package com.overlap2d.plugins.physics;

import com.commons.UIDraggablePanel;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

public class PhysicsPanel extends UIDraggablePanel {

    private VisTable mainTable;

    private VisLabel entitiesCount;
    private VisLabel fpsLbl;

    public PhysicsPanel() {
        super("Physics");
        addCloseButton();

        mainTable = new VisTable();

        add(mainTable).width(222);
    }

    public void initLockView() {
        mainTable.clear();

        mainTable.add(new VisLabel("no scenes open")).right();
    }

    public void initView() {
        mainTable.clear();

        entitiesCount = new VisLabel();
        fpsLbl = new VisLabel();


        mainTable.add(new VisLabel("Entity count: ")).right();
        mainTable.add(entitiesCount).left().padLeft(4);
        mainTable.row();

        mainTable.add(new VisLabel("FPS: ")).right();
        mainTable.add(fpsLbl).left().padLeft(4);
        mainTable.row();
        pack();
    }

}
