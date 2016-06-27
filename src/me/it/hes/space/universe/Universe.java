package me.it.hes.space.universe;

import me.it.hes.space.galaxy.*;

public class Universe {

	private Galaxy[] galaxies;
	
	public Universe(int nbGalaxies) {
		galaxies = new Galaxy[nbGalaxies];
		for (int i = 0; i < galaxies.length; i++) {
			galaxies[i] = new Galaxy((int) ((Math.random()+1)*10));
		}
	}

	public Galaxy[] getGalaxies() {
		return galaxies;
	}
}
