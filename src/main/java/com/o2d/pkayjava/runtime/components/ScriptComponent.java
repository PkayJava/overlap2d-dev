package com.o2d.pkayjava.runtime.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.o2d.pkayjava.runtime.scripts.IScript;

import java.util.Iterator;

/**
 * Created by azakhary on 6/19/2015.
 */
public class ScriptComponent implements Component {

    public Array<com.o2d.pkayjava.runtime.scripts.IScript> scripts = new Array<com.o2d.pkayjava.runtime.scripts.IScript>();

    public void addScript(com.o2d.pkayjava.runtime.scripts.IScript script) {
        scripts.add(script);
    }

    public void addScript(String className) {
        try {
            com.o2d.pkayjava.runtime.scripts.IScript script = (com.o2d.pkayjava.runtime.scripts.IScript) ClassReflection.newInstance(ClassReflection.forName(className));
            addScript(script);
        } catch (ReflectionException e) {
            // well, if it's not there, then we don't care
        }
    }

    public void removeScript(Class className) {
        Iterator<com.o2d.pkayjava.runtime.scripts.IScript> i = scripts.iterator();
        while (i.hasNext()) {
            com.o2d.pkayjava.runtime.scripts.IScript s = i.next();
            if(s.getClass().getName().equals(className)) {
                i.remove();
            }
        }
    }
}
