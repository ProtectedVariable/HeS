package me.soldier.graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import me.soldier.math.Matrix4f;
import me.soldier.math.Vector2f;
import me.soldier.math.Vector3f;
import me.soldier.math.Vector4f;

/**
 * @author Osoldier
 * @since 7 oct. 2014
 */
public abstract class Shader {


	private int programID, vertexShaderID, fragmentShaderID, geometryShaderID;
	
	/**
	 * Creates a shader program with a vertex and a fragment stage
	 * @param vertexFile Path to vertex shader
	 * @param fragmentFile Path to Fragment shader
	 */
	public Shader(String vertexFile, String fragmentFile) {
		this(vertexFile, fragmentFile, null);
	}
	
	/**
	 * Creates a shader program with a vertex, a geometry and a fragment stage
	 * @param vertexFile Path to vertex shader
	 * @param fragmentFile Path to fragment shader
	 * @param geometryFile Path to geometry shader
	 */
	public Shader(String vertexFile, String fragmentFile, String geometryFile) {
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		if(geometryFile != null) geometryShaderID = loadShader(geometryFile, GL_GEOMETRY_SHADER);
		
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		if(geometryFile != null) glAttachShader(programID, geometryShaderID);
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	public abstract void loadUniforms();
	
	protected void bindAttribute(int attr, String varName) {
		glBindAttribLocation(programID, attr, varName);
	}
	
	public void start() {
		glUseProgram(programID);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public int getAttributeLocation(String pName) {
		start();
		return glGetAttribLocation(programID, pName);
	}
	
	public int getUniformLocation(String name) {
		return glGetUniformLocation(programID, name);
	}

	public void setUniform(int pLocation, float value) {
		start();
		glUniform1f(pLocation, value);
	}

	public void setUniform(int pLocation, Vector3f value) {
		start();
		glUniform3f(pLocation, value.x, value.y, value.z);
	}
	
	public void setUniform(int pLocation, Vector2f value) {
		start();
		glUniform2f(pLocation, value.x, value.y);
	}
	
	public void setUniform(int pLocation, Vector4f value) {
		start();
		glUniform4f(pLocation, value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(int pLocation, boolean value) {
		start();
		glUniform1i(pLocation, (value) ? 1 : 0);
	}


	public void setUniform(int pLocation, int value) {
		start();
		glUniform1i(pLocation, value);
	}

	public void setUniform(int pLocation, Matrix4f matrix) {
		start();
		glUniformMatrix4fv(pLocation, false, matrix.toFloatBuffer());
	}
	
	public void cleanUp() {
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder shaderSrc = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				shaderSrc.append(line).append("\n");
			}
			reader.close();
		} catch(IOException e) {
			System.err.println("Couldn't read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSrc);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shaderID));
			System.err.println("Couldn't compile shader");
			System.exit(-1);
		}
		return shaderID;
	}

	
}
