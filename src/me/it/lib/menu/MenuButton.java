package me.it.lib.menu;

import libs.thinmatrix.fontMeshCreator.GUIText;
import me.it.hes.core.*;
import me.it.lib.math.*;

public class MenuButton extends MenuItem {

    public MenuButton(Menu m, String text) {
	super(m);
	this.setText(text);
	this.setGuiText(new GUIText(getText(), 3, Game.font, new Vector3f(0, 0, 0), 1, true));
	this.getGuiText().setColour(1, 1, 1);
	this.getGuiText().setRendering(false);
    }

    @Override
    public void Update() {
	if (true) {
	    this.observer.OnClick(this);
	}
    }
}
