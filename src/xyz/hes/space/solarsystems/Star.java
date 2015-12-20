package xyz.hes.space.solarsystems;

import me.soldier.meshutil.Model;
import me.soldier.meshutil.OBJLoader;

public class Star {

	private int size;
	
	private static Model starModel;
	
	static {
		starModel = OBJLoader.loadObjModel("res/sphere.obj");
	}
	
	public Star(int size) {
		this.setSize(size);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static Model getModel() {
		return starModel;
	}

	public static void setModel(Model starModel) {
		Star.starModel = starModel;
	}
}
