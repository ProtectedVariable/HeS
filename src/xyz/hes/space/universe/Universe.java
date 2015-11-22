package xyz.hes.space.universe;

import xyz.hes.space.galaxy.Galaxy;
import xyz.hes.space.objects.Planet;

public class Universe {

	private Galaxy[] galaxies;
	
	public Universe(int nbGalaxies) {
		galaxies = new Galaxy[nbGalaxies];
		for (int i = 0; i < galaxies.length; i++) {
			galaxies[i] = new Galaxy((int) ((Math.random()+1)*10));
		}
		System.out.println("Number of planets: "+Planet.TEMP_COUNT);
	}

	public Galaxy[] getGalaxies() {
		return galaxies;
	}
}
