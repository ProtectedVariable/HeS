package me.it.hes.space.galaxy;

import me.it.hes.space.*;
import me.it.hes.space.solarsystems.*;
import me.it.lib.math.*;
import me.it.lib.meshutil.*;

public class Galaxy extends SpaceProperty {

	private SolarSystem[] systems;
	private Vector3f position;
	private Vector3f color = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());

	private static int vao;

	static {
		float[] texCoords = { 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 };
		float[] coords = { -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f };
		vao = Loader.createGUIVAO(coords, texCoords);
	}

	private float rx, ry, rz;
	public boolean MouseHover = false;

	public Galaxy(int size) {
		systems = new SolarSystem[size / 3];
		for (int i = 0; i < systems.length; i++) {
			systems[i] = new SolarSystem((int) (Math.random() * 9 + 1));
		}
		this.setPosition(new Vector3f((float) (Math.random() - 0.5) * 400, (float) (Math.random() - 0.5) * 400, -100));
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

	public static int getVAOID() {
		return vao;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}
}
