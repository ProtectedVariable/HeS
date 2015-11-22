package xyz.hes.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

public class Main {

	// BASE
	public static int width = 1600;
	public static int height = 900;

	public static boolean running = false;

	public static long window;
	private GLFWKeyCallback keyCallback;
	private GLFWWindowSizeCallback sizeCallback;
	private GLFWMouseButtonCallback mouseCallback;
	private GLFWScrollCallback scrollCallback;
	
	private Game game;
	String OS;

	// BASE
	public void start() {
		running = true;
		OS = System.getProperty("os.name");
		System.out.println(OS);
		OS = OS.toLowerCase();
		// Linux / Unix
		if (OS.indexOf("nux") >= 0 | OS.indexOf("nix") >= 0 | OS.indexOf("aix") >= 0) {
			throw new RuntimeException("Unix systems aren't supported yet");
		}
		run();
	}

	private void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("Could not initialize GLFW!");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, 4);

		if (OS.indexOf("mac") >= 0) {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		}

		window = glfwCreateWindow(width, height, "HeS", NULL, NULL);
		if (window == NULL) {
			System.err.println("Could not create GLFW window!");
			return;
		}

		InitWindow();
		InitGL();
		// if(!SteamAPI.init(System.getProperty("user.dir"))) {
		// System.err.println("Erreur Steam");
		// }
	}

	public void run() {
		init();
		game = new Game();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		keyCallback.release();
		sizeCallback.release();
		mouseCallback.release();
		scrollCallback.release();
		// SteamAPI.shutdown();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		game.Render();
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println("Error " + error);
		glfwSwapBuffers(window);
	}

	private void update() {
		game.Update();
		glfwPollEvents();
	}

	private void InitGL() {
		GLContext.createFromCurrent();
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		glViewport(0, 0, width, height);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL13.GL_MULTISAMPLE);
	}

	private void InitWindow() {

		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2 + 50,
				(GLFWvidmode.height(vidmode) - height) / 2);

		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwSetWindowSizeCallback(window, sizeCallback = new ResizeHandler());
		glfwSetMouseButtonCallback(window, mouseCallback = new MouseHandler());
		glfwSetScrollCallback(window, scrollCallback = new ScrollHandler());
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);

	}

	public static void main(String[] args) {
		new Main().start();
	}
}
