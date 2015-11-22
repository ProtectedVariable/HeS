package me.soldier.math;

public class ViewMatrix extends Matrix4f {
	
	public ViewMatrix() {
		Identity();
	}
	
	public void Transform(Vector3f position, Vector3f angle) {
		this.Identity();
		position = new Vector3f(-position.x, -position.y, -position.z);
		this.translate(position);
		this.multiply(rotate(-angle.y, 0, 1, 0));
		this.multiply(rotate(-angle.x, 1, 0, 0));
		this.multiply(rotate(-angle.z, 0, 0, 1));
	}
}
