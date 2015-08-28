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

package com.o2d.pkayjava.editor.data.vo;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

/**
 * Created by sargis on 8/1/14.
 */
public class EditorConfigVO {
    public static final String EDITOR_CONFIG_FILE = "config.pit";
    public String lastOpenedSystemPath = "";

    public String constructJsonString() {
        String str = "";
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        str = json.prettyPrint(this);
        return str;
    }
}