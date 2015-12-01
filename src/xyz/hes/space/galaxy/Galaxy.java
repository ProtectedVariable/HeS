package xyz.hes.space.galaxy;

import java.util.LinkedList;
import java.util.List;

import me.soldier.math.Vector3f;
import me.soldier.meshutil.Loader;
import me.soldier.meshutil.Model;
import xyz.hes.space.objects.SolarSystem;

public class Galaxy {

	private SolarSystem[] systems;
	private Vector3f position;
	private Vector3f color = new Vector3f(1, 1, 1);

	private float[] vertices;
	private Model model;
	private float rx,ry,rz;
	
	public Galaxy(int size) {
		systems = new SolarSystem[size * 10];
		for (int i = 0; i < systems.length; i++) {
			systems[i] = new SolarSystem((int) (Math.random() * 9 + 1));
		}
		this.setPosition(new Vector3f((float) (Math.random() - 0.5) * 200, (float) (Math.random() - 0.5) * 200, -100));
		defineVertices();
	}

	private void defineVertices() {
		List<Float> vert = new LinkedList<Float>();
		float A, B, N;
		A = (int) Math.round(Math.random()*5+10);
		B = (float) (Math.random()+0.5);
		N = (float) Math.round((Math.random()+2)*6);
		for (int i = 0; i < 360; i++) {
			float theta = (float) Math.toRadians(i);
			float value = (float) (A / (Math.log(B*Math.tan(theta / N))));
			vert.add((float) (value * Math.cos(theta)));
			vert.add((float) (value * Math.sin(theta)));
			vert.add(0f);
			vert.add((float) (-value * Math.cos(theta)));
			vert.add((float) (-value * Math.sin(theta)));
			vert.add(0f);
		}
		vertices = new float[vert.size()];
		for (int i = 0; i < vert.size(); i++) {
			vertices[i] = vert.get(i);
		}
		model = (Loader.createModelVAO(vertices));
		this.setRx((float)(Math.random()+0.5)*180);
		this.setRz((float)Math.random()*60);
	}

	public float[] getVertices() {
		return vertices;
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

	public Model getModel() {
		return model;
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
