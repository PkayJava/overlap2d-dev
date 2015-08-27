package com.o2d.pkayjava.runtime.components.sprite;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.o2d.pkayjava.runtime.SceneLoader;
import com.o2d.pkayjava.runtime.data.FrameRange;

public class SpriteAnimationComponent implements Component {
	public String animationName = "";
	public int fps = 24;
	public HashMap<String, FrameRange> frameRangeMap = new HashMap<String, FrameRange>();
    public String currentAnimation;
    public Animation.PlayMode playMode = Animation.PlayMode.LOOP;
	
}
