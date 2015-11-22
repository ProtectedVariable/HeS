package xyz.hes.space.objects;

public class SolarSystem {

	private Star sun;
	private Planet[] planets;
	
	public SolarSystem(int starCount) {
		this.sun = new Star(starCount);
		this.planets = new Planet[starCount];
		for (int i = 0; i < planets.length; i++) {
			planets[i] = new Planet();
		}
	}

	public Star getSun() {
		return sun;
	}

	public Planet[] getPlanets() {
		return planets;
	}
}
