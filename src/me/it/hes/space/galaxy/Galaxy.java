package me.it.hes.space.galaxy;

import java.util.List;

import libs.thinmatrix.fontMeshCreator.GUIText;
import me.it.hes.core.Game;
import me.it.hes.space.SpaceProperty;
import me.it.hes.space.solarsystems.SolarSystem;
import me.it.lib.math.Vector3f;
import me.it.lib.meshutil.Loader;
import me.it.lib.util.FileReader;

public class Galaxy extends SpaceProperty {

    private SolarSystem[] systems;
    private Vector3f position;
    private Vector3f color = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());

    private static int vao;
    private static List<String> names;

    static {
	float[] texCoords = { 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 };
	float[] coords = { -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f };
	vao = Loader.createGUIVAO(coords, texCoords);
	names = FileReader.readFile(Game.LANG_PATH+"P_N.txt");
    }

    private float rx, ry, rz;
    public boolean MouseHover = false;

    public Galaxy(String name, int size) {
	this.setName(name);
	systems = new SolarSystem[size / 3];
	for (int i = 0; i < systems.length; i++) {
	    systems[i] = new SolarSystem(names.get((int) Math.round(Math.random()*(names.size()-1))), (int) (Math.random() * 9 + 1));
	}
	this.setPosition(new Vector3f((float) (Math.random() - 0.5) * 400, (float) (Math.random() - 0.5) * 400, -100));
	this.setDisplayText(new GUIText(name, 200, Game.font, new Vector3f(position.x+0.4f*GalaxyRenderer.SCALE, position.y+0.4f*GalaxyRenderer.SCALE, position.z), 1000, false));
	this.getDisplayText().setColour(1, 1, 1);
	this.getDisplayText().setRendering(false);
	this.getDisplayText().set3D(true);
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
