package me.it.lib.postprocess.fx;

import me.it.lib.graphics.*;

public class BlendShader extends Shader{

	private final static String VERT_FILE = "src/me/it/lib/postprocess/fx/scrquad.vert";
	private final static String FRAG_FILE = "src/me/it/lib/postprocess/fx/blend.frag";

	
	public BlendShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	public void loadUniforms() {
		this.setUniform(this.getUniformLocation("scene"), 1);
		this.setUniform(this.getUniformLocation("bloomBlur"), 0);
	}

}
