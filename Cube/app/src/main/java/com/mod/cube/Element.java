package com.mod.cube;

import android.graphics.Canvas;
import java.util.ArrayList;

public abstract class Element {

	public static ArrayList<Element> elements = new ArrayList<Element>();
	
	public int size = 0;
	public boolean clicked = false;
	public Element() {
		elements.add(this);
	}
	
	public abstract void tick();
	public abstract void cloneItself();
	public abstract void render(Canvas canvas);

	public boolean containsMouse(){
		return false;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public int getSize(){
		return this.size;
	}
	
	
}
