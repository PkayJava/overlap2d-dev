package com.o2d.pkayjava.editor.view.ui.properties.panels;

import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.FontManager;
import com.o2d.pkayjava.editor.view.stage.tools.TextTool;
import com.o2d.pkayjava.editor.view.ui.properties.UIAbstractPropertiesMediator;
import com.o2d.pkayjava.editor.view.ui.properties.panels.*;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UITextToolProperties;

/**
 * Created by avetiszakharyan on 4/24/15.
 */
public class UITextToolPropertiesMediator extends UIAbstractPropertiesMediator<TextTool, UITextToolProperties> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UITextToolPropertiesMediator.class.getName();
        NAME = TAG;
    }

    private FontManager fontManager;

    public UITextToolPropertiesMediator() {
        super(NAME, new UITextToolProperties());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();
        fontManager = facade.retrieveProxy(FontManager.NAME);
        if (fontManager != null) {
            viewComponent.setFontFamilyList(fontManager.getFontNamesFromMap());
        }
    }

    @Override
    protected void translateObservableDataToView(TextTool item) {
        viewComponent.setFontFamily(item.getFontFamily());
        viewComponent.setFontSize(item.getFontSize());
    }

    @Override
    public void setItem(TextTool settings) {
        super.setItem(settings);
        observableReference.setFontFamily(viewComponent.getFontFamily());

    }

    @Override
    protected void translateViewToItemData() {
        observableReference.setFontFamily(viewComponent.getFontFamily());
        observableReference.setFontSize(viewComponent.getFontSize());
    }
}
