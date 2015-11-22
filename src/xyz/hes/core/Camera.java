package xyz.hes.core;

import me.soldier.math.*;

/**
 * @author Osoldier
 * @since 28 janv. 2015
 * @project Galaxy
 */
public class Camera {

	public ViewMatrix vw_matrix;
	public Vector3f position = null;
	public float yaw = 0.0f;
	public float pitch = 0.0f;

	public Camera(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		vw_matrix = new ViewMatrix();
	}

	public void yaw(float amount) {
		yaw += amount;
	}

	public void pitch(float amount) {
		pitch += amount;
	}

	public void Forward(float distance) {
		position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
		position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void Backwards(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void Left(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw - 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	public void Right(float distance) {
		position.x += distance * (float) Math.sin(Math.toRadians(yaw + 90));
		position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}
	
	public void Up(float distance) {
		position.y -= distance;
	}
	
	public void Down(float distance) {
		position.y += distance;
	}
	
	public void lookThrough() {
		vw_matrix.Transform(position, new Vector3f(pitch, yaw, 0));
	}
}
