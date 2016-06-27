package me.it.lib.menu;

import libs.thinmatrix.fontMeshCreator.GUIText;

public abstract class MenuItem {

	protected Menu observer;
	private String text;
	private GUIText guiText;
	
	public MenuItem(Menu m) {
		this.observer = m;
	}
	
	public abstract void Update();

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public GUIText getGuiText() {
		return guiText;
	}

	public void setGuiText(GUIText guiText) {
		this.guiText = guiText;
	}
}
