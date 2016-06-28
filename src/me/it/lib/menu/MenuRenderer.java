package me.it.lib.menu;

import libs.thinmatrix.fontRendering.TextMaster;
import me.it.hes.core.*;
import me.it.lib.math.*;

public class MenuRenderer {

    public MenuRenderer() {
    }

    public void renderMenu(Menu m) {
	int i = Main.pix_height / 2 - (m.getItems().length / 2 * 100);
	for (MenuItem mi : m.getItems()) {
	    i += 90 + 10;
	    if (mi != null) {
		mi.getGuiText().setPosition(new Vector3f(0, (float) i / (float) Main.pix_height, 0));
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
