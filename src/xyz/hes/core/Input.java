package xyz.hes.core;

import org.lwjgl.glfw.*;

public class Input extends GLFWKeyCallback {

	public static boolean[] keys = new boolean[65536];

	public void invoke(long window, int key, int scancode, int action, int mods) {
		if(key >= 0)
		keys[key] = action != GLFW.GLFW_RELEASE; 
	}
		
	public static boolean isKeyDown(int keycode) {
		return keys[keycode];
	}
}
