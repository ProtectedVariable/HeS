package me.it.lib.math;

public class ProjectionMatrix extends Matrix4f {

	

	public ProjectionMatrix(float l, float r, float b, float t, float near, float far) {
		this.orthographic(l, r, b, t, near, far);
	}

	public ProjectionMatrix(float fov, float aspect, float near, float far) {
		this.perspective(fov, aspect, near, far);
	}
}
