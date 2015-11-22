package xyz.hes.core;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollHandler extends GLFWScrollCallback {
	
	private static float dY = 0;
	
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		dY = Math.abs(yoffset) <= 0.1?0:(float)yoffset;
	}

	public static float getdY() {
		return dY;
	}
}
