package com.o2d.pkayjava.editor.view.stage.input;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.o2d.pkayjava.editor.view.stage.input.*;
import com.o2d.pkayjava.editor.view.stage.input.InputListener;

public class InputListenerComponent implements Component {
	private Array<InputListener> listeners = new Array<InputListener>(1);

	public void addListener(InputListener listener){
		if (!listeners.contains(listener, true)) {
			listeners.add(listener);
		}

	}

	public void removeListener(InputListener listener){
		listeners.removeValue(listener, true);
	}

	public void removeAllListener(){
		listeners.clear();
	}

	public Array<InputListener> getAllListeners(){
		listeners.shrink();
		return listeners;
	}
	
}
