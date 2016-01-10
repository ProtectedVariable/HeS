package me.soldier.menu;

import fontRendering.TextMaster;
import me.soldier.math.Vector2f;
import xyz.hes.core.Main;

public class MenuRenderer {

	public MenuRenderer() {
	}

	public void renderMenu(Menu m) {
		int i = Main.pix_height/2-(m.getItems().length/2*100);
		for (MenuItem mi : m.getItems()) {
			i+=90+10;
			if (mi != null) {
				mi.getGuiText().setPosition(new Vector2f(0, (float)i / (float)Main.pix_height));
				TextMaster.loadText(mi.getGuiText());
			}
		}
	}

	public void cleanUp(Menu m) {
		for (MenuItem mi : m.getItems()) {
			TextMaster.removeText(mi.getGuiText());
		}
	}
}
