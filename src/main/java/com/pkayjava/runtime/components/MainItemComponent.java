package com.pkayjava.runtime.components;

import com.badlogic.ashley.core.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by socheatkhauv on 8/27/15.
 */
public class MainItemComponent implements Component {

    public int uniqueId = 0;
    public String itemIdentifier = "";
    public String libraryLink = "";
    public Set<String> tags = new HashSet<String>();
    public String customVars = "";
    public int entityType;
    public boolean visible = true;

}
