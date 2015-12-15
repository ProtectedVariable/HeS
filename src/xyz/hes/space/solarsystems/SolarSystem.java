package xyz.hes.space.solarsystems;

import me.soldier.math.Vector3f;
import xyz.hes.space.SpaceProperty;
import xyz.hes.space.planets.Planet;

public class SolarSystem extends SpaceProperty {

	private Star sun;
	private Planet[] planets;
	private Vector3f position;
	
	public SolarSystem(int starCount) {
		this.setPosition(new Vector3f((float)(Math.random()-0.5f)*100, 0, (float)(Math.random()-0.5f)*100));
		this.sun = new Star(starCount);
		this.planets = new Planet[starCount];
		for (int i = 0; i < planets.length; i++) {
			planets[i] = new Planet(new Vector3f((float)Math.random(), 0, (float)Math.random()), new Vector3f(1, 1, 1), (float) (Math.random()*3));
		}
	}

	public Star getSun() {
		return sun;
	}

	public Planet[] getPlanets() {
		return planets;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
}
