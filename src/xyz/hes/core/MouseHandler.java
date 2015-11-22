package xyz.hes.core;

import org.lwjgl.glfw.*;

public class MouseHandler extends GLFWMouseButtonCallback {

	private static boolean[] buttons = new boolean[2];
	@Override
	public void invoke(long window, int button, int action, int mods) {
		buttons[button] = (action == GLFW.GLFW_PRESS);
	}
	
	public static boolean isButtonDown(int button) {
		return buttons[button];
	}

}
