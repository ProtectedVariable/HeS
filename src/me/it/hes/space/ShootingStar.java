package me.it.hes.space;

import me.it.lib.meshutil.*;

public class ShootingStar {

	float[] vertices = new float[] { 0, 0, 0 };
	private Model model;
	private float x,y;
	private float ax, ay;
	private float ttl = 100;
	
	
	public ShootingStar(float x, float y) {
		this.setX(x);
		this.setY(y);
		ax = (float)(Math.random()+0.5)*5f;
		ay = (float)(Math.random()+0.5)*5f;
		this.model = Loader.createModelVAO(vertices);
	}
	
	public boolean update() {
		ttl--;
		x += ax;
		y -= ay;
		return ttl > 0;
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
