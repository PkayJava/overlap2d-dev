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

package com.o2d.pkayjava.editor.view.stage.tools;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.o2d.pkayjava.editor.factory.ItemFactory;
import com.o2d.pkayjava.runtime.data.LightVO;
import com.o2d.pkayjava.runtime.factory.EntityFactory;
import com.o2d.pkayjava.editor.view.stage.tools.*;
import com.o2d.pkayjava.editor.view.stage.tools.ItemDropTool;

/**
 * Created by azakhary on 4/30/2015.
 */
public class PointLightTool extends ItemDropTool {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = PointLightTool.class.getName();
        NAME = TAG;
    }


    @Override
    public Entity putItem(float x, float y) {
        LightVO vo = new LightVO();
        vo.type = LightVO.LightType.POINT;
        vo.distance = 300/sandbox.getPixelPerWU();

        return ItemFactory.get().createLightItem(vo, new Vector2(x, y));
    }

    @Override
    public int[] listItemFilters() {
        int[] filter = {EntityFactory.LIGHT_TYPE};
        return filter;
    }
}
