package me.soldier.meshutil;

import me.soldier.graphics.Texture;

public class Material {

	public final static Material GOLD = new Material(new Texture("res/gold.png", -1.5f), 10, 1);
	
	Texture tex;
	float shineDamper;
	float reflectivity;
	
	public Material(Texture tex, float shineDamper, float reflectivity) {
		this.tex = tex;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
	}

	public Texture getTexture() {
		return tex;
	}

	public void setTexture(Texture tex) {
		this.tex = tex;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
}
