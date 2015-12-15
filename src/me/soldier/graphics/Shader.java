package me.soldier.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.*;

import me.soldier.math.*;

/**
 * @author Osoldier
 * @Project OSEngine
 * @since 7 oct. 2014
 */
public abstract class Shader {


	private int programID, vertexShaderID, fragmentShaderID, geometryShaderID;
	
	public Shader(String vertexFile, String fragmentFile) {
		this(vertexFile, fragmentFile, null);
	}
	
	public Shader(String vertexFile, String fragmentFile, String geometryFile) {
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		if(geometryFile != null) geometryShaderID = loadShader(geometryFile, GL_GEOMETRY_SHADER);
		
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		if(geometryFile != null) glAttachShader(programID, geometryShaderID);
		bindAttributes();
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	
	protected void bindAttributes() {
		
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
	
	public void setUniform(int pLocation, Vector4f value) {
		start();
		glUniform4f(pLocation, value.x, value.y, value.z, value.w);
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
