package xyz.hes.space.solarsystems;

import me.soldier.math.Vector3f;
import xyz.hes.space.SpaceProperty;
import xyz.hes.space.planets.Planet;

public class SolarSystem extends SpaceProperty {

	private Star sun;
	private Planet[] planets;
	private Vector3f position;
	private float t = 1000;
	private float rad;
	
	public SolarSystem(int starCount) {
		this.setPosition(new Vector3f((float)(Math.random()-0.5f)*100, 0, (float)(Math.random()-0.5f)*100));
		this.sun = new Star(starCount);
		this.planets = new Planet[starCount];
		for (int i = 0; i < planets.length; i++) {
			planets[i] = new Planet(new Vector3f((float)Math.random(), 0, (float)Math.random()), new Vector3f(1, 1, 1), (float) (Math.random()*3));
		}
	}
	
	public void Update() {
		rad = (float) Math.sqrt(position.x*position.x+position.z*position.z);
		t += 1f/50f;
		float T = (float) (2*Math.PI*Math.sqrt((rad*rad*rad)/1000));
		position.x = (float) (rad * Math.cos((2*Math.PI*t)/T));
		position.z = (float) (rad * Math.sin((2*Math.PI*t)/T));
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
