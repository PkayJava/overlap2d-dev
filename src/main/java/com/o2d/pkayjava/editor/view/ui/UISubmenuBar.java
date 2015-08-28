/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.o2d.pkayjava.editor.view.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import com.o2d.pkayjava.editor.view.ui.box.*;

/**
 * Created by sargis on 4/8/15.
 */
public class UISubmenuBar extends VisTable {
    private final com.o2d.pkayjava.editor.Overlap2DFacade facade;

    public UISubmenuBar() {
        Skin skin = VisUI.getSkin();
        facade = com.o2d.pkayjava.editor.Overlap2DFacade.getInstance();
        //debug();
        setBackground(skin.getDrawable("sub-menu-bg"));

        //hierarchy
        UICompositeHierarchyMediator uiCompositeHierarchyMediator = facade.retrieveMediator(UICompositeHierarchyMediator.NAME);
        UICompositeHierarchy uiCompositeHierarchy = null;
        if (uiCompositeHierarchyMediator != null) {
            uiCompositeHierarchy = uiCompositeHierarchyMediator.getViewComponent();
        }
        if (uiCompositeHierarchy != null) {
            add(uiCompositeHierarchy).left().expand().fill().padRight(6);
        }

        //grid
        UIGridBoxMediator uiGridBoxMediator = facade.retrieveMediator(UIGridBoxMediator.NAME);
        UIGridBox uiGridBox = null;
        if (uiGridBoxMediator != null) {
            uiGridBox = uiGridBoxMediator.getViewComponent();
        }
        if (uiGridBox != null) {
            add(uiGridBox).padRight(8);
        }
        //

        //grid
        UIZoomBoxMediator uiZoomBoxMediator = facade.retrieveMediator(UIZoomBoxMediator.NAME);
        UIZoomBox uiZoomBox = null;
        if (uiZoomBoxMediator != null) {
            uiZoomBox = uiZoomBoxMediator.getViewComponent();
        }
        if (uiZoomBox != null) {
            add(uiZoomBox).padRight(8);
        }

        //resolution box
        UIResolutionBoxMediator uiResolutionBoxMediator = facade.retrieveMediator(UIResolutionBoxMediator.NAME);
        UIResolutionBox uiResolutionBox = null;
        if (uiResolutionBoxMediator != null) {
            uiResolutionBox = uiResolutionBoxMediator.getViewComponent();
        }
        if (uiResolutionBox != null) {
            add(uiResolutionBox);
        }
    }
}
