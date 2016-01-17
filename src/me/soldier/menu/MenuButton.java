package me.soldier.menu;

import libs.thinmatrix.fontMeshCreator.GUIText;
import me.soldier.math.Vector2f;
import xyz.hes.core.Game;

public class MenuButton extends MenuItem {
	
	public MenuButton(Menu m, String text) {
		super(m);
		this.setText(text);
		this.setGuiText(new GUIText(getText(), 3, Game.font, new Vector2f(0, 0), 1, true));
		this.getGuiText().setColour(1, 1, 1);
	}

	@Override
	public void Update() {
		if(true) {
			this.observer.OnClick(this);
		}
	}	
}
