package me.soldier.math;


public class ModelMatrix extends Matrix4f {

	public ModelMatrix() {
		this.Identity();
	}

	public void Transform(Vector3f position, float angle, Vector3f rotation, Vector3f scale) {
		this.Identity();
		this.Rotate(angle, rotation.x, rotation.y, rotation.z);
		this.Scale(scale);
		this.translate(position);

	}

	public void Rotate(float angle, float x, float y, float z) {
		this.multiply(rotate(angle, x, y, z));
	}

	public void Translate(Vector3f position) {
		translate(position);
	}

	public void Scale(Vector3f scale) {
		this.multiply(scale(scale));
	}

}
