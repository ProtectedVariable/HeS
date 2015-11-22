package me.soldier.math;

import java.nio.*;

public class Matrix4f {

	public static final int SIZE = 4 * 4;
	/** [column + line * 4] */
	public float[] elements = new float[SIZE];

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

		float f = (float) (1.0f / Math.tan(fov));

		float frustrum_length = near - far;

		this.elements[0 + 0 * 4] = f / aspect;

		this.elements[1 + 1 * 4] = f;

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
			/*
			 * elements[0] elements[1] elements[2] elements[3]
			 * elements[0+1*4] elements[1+1*4] elements[2+1*4] elements[3+1*4]
			 * elements[0+2*4] elements[1+2*4] elements[2+2*4] elements[3+2*4]
			 * elements[0+3*4] elements[1+3*4] elements[2+3*4] elements[3+3*4]
			 */
			if (dest == null)
				dest = new Matrix4f();
			float determinant_inv = 1f / determinant;

			// first row
			float t00 = determinant3x3(src.elements[1 + 1 * 4], src.elements[2 + 1 * 4], src.elements[3 + 1 * 4], src.elements[1 + 2 * 4], src.elements[2 + 2 * 4], src.elements[3 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 3 * 4], src.elements[3 + 3 * 4]);
			float t01 = -determinant3x3(src.elements[0 + 1 * 4], src.elements[2 + 1 * 4], src.elements[3 + 1 * 4], src.elements[0 + 2 * 4], src.elements[2 + 2 * 4], src.elements[3 + 2 * 4], src.elements[0 + 3 * 4], src.elements[2 + 3 * 4], src.elements[3 + 3 * 4]);
			float t02 = determinant3x3(src.elements[0 + 1 * 4], src.elements[1 + 1 * 4], src.elements[3 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 2 * 4], src.elements[3 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 3 * 4], src.elements[3 + 3 * 4]);
			float t03 = -determinant3x3(src.elements[0 + 1 * 4], src.elements[1 + 1 * 4], src.elements[2 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 2 * 4], src.elements[2 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 3 * 4], src.elements[2 + 3 * 4]);
			// second row
			float t10 = -determinant3x3(src.elements[1], src.elements[2], src.elements[3], src.elements[1 + 2 * 4], src.elements[2 + 2 * 4], src.elements[3 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 3 * 4], src.elements[3 + 3 * 4]);
			float t11 = determinant3x3(src.elements[0], src.elements[2], src.elements[3], src.elements[0 + 2 * 4], src.elements[2 + 2 * 4], src.elements[3 + 2 * 4], src.elements[0 + 3 * 4], src.elements[2 + 3 * 4], src.elements[3 + 3 * 4]);
			float t12 = -determinant3x3(src.elements[0], src.elements[1], src.elements[3], src.elements[0 + 2 * 4], src.elements[1 + 2 * 4], src.elements[3 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 3 * 4], src.elements[3 + 3 * 4]);
			float t13 = determinant3x3(src.elements[0], src.elements[1], src.elements[2], src.elements[0 + 2 * 4], src.elements[1 + 2 * 4], src.elements[2 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 3 * 4], src.elements[2 + 3 * 4]);
			// third row
			float t20 = determinant3x3(src.elements[1], src.elements[2], src.elements[3], src.elements[1 + 1 * 4], src.elements[2 + 1 * 4], src.elements[3 + 1 * 4], src.elements[1 + 3 * 4], src.elements[2 + 3 * 4], src.elements[3 + 3 * 4]);
			float t21 = -determinant3x3(src.elements[0], src.elements[2], src.elements[3], src.elements[0 + 1 * 4], src.elements[2 + 1 * 4], src.elements[3 + 1 * 4], src.elements[0 + 3 * 4], src.elements[2 + 3 * 4], src.elements[3 + 3 * 4]);
			float t22 = determinant3x3(src.elements[0], src.elements[1], src.elements[3], src.elements[0 + 1 * 4], src.elements[1 + 1 * 4], src.elements[3 + 1 * 4], src.elements[0 + 3 * 4], src.elements[1 + 3 * 4], src.elements[3 + 3 * 4]);
			float t23 = -determinant3x3(src.elements[0], src.elements[1], src.elements[2], src.elements[0 + 1 * 4], src.elements[1 + 1 * 4], src.elements[2 + 1 * 4], src.elements[0 + 3 * 4], src.elements[1 + 3 * 4], src.elements[2 + 3 * 4]);
			// fourth row
			float t30 = -determinant3x3(src.elements[1], src.elements[2], src.elements[3], src.elements[1 + 1 * 4], src.elements[2 + 1 * 4], src.elements[3 + 1 * 4], src.elements[1 + 2 * 4], src.elements[2 + 2 * 4], src.elements[3 + 2 * 4]);
			float t31 = determinant3x3(src.elements[0], src.elements[2], src.elements[3], src.elements[0 + 1 * 4], src.elements[2 + 1 * 4], src.elements[3 + 1 * 4], src.elements[0 + 2 * 4], src.elements[2 + 2 * 4], src.elements[3 + 2 * 4]);
			float t32 = -determinant3x3(src.elements[0], src.elements[1], src.elements[3], src.elements[0 + 1 * 4], src.elements[1 + 1 * 4], src.elements[3 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 2 * 4], src.elements[3 + 2 * 4]);
			float t33 = determinant3x3(src.elements[0], src.elements[1], src.elements[2], src.elements[0 + 1 * 4], src.elements[1 + 1 * 4], src.elements[2 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 2 * 4], src.elements[2 + 2 * 4]);

			// transpose and divide by the determinant
			dest.elements[0] = t00 * determinant_inv;
			dest.elements[1 + 1 * 4] = t11 * determinant_inv;
			dest.elements[2 + 2 * 4] = t22 * determinant_inv;
			dest.elements[3 + 3 * 4] = t33 * determinant_inv;
			dest.elements[1] = t10 * determinant_inv;
			dest.elements[0 + 1 * 4] = t01 * determinant_inv;
			dest.elements[0 + 2 * 4] = t02 * determinant_inv;
			dest.elements[2] = t20 * determinant_inv;
			dest.elements[2 + 1 * 4] = t21 * determinant_inv;
			dest.elements[1 + 2 * 4] = t12 * determinant_inv;
			dest.elements[3] = t30 * determinant_inv;
			dest.elements[0 + 3 * 4] = t03 * determinant_inv;
			dest.elements[3 + 1 * 4] = t31 * determinant_inv;
			dest.elements[1 + 3 * 4] = t13 * determinant_inv;
			dest.elements[2 + 3 * 4] = t23 * determinant_inv;
			dest.elements[3 + 2 * 4] = t32 * determinant_inv;
			return dest;
		} else
			return null;
	}

	private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
		return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
	}

	public float determinant() {
		float f =
				elements[0]
						* ((elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] + elements[2 + 1 * 4] * elements[3 + 2 * 4] * elements[1 + 3 * 4] + elements[3 + 1 * 4] * elements[1 + 2 * 4] * elements[2 + 3 * 4])
								- elements[3 + 1 * 4] * elements[2 + 2 * 4] * elements[1 + 3 * 4]
								- elements[1 + 1 * 4] * elements[3 + 2 * 4] * elements[2 + 3 * 4]
								- elements[2 + 1 * 4] * elements[1 + 2 * 4] * elements[3 + 3 * 4]);
		f -= elements[1]
				* ((elements[0 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] + elements[2 + 1 * 4] * elements[3 + 2 * 4] * elements[0 + 3 * 4] + elements[3 + 1 * 4] * elements[0 + 2 * 4] * elements[2 + 3 * 4])
						- elements[3 + 1 * 4] * elements[2 + 2 * 4] * elements[0 + 3 * 4]
						- elements[0 + 1 * 4] * elements[3 + 2 * 4] * elements[2 + 3 * 4]
						- elements[2 + 1 * 4] * elements[0 + 2 * 4] * elements[3 + 3 * 4]);
		f += elements[2]
				* ((elements[0 + 1 * 4] * elements[1 + 2 * 4] * elements[3 + 3 * 4] + elements[1 + 1 * 4] * elements[3 + 2 * 4] * elements[0 + 3 * 4] + elements[3 + 1 * 4] * elements[0 + 2 * 4] * elements[1 + 3 * 4])
						- elements[3 + 1 * 4] * elements[1 + 2 * 4] * elements[0 + 3 * 4]
						- elements[0 + 1 * 4] * elements[3 + 2 * 4] * elements[1 + 3 * 4]
						- elements[1 + 1 * 4] * elements[0 + 2 * 4] * elements[3 + 3 * 4]);
		f -= elements[3]
				* ((elements[0 + 1 * 4] * elements[1 + 2 * 4] * elements[2 + 3 * 4] + elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[0 + 3 * 4] + elements[2 + 1 * 4] * elements[0 + 2 * 4] * elements[1 + 3 * 4])
						- elements[2 + 1 * 4] * elements[1 + 2 * 4] * elements[0 + 3 * 4]
						- elements[0 + 1 * 4] * elements[2 + 2 * 4] * elements[1 + 3 * 4]
						- elements[1 + 1 * 4] * elements[0 + 2 * 4] * elements[2 + 3 * 4]);
		return f;
	}

	public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
		if (dest == null)
			dest = new Vector4f();

		float x = left.elements[0] * right.x + left.elements[0+1*4] * right.y + left.elements[0+2*4] * right.z + left.elements[0+3*4] * right.w;
		float y = left.elements[1+0*4] * right.x + left.elements[1+1*4] * right.y + left.elements[1+2*4] * right.z + left.elements[1+3*4] * right.w;
		float z = left.elements[2+0*4] * right.x + left.elements[2+1*4] * right.y + left.elements[2+2*4] * right.z + left.elements[2+3*4] * right.w;
		float w = left.elements[3+0*4] * right.x + left.elements[3+1*4] * right.y + left.elements[3+2*4] * right.z + left.elements[3+3*4] * right.w;

		dest.x = x;
		dest.y = y;
		dest.z = z;
		dest.w = w;

		return dest;
	}
	
	@Override
	public String toString() {
		String str = "";
		int limit = -1;
		for (float f : this.elements) {
			limit++;
			if (limit == 4) {
				limit = 0;
				System.out.println();
			}
			System.out.print("|" + f + "|");
		}
		return str;
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
