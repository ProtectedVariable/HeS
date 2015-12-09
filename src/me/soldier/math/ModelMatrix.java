package me.soldier.math;

public class ModelMatrix extends Matrix4f {

	public ModelMatrix() {
		this.Identity();
	}

	public void Transform(Vector3f position, float anglex, float angley, float anglez, Vector3f scale) {
		this.Identity();
		this.Rotate(anglex, 1, 0, 0);
		this.Rotate(angley, 0, 1, 0);
		this.Rotate(anglez, 0, 0, 1);
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
