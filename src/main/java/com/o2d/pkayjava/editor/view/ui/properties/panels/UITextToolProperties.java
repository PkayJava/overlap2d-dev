package com.o2d.pkayjava.editor.view.ui.properties.panels;


import com.o2d.pkayjava.editor.view.ui.properties.panels.UILabelItemProperties;

/**
 * Created by azakhary on 4/24/15.
 */
public class UITextToolProperties extends UILabelItemProperties {

    private static final String TAG;
    public static final String NAME;
    public static final String FONT_FAMILY_SELECTED;

    static {
        TAG = UITextToolProperties.class.getName();
        NAME = TAG;
        FONT_FAMILY_SELECTED = NAME + "." + "FONT_FAMILY_SELECTED";
    }

    public UITextToolProperties() {
        super();

    }
}
