package me.soldier.math;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Matrix4f {

	public static final int SIZE = 4 * 4;
	/** [column + line * 4] */
	public float[] elements = new float[SIZE];
	private static int m00 = 0, m01 = 1, m02 = 2, m03 = 3;
	private static int m10 = 4, m11 = 5, m12 = 6, m13 = 7;
	private static int m20 = 8, m21 = 9, m22 = 10, m23 = 11;
	private static int m30 = 12, m31 = 13, m32 = 14, m33 = 15;

	public Matrix4f() {

	}

	public void Identity() {
		for (int i = 0; i < SIZE; i++) {
			this.elements[i] = 0.0f;
		}
		this.elements[0 + 0 * 4] = 1.0f;
		this.elements[1 + 1 * 4] = 1.0f;
		this.elements[2 + 2 * 4] = 1.0f;
		this.elements[3 + 3 * 4] = 1.0f;
	}

	protected void orthographic(float left, float right, float bottom, float top, float near, float far) {
		this.Identity();
		this.elements[0 + 0 * 4] = 2.0f / (right - left);
		this.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		this.elements[2 + 2 * 4] = -2.0f / (far - near);

		this.elements[3 + 0 * 4] = -((right + left) / (right - left));
		this.elements[3 + 1 * 4] = -((top + bottom) / (top - bottom));
		this.elements[3 + 2 * 4] = -((far + near) / (far - near));

	}

	protected void perspective(float fov, float aspect, float near, float far) {
		this.Identity();

		fov = (float) Math.toRadians(fov);

		float ys = (float) ((1f / Math.tan(fov / 2f)));
		float xs = ys / aspect;

		float frustrum_length = near - far;

		this.elements[0 + 0 * 4] = xs;

		this.elements[1 + 1 * 4] = ys;

		this.elements[2 + 2 * 4] = ((far + near) / frustrum_length);

		this.elements[2 + 3 * 4] = -1;

		this.elements[3 + 2 * 4] = ((2 * near * far) / frustrum_length);

		this.elements[3 + 3 * 4] = 0;
	}

	protected void translate(Vector3f vector) {
		this.elements[3 + 0 * 4] = vector.x;
		this.elements[3 + 1 * 4] = vector.y;
		this.elements[3 + 2 * 4] = vector.z;
	}

	protected Matrix4f scale(Vector3f vector) {
		Matrix4f result = new Matrix4f();
		result.Identity();
		result.elements[0 + 0 * 4] = vector.x;
		result.elements[1 + 1 * 4] = vector.y;
		result.elements[2 + 2 * 4] = vector.z;
		return result;
	}

	protected Matrix4f rotate(float angle, float x, float y, float z) {
		Matrix4f result = new Matrix4f();
		result.Identity();
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		float omc = 1.0f - cos;

		result.elements[0 + 0 * 4] = x * omc + cos;
		result.elements[1 + 0 * 4] = x * y * omc - z * sin;
		result.elements[2 + 0 * 4] = x * z * omc + y * sin;

		result.elements[0 + 1 * 4] = y * x * omc + z * sin;
		result.elements[1 + 1 * 4] = y * omc + cos;
		result.elements[2 + 1 * 4] = y * z * omc - x * sin;

		result.elements[0 + 2 * 4] = x * z * omc - y * sin;
		result.elements[1 + 2 * 4] = y * z * omc + x * sin;
		result.elements[2 + 2 * 4] = z * omc + cos;

		return result;
	}

	protected void multiply(Matrix4f matrix) {
		Matrix4f result = new Matrix4f();
		result.Identity();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		this.elements = result.elements;
	}

	// Matrix op
	public Matrix4f invert() {
		return invert(this, this);
	}

	public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
		float determinant = src.determinant();

		if (determinant != 0) {

			if (dest == null)
				dest = new Matrix4f();
			float determinant_inv = 1f / determinant;

			// first row
			float t00 = determinant3x3(src.elements[m11], src.elements[m12], src.elements[m13], src.elements[m21],
					src.elements[m22], src.elements[m23], src.elements[m31], src.elements[m32], src.elements[m33]);
			float t01 = -determinant3x3(src.elements[m10], src.elements[m12], src.elements[m13], src.elements[m20],
					src.elements[m22], src.elements[m23], src.elements[m30], src.elements[m32], src.elements[m33]);
			float t02 = determinant3x3(src.elements[m10], src.elements[m11], src.elements[m13], src.elements[m20],
					src.elements[m21], src.elements[m23], src.elements[m30], src.elements[m31], src.elements[m33]);
			float t03 = -determinant3x3(src.elements[m10], src.elements[m11], src.elements[m12], src.elements[m20],
					src.elements[m21], src.elements[m22], src.elements[m30], src.elements[m31], src.elements[m32]);
			// second row
			float t10 = -determinant3x3(src.elements[m01], src.elements[m02], src.elements[m03], src.elements[m21],
					src.elements[m22], src.elements[m23], src.elements[m31], src.elements[m32], src.elements[m33]);
			float t11 = determinant3x3(src.elements[m00], src.elements[m02], src.elements[m03], src.elements[m20],
					src.elements[m22], src.elements[m23], src.elements[m30], src.elements[m32], src.elements[m33]);
			float t12 = -determinant3x3(src.elements[m00], src.elements[m01], src.elements[m03], src.elements[m20],
					src.elements[m21], src.elements[m23], src.elements[m30], src.elements[m31], src.elements[m33]);
			float t13 = determinant3x3(src.elements[m00], src.elements[m01], src.elements[m02], src.elements[m20],
					src.elements[m21], src.elements[m22], src.elements[m30], src.elements[m31], src.elements[m32]);
			// third row
			float t20 = determinant3x3(src.elements[m01], src.elements[m02], src.elements[m03], src.elements[m11],
					src.elements[m12], src.elements[m13], src.elements[m31], src.elements[m32], src.elements[m33]);
			float t21 = -determinant3x3(src.elements[m00], src.elements[m02], src.elements[m03], src.elements[m10],
					src.elements[m12], src.elements[m13], src.elements[m30], src.elements[m32], src.elements[m33]);
			float t22 = determinant3x3(src.elements[m00], src.elements[m01], src.elements[m03], src.elements[m10],
					src.elements[m11], src.elements[m13], src.elements[m30], src.elements[m31], src.elements[m33]);
			float t23 = -determinant3x3(src.elements[m00], src.elements[m01], src.elements[m02], src.elements[m10],
					src.elements[m11], src.elements[m12], src.elements[m30], src.elements[m31], src.elements[m32]);
			// fourth row
			float t30 = -determinant3x3(src.elements[m01], src.elements[m02], src.elements[m03], src.elements[m11],
					src.elements[m12], src.elements[m13], src.elements[m21], src.elements[m22], src.elements[m23]);
			float t31 = determinant3x3(src.elements[m00], src.elements[m02], src.elements[m03], src.elements[m10],
					src.elements[m12], src.elements[m13], src.elements[m20], src.elements[m22], src.elements[m23]);
			float t32 = -determinant3x3(src.elements[m00], src.elements[m01], src.elements[m03], src.elements[m10],
					src.elements[m11], src.elements[m13], src.elements[m20], src.elements[m21], src.elements[m23]);
			float t33 = determinant3x3(src.elements[m00], src.elements[m01], src.elements[m02], src.elements[m10],
					src.elements[m11], src.elements[m12], src.elements[m20], src.elements[m21], src.elements[m22]);

			// transpose and divide by the determinant
			dest.elements[m00] = t00 * determinant_inv;
			dest.elements[m11] = t11 * determinant_inv;
			dest.elements[m22] = t22 * determinant_inv;
			dest.elements[m33] = t33 * determinant_inv;
			dest.elements[m01] = t10 * determinant_inv;
			dest.elements[m10] = t01 * determinant_inv;
			dest.elements[m20] = t02 * determinant_inv;
			dest.elements[m02] = t20 * determinant_inv;
			dest.elements[m12] = t21 * determinant_inv;
			dest.elements[m21] = t12 * determinant_inv;
			dest.elements[m03] = t30 * determinant_inv;
			dest.elements[m30] = t03 * determinant_inv;
			dest.elements[m13] = t31 * determinant_inv;
			dest.elements[m31] = t13 * determinant_inv;
			dest.elements[m32] = t23 * determinant_inv;
			dest.elements[m23] = t32 * determinant_inv;
			return dest;
		} else
			return null;
	}

	private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20,
			float t21, float t22) {
		return t00 * (t11 * t22 - t12 * t21)
				+ t01 * (t12 * t20 - t10 * t22)
				+ t02 * (t10 * t21 - t11 * t20);
	}

	public float determinant() {
		float f = elements[m00]
				* ((elements[m11] * elements[m22] * elements[m33] + elements[m12] * elements[m23] * elements[m31]
						+ elements[m13] * elements[m21] * elements[m32])
						- elements[m13] * elements[m22] * elements[m31]
						- elements[m11] * elements[m23] * elements[m32]
						- elements[m12] * elements[m21] * elements[m33]);
		f -= elements[m01]
				* ((elements[m10] * elements[m22] * elements[m33] + elements[m12] * elements[m23] * elements[m30]
						+ elements[m13] * elements[m20] * elements[m32])
						- elements[m13] * elements[m22] * elements[m30]
						- elements[m10] * elements[m23] * elements[m32]
						- elements[m12] * elements[m20] * elements[m33]);
		f += elements[m02]
				* ((elements[m10] * elements[m21] * elements[m33] + elements[m11] * elements[m23] * elements[m30]
						+ elements[m13] * elements[m20] * elements[m31])
						- elements[m13] * elements[m21] * elements[m30]
						- elements[m10] * elements[m23] * elements[m31]
						- elements[m11] * elements[m20] * elements[m33]);
		f -= elements[m03]
				* ((elements[m10] * elements[m21] * elements[m32] + elements[m11] * elements[m22] * elements[m30]
						+ elements[m12] * elements[m20] * elements[m31])
						- elements[m12] * elements[m21] * elements[m30]
						- elements[m10] * elements[m22] * elements[m31]
						- elements[m11] * elements[m20] * elements[m32]);
		return f;
	}

	public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		float x = left.elements[m00] * right.x + left.elements[m10] * right.y + left.elements[m20] * right.z
				+ left.elements[m30] * right.w;
		float y = left.elements[m01] * right.x + left.elements[m11] * right.y + left.elements[m21] * right.z
				+ left.elements[m31] * right.w;
		float z = left.elements[m02] * right.x + left.elements[m12] * right.y + left.elements[m22] * right.z
				+ left.elements[m32] * right.w;
		float w = left.elements[m03] * right.x + left.elements[m13] * right.y + left.elements[m23] * right.z
				+ left.elements[m33] * right.w;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;

		return dest;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < elements.length; i++) {
			buf.append(elements[i]+" ");
			if((i+1) % 4 == 0) buf.append("\n");
		}
		return buf.toString();
	}

	public FloatBuffer toFloatBuffer() {
		return createFloatBuffer(elements);
	}

	private static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer res = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		res.put(array).flip();
		return res;
	}

}
