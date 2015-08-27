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

package com.o2d.pkayjava.editor.controller;

import com.puremvc.patterns.command.SimpleCommand;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.view.ui.FollowersUIMediator;
import com.o2d.pkayjava.editor.view.menu.Overlap2DMenuBarMediator;
import com.o2d.pkayjava.editor.view.Overlap2DScreenMediator;
import com.o2d.pkayjava.editor.view.stage.SandboxMediator;
import com.o2d.pkayjava.editor.view.stage.UIStageMediator;
import com.o2d.pkayjava.editor.view.ui.RulersUIMediator;
import com.o2d.pkayjava.editor.view.ui.UIDropDownMenuMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIAlignBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UICompositeHierarchyMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIGridBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIItemsTreeBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UILayerBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIMultiPropertyBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIResolutionBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIResourcesBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIToolBoxMediator;
import com.o2d.pkayjava.editor.view.ui.box.UIZoomBoxMediator;
import com.o2d.pkayjava.editor.view.ui.dialog.*;

/**
 * Created by sargis on 4/1/15.
 */
public class BootstrapViewCommand extends SimpleCommand {
    @Override
    public void execute(Notification notification) {
        super.execute(notification);
        facade = Overlap2DFacade.getInstance();
        facade.registerMediator(new Overlap2DScreenMediator());
        facade.registerMediator(new Overlap2DMenuBarMediator());
        facade.registerMediator(new UICompositeHierarchyMediator());
        facade.registerMediator(new UIGridBoxMediator());
        facade.registerMediator(new UIResolutionBoxMediator());
        facade.registerMediator(new UIZoomBoxMediator());
        facade.registerMediator(new UIToolBoxMediator());

        // Dialogs
        facade.registerMediator(new NewProjectDialogMediator());
        facade.registerMediator(new ImportDialogMediator());
        facade.registerMediator(new ExportSettingsDialogMediator());
        facade.registerMediator(new CreateNewResolutionDialogMediator());
        facade.registerMediator(new CustomVariablesDialogMediator());
        facade.registerMediator(new TagsDialogMediator());
        facade.registerMediator(new EditSpriteAnimationDialogMediator());

        facade.registerMediator(new RulersUIMediator());
        facade.registerMediator(new FollowersUIMediator());

        facade.registerMediator(new UIAlignBoxMediator());
        facade.registerMediator(new UIItemsTreeBoxMediator());
        facade.registerMediator(new UIMultiPropertyBoxMediator());
        facade.registerMediator(new UILayerBoxMediator());
        facade.registerMediator(new UIResourcesBoxMediator());
        facade.registerMediator(new UIStageMediator());
        facade.registerMediator(new SandboxMediator());
        facade.registerMediator(new UIDropDownMenuMediator());
    }
}
