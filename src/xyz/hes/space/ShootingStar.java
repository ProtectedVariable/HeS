package xyz.hes.space;

import me.soldier.meshutil.Loader;
import me.soldier.meshutil.Model;

public class ShootingStar {

	float[] vertices = new float[] { 0, 0, -1 };
	private Model model;
	private float x,y;
	private float ttl = 100;
	
	
	public ShootingStar(float x, float y) {
		this.setX(x);
		this.setY(y);
		this.model = Loader.createModelVAO(vertices);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getTtl() {
		return ttl;
	}

	public void setTtl(float ttl) {
		this.ttl = ttl;
	}
	
}
