package com.o2d.pkayjava.editor.view.ui.properties.panels;

import com.badlogic.ashley.core.Entity;
import com.puremvc.patterns.observer.Notification;
import com.o2d.pkayjava.editor.view.stage.Sandbox;
import com.o2d.pkayjava.editor.Overlap2DFacade;
import com.o2d.pkayjava.editor.proxy.FontManager;
import com.o2d.pkayjava.editor.proxy.ResourceManager;
import com.o2d.pkayjava.editor.view.ui.properties.UIItemPropertiesMediator;
import com.o2d.pkayjava.runtime.components.label.LabelComponent;
import com.o2d.pkayjava.runtime.utils.ComponentRetriever;
import com.o2d.pkayjava.editor.view.ui.properties.panels.UILabelItemProperties;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * Created by avetiszakharyan on 4/24/15.
 */
public class UILabelItemPropertiesMediator extends UIItemPropertiesMediator<Entity, UILabelItemProperties> {

    private static final String TAG;
    public static final String NAME;

    static {
        TAG = UILabelItemPropertiesMediator.class.getName();
        NAME = TAG;
    }

    private String prevText = null;

    private FontManager fontManager;

    public UILabelItemPropertiesMediator() {
        super(NAME, new UILabelItemProperties());
    }

    @Override
    public String[] listNotificationInterests() {
        final String[] parentInterests = super.listNotificationInterests();
        return ArrayUtils.add(parentInterests, UILabelItemProperties.LABEL_TEXT_CHAR_TYPED);
    }

    @Override
    public void handleNotification(Notification notification) {
        super.handleNotification(notification);
        if (notification.getName().equals(UILabelItemProperties.LABEL_TEXT_CHAR_TYPED)) {
            onTextChange();
        }
    }

    private void onTextChange() {

        LabelComponent labelComponent = ComponentRetriever.get(observableReference, LabelComponent.class);
        labelComponent.setText(viewComponent.getText());
    }

    @Override
    public void onRegister() {
        facade = Overlap2DFacade.getInstance();
        fontManager = facade.retrieveProxy(FontManager.NAME);
        lockUpdates = true;
        viewComponent.setFontFamilyList(fontManager.getFontNamesFromMap());
        lockUpdates = false;
    }

    @Override
    protected void translateObservableDataToView(Entity item) {
        LabelComponent labelComponent = ComponentRetriever.get(item, LabelComponent.class);
        viewComponent.setFontFamily(labelComponent.fontName);
        viewComponent.setFontSize(labelComponent.fontSize);
        viewComponent.setAlignValue(labelComponent.labelAlign);
        viewComponent.setText(labelComponent.text.toString());

        if (prevText == null) this.prevText = viewComponent.getText();
    }

    @Override
    protected void translateViewToItemData() {

        final String newText = viewComponent.getText();

        Object[] payload = new Object[6];
        payload[0] = observableReference;
        payload[1] = viewComponent.getFontFamily();
        payload[2] = viewComponent.getFontSize();
        payload[3] = viewComponent.getAlignValue();
        payload[4] = newText;
        payload[5] = prevText;
        sendNotification(Sandbox.ACTION_UPDATE_LABEL_DATA, payload);

        this.prevText = newText;

    }

    @Override
    protected void afterItemDataModified() {

    }
}
