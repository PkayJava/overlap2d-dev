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

package com.o2d.pkayjava.editor.event;

import com.kotcrab.vis.ui.widget.NumberSelector;
import com.o2d.pkayjava.editor.Overlap2DFacade;

/**
 * Created by azakhary on 6/12/2015.
 */
public class NumberSelectorOverlapListener implements NumberSelector.NumberSelectorListener {

    private final String eventName;

    public NumberSelectorOverlapListener(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public void changed(int number) {
        Overlap2DFacade facade = Overlap2DFacade.getInstance();
        facade.sendNotification(eventName, number);
    }
}