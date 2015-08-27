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

package com.o2d.pkayjava.editor.proxy;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.puremvc.patterns.proxy.BaseProxy;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.*;
import com.o2d.pkayjava.editor.proxy.ProjectManager;
import com.o2d.pkayjava.runtime.utils.MySkin;


public class EditorTextureManager extends BaseProxy {
    private static final String TAG = com.uwsoft.editor.proxy.EditorTextureManager.class.getCanonicalName();
    public static final String NAME = TAG;



    public MySkin editorSkin;
    private TextureAtlas editorAtlas;

    public EditorTextureManager() {
        super(NAME);
    }

    @Override
    public void onRegister() {
        super.onRegister();
        facade = Overlap2DFacade.getInstance();
        loadEditorAssets();
    }

    public void loadEditorAssets() {
        editorAtlas = new TextureAtlas(Gdx.files.getFileHandle("imgs/pack.atlas", FileType.Internal));
    }

    public AtlasRegion getEditorAsset(String name) {
        return editorAtlas.findRegion(name);
    }

    public TextureAtlas getEditorAssetsList() {
        return editorAtlas;
    }

    public void dispose() {

    }

    public Button createImageButton(String button, String hover, String pressed) {
        ButtonStyle btnStl = new ButtonStyle();
        btnStl.up = new TextureRegionDrawable(getEditorAsset(button));
        btnStl.down = new TextureRegionDrawable(getEditorAsset(pressed));
        btnStl.over = new TextureRegionDrawable(getEditorAsset(hover));

        Button btn = new Button(btnStl);

        return btn;
    }

    public Texture getRegionOriginalImage(String regionName) {
        com.uwsoft.editor.proxy.ProjectManager projectManager = facade.retrieveProxy(ProjectManager.NAME);
        String sourcePath = projectManager.getCurrentWorkingPath() + "/" + projectManager.getCurrentProjectVO().projectName + "/assets/orig/images/" + regionName + ".png";
        return new Texture(Gdx.files.absolute(sourcePath));
    }

}
