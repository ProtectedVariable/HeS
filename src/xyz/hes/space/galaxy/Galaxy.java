package xyz.hes.space.galaxy;

import me.soldier.math.Vector3f;
import xyz.hes.space.objects.SolarSystem;

public class Galaxy {

	private SolarSystem[] systems;
	private Vector3f position;
	private Vector3f color = new Vector3f(1,1,1);
	
	public Galaxy(int size) {
		systems = new SolarSystem[size*10];
		for (int i = 0; i < systems.length; i++) {
			systems[i] = new SolarSystem((int) (Math.random()*9+1));
		}
		this.setPosition(new Vector3f((float)(Math.random()-0.5)*200, (float)(Math.random()-0.5)*200, -100));
		//this.setPosition(new Vector3f(0,0,-100));
	}

	public SolarSystem[] getSystems() {
		return systems;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setColor(int r, int g, int b) {
		color.x = r;
		color.y = g;
		color.z = b;
	}

	public Vector3f getColor() {
		return color;
	}	
}
