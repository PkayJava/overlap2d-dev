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

package com.o2d.pkayjava.editor.view.ui.properties.panels;

import com.badlogic.ashley.core.Entity;
import com.o2d.pkayjava.editor.view.ui.properties.UIItemPropertiesMediator;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UICompositeItemProperties;

/**
 * Created by azakhary on 4/16/2015.
 */
public class UICompositeItemPropertiesMediator extends UIItemPropertiesMediator<Entity, UICompositeItemProperties> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UICompositeItemPropertiesMediator.class.getName();
        NAME = TAG;
    }

    public UICompositeItemPropertiesMediator() {
        super(NAME, new UICompositeItemProperties());
    }

    @Override
    protected void translateObservableDataToView(Entity item) {

    }

    @Override
    protected void translateViewToItemData() {

    }
}