package me.soldier.util;

import me.soldier.math.Matrix4f;
import me.soldier.math.Vector2f;
import me.soldier.math.Vector3f;
import me.soldier.math.Vector4f;
import xyz.hes.core.Main;

public class MousePicker {

	private Vector3f currentRay;

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private float mouseX, mouseY;

	public MousePicker(Matrix4f viewMatrix, Matrix4f projectionMatrix) {
		this.viewMatrix = viewMatrix;
		this.projectionMatrix = projectionMatrix;
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}

	public void Update(float x, float y) {
		currentRay = calculateMouseRay();
		this.mouseX = x;
		this.mouseY = y;
	}

	private Vector3f calculateMouseRay() {
		// Viewport -> Normalized device
		Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
		// Normalized Coords -> Homogenous clip space
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
		// Homogenous coords -> eye space (view)
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		// Eye space -> World space
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalize();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalizedDeviceCoords(float x, float y) {
		return new Vector2f((2f * x) / Main.width - 1f, ((2f * y) / Main.height - 1f));
	}

	/**
	 * Check if the current vector intersect with bounding sphere
	 * @param c center of the sphere
	 * @param o origin of the vector (camera position)
	 * @param r radius
	 * @return true if there is one or more intersections, false otherwise
	 */
	public boolean collideWithObj(Vector3f c, Vector3f o, float r) {
		float delta = -1;
		System.out.println(mouseX+" "+mouseY+" "+c);
		Vector3f diff = Vector3f.Sub(c, o);
		diff.y = -diff.y;
		float b = Vector3f.Dot(currentRay, diff);
		float lengthSquared = diff.length()*diff.length();
		float radiusSquared = r * r;
		delta = radiusSquared+(b*b)-lengthSquared;
		return delta >= 0;
	}
}
