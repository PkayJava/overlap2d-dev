package com.pkayjava.editor.view.frame;

import com.pkayjava.editor.Overlap2DFacade;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class FileDropListener implements DropTargetListener {

    private static final String CLASS_NAME = "com.uwsoft.editor.view.frame.FileDropListener";
    public static final String ACTION_DRAG_ENTER = CLASS_NAME + "ACTION_DRAG_ENTER";
    public static final String ACTION_DRAG_OVER = CLASS_NAME + "ACTION_DRAG_OVER";
    public static final String ACTION_DRAG_EXIT = CLASS_NAME + "ACTION_DRAG_EXIT";
    public static final String ACTION_DROP = CLASS_NAME + "ACTION_DROP";

    public void sendNotification(String notification) {
        sendNotification(notification, null);
    }

    public void sendNotification(String notification, Object data) {
        Overlap2DFacade facade = Overlap2DFacade.getInstance();
        if (facade != null) {
            facade.sendNotification(notification, data);
        }
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        sendNotification(ACTION_DRAG_ENTER, dtde);
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        sendNotification(ACTION_DRAG_OVER, dtde);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        // Do we even need this?
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        sendNotification(ACTION_DRAG_EXIT);
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        sendNotification(ACTION_DROP, dtde);
    }
}
