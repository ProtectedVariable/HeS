package me.it.hes.space.planets;

import me.it.hes.space.*;
import me.it.lib.math.*;
import me.it.lib.meshutil.*;

public class Planet extends SpaceProperty {

	private static Model planetModel;

	static {
		if(MasterRenderer.LOD.getExposant() < 5) {
			setModel(OBJLoader.loadObjModel("res/icosphere.obj"));
		} else {
			setModel(OBJLoader.loadObjModel("res/sphere.obj"));
		}
	}
	
	/** position relative to the star of the solar system */
	private Vector3f position;
	private float Rx, Ry, Rz;
	private float size;
	
	public Planet(Vector3f pos, Vector3f rotation, float size) {
		this.setPosition(position);
		this.setRx(rotation.x);
		this.setRy(rotation.y);
		this.setRz(rotation.z);
		this.size = size;
	}

	public static Model getModel() {
		return planetModel;
	}

	public static void setModel(Model planetModel) {
		Planet.planetModel = planetModel;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRx() {
		return Rx;
	}

	public void setRx(float rx) {
		Rx = rx;
	}

	public float getRy() {
		return Ry;
	}

	public void setRy(float ry) {
		Ry = ry;
	}

	public float getRz() {
		return Rz;
	}

	public void setRz(float rz) {
		Rz = rz;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
	
}
