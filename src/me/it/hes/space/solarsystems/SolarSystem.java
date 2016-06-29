package me.it.hes.space.solarsystems;

import libs.thinmatrix.fontMeshCreator.GUIText;
import me.it.hes.core.Game;
import me.it.hes.space.*;
import me.it.hes.space.galaxy.GalaxyRenderer;
import me.it.hes.space.planets.*;
import me.it.lib.math.*;

public class SolarSystem extends SpaceProperty {

    private Star sun;
    private Planet[] planets;
    private Vector3f position;
    private float t = 1000;
    private float rad;

    public SolarSystem(String name, int starCount) {
	this.setName(name);
	this.setPosition(new Vector3f((float) (Math.random() - 0.5f) * 100, 0, (float) (Math.random() - 0.5f) * 100));
	this.sun = new Star(starCount);
	this.planets = new Planet[starCount];
	for (int i = 0; i < planets.length; i++) {
	    planets[i] = new Planet(new Vector3f((float) Math.random(), 0, (float) Math.random()), new Vector3f(1, 1, 1), (float) (Math.random() * 3));
	}
	this.setDisplayText(new GUIText(name, 30, Game.font, new Vector3f(position.x+0.4f*GalaxyRenderer.SCALE, position.y+0.4f*GalaxyRenderer.SCALE, position.z), 1000, false));
	this.getDisplayText().setColour(1, 1, 1);
	this.getDisplayText().setRendering(false);
	this.getDisplayText().set3D(true);
    }

    public void Update() {
	rad = (float) Math.sqrt(position.x * position.x + position.z * position.z);
	t += 1f / 200f;
	float T = (float) (2 * Math.PI * Math.sqrt((rad * rad * rad) / 1000));
	position.x = (float) (rad * Math.cos((2 * Math.PI * t) / T));
	position.z = (float) (rad * Math.sin((2 * Math.PI * t) / T));
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
