package me.it.hes.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Thomas Ibanez on 27 juin 2016
 */
public class OSSpecifics {

    private static boolean WIN, OSX, LINUX;

    static {
	String system = System.getProperty("os.name").toLowerCase();
	OSX = system.indexOf("mac") >= 0;
	WIN = system.indexOf("win") >= 0;
	LINUX = system.indexOf("nux") >= 0 | system.indexOf("nix") >= 0 | system.indexOf("aix") >= 0;
	assert ((WIN && !OSX && !LINUX) || (!WIN && OSX && !LINUX) || (!WIN && !OSX && LINUX));
    }

    public static void GLFWSpecifics() {
	if (OSX) {
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
	}
    }

    public static void OpenGLSpecifics() {
    }

}
