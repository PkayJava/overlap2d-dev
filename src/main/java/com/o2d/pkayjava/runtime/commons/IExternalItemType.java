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

package com.o2d.pkayjava.runtime.commons;

import box2dLight.RayHandler;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.World;
import com.o2d.pkayjava.runtime.factory.component.ComponentFactory;
import com.o2d.pkayjava.runtime.resources.IResourceRetriever;
import com.o2d.pkayjava.runtime.systems.render.logic.Drawable;

/**
 * Created by azakhary on 7/20/2015.
 */
public interface IExternalItemType {

    public int getTypeId();
    public com.o2d.pkayjava.runtime.systems.render.logic.Drawable getDrawable();
    public IteratingSystem getSystem();
    public com.o2d.pkayjava.runtime.factory.component.ComponentFactory getComponentFactory();
    public void injectMappers();
    public void injectDependencies(RayHandler rayHandler, World world, com.o2d.pkayjava.runtime.resources.IResourceRetriever rm);
}
