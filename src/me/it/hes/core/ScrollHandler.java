package me.it.hes.core;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollHandler extends GLFWScrollCallback {
	
	private static float Y = 0;
	
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		Y += yoffset/2f;
	}

	public static float getY() {
		return Y;
	}
}
