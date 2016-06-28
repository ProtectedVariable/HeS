package me.it.hes.space.universe;

import java.util.List;

import me.it.hes.core.Game;
import me.it.hes.space.galaxy.*;
import me.it.lib.util.FileReader;

public class Universe {

    private Galaxy[] galaxies;
    private List<String> names;

    public Universe(int nbGalaxies) {
	names = FileReader.readFile(Game.LANG_PATH+"G_N.txt");
	galaxies = new Galaxy[nbGalaxies];
	for (int i = 0; i < galaxies.length; i++) {
	    galaxies[i] = new Galaxy(names.get((int) Math.round(Math.random()*(names.size()-1))), (int) ((Math.random() + 1) * 10));
	}
    }

    public Galaxy[] getGalaxies() {
	return galaxies;
    }
}
