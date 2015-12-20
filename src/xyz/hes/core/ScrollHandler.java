package xyz.hes.core;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollHandler extends GLFWScrollCallback {
	
	private static float dY = 0;
	
	@Override
	public void invoke(long window, double xoffset, double yoffset) {
		dY += yoffset/10f;
	}

	public static float getdY() {
		return dY;
	}
	
	public static void update() {
		//dY -= (dY/20*Math.abs(dY));
	}
}
