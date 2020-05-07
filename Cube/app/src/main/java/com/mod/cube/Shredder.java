package com.mod.cube;

import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.List;

public class Shredder{

	public static SoundPool soundPool;
	public static int popID, bounceID;

	public Shredder(){
		soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		popID = soundPool.load(MainActivity.context, R.raw.pop, 1);
		bounceID = soundPool.load(MainActivity.context, R.raw.pop, 1);
	}

	public static void scan(){
		synchronized (Element.elements) {
			for (Element element : Element.elements) {
				if (element.containsMouse()) {
					soundPool.play(popID, 0.08f, 0.08f, 1, 0, (float)(Calc.map(element.getSize(),0,BubblePool.SIZE,1f,0f)));
					if (element.getSize() < 25) {
						Element.elements.remove(element);
					} else {
						element.cloneItself();
						Element.elements.remove(element);
					}
					if(Element.elements.size() == 0){
						Frame.bubblePool.generateSquare(BubblePool.SIZE, BubblePool.COUNT);
					}else if(Element.elements.size() > 350){
						Element.elements.clear();
						Frame.bubblePool.generateSquare(BubblePool.SIZE,BubblePool.COUNT);
					}
					break;
				}
			}
		}
	}

	
}
