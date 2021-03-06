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

package com.o2d.pkayjava.editor.view.ui.widget;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.o2d.pkayjava.editor.view.ui.widget.actors.GridView;
import com.o2d.pkayjava.editor.view.ui.widget.actors.ResolutionBounds;
import com.o2d.pkayjava.editor.view.stage.Sandbox;

public class SandboxUI extends Group {

    public SandboxUI(Sandbox s) {
        GridView gridView = new GridView();
        addActor(gridView);
        ResolutionBounds resolutionBounds = new ResolutionBounds(s);
        addActor(resolutionBounds);
    }
}
