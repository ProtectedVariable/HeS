package xyz.hes.space.galaxy;

import me.soldier.math.Vector3f;
import xyz.hes.space.objects.SolarSystem;

public class Galaxy {

	private SolarSystem[] systems;
	private Vector3f position;
	
	public Galaxy(int size) {
		systems = new SolarSystem[size*10];
		for (int i = 0; i < systems.length; i++) {
			systems[i] = new SolarSystem((int) (Math.random()*9+1));
		}
		this.setPosition(new Vector3f((float)(Math.random()-0.5)*200, (float)(Math.random()-0.5)*200, -100));
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
	
	
}
