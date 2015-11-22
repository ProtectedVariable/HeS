package me.soldier.math;

public class Vector3f {

	public float x, y, z;

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f() {
		this(0, 0, 0);
	}

	public void normalize() {
		float l = length();
		this.x = this.x / l;
		this.y = this.y / l;
		this.z = this.z / l;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public static Vector3f Add(Vector3f v1, Vector3f v2) {
		return new Vector3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

	public static Vector3f Multiply(Vector3f v1, float s) {
		return new Vector3f(v1.x * s, v1.y * s, v1.z * s);
	}

	public static Vector3f Sub(Vector3f v1, Vector3f v2) {
		return new Vector3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}

	public static Vector3f Cross(Vector3f v1, Vector3f v2) {
		return new Vector3f(v1.y*v2.z-(v2.y*v1.z), -(v1.x*v2.z-(v2.x*v1.z)), v1.x*v2.y-(v2.x-v1.y));
	}

}
