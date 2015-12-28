package me.soldier.graphics;

import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import xyz.hes.core.Main;

public class TextShader extends Shader {

	public Texture CurrentTexture;
	public Vector3f BackColor;
	
	public TextShader(String vertexFile, String fragmentFile) {
		super(vertexFile, fragmentFile);
		ProjectionMatrix pr = new ProjectionMatrix(0, Main.width, Main.height, 0, -1, 1);
		setUniform(getUniformLocation("pr_matrix"), pr);
	}

	@Override
	protected void bindAttributes() {
	}

	@Override
	public void loadUniforms() {
		CurrentTexture.bind();
		this.setUniform(this.getUniformLocation("text"), CurrentTexture.getId());
		this.setUniform(this.getUniformLocation("backColor"), BackColor);
	}

}
