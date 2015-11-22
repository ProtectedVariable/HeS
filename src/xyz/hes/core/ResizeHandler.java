package xyz.hes.core;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class ResizeHandler extends GLFWWindowSizeCallback {

	public ResizeHandler() {
		
	}
	
	@Override
	public void invoke(long window, int width, int height) {
		Main.width = width;
		Main.height = height;
		GL11.glViewport(0, 0, width, height);
	}
}
