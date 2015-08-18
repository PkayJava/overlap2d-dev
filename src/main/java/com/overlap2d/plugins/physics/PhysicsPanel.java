package com.overlap2d.plugins.physics;

import com.commons.UIDraggablePanel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.uwsoft.editor.event.ButtonToNotificationListener;
import com.uwsoft.editor.renderer.scene2d.ButtonClickListener;
import com.uwsoft.editor.view.menu.Overlap2DMenuBar;

public class PhysicsPanel extends UIDraggablePanel {

    private VisTable mainTable;

    public PhysicsPanel() {
        super("Physics");
        addCloseButton();

        mainTable = new VisTable();

        add(mainTable).width(222);
    }

    public void initView() {
        mainTable.clear();

        VisTextButton exportButton = new VisTextButton("Export");
//        exportButton.addListener()
        mainTable.add(exportButton).row();

        exportButton.addListener(new ButtonToNotificationListener(Overlap2DMenuBar.EXPORT));

        pack();
    }

}
