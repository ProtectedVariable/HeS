package me.soldier.postprocess.fx;

import me.soldier.graphics.Shader;

public class BlurShader extends Shader {	
	
	private final static String VERT_FILE = "src/me/soldier/postprocess/fx/scrquad.vert";
	private final static String FRAG_FILE = "src/me/soldier/postprocess/fx/blur.frag";

	
	public BlurShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	public void loadUniforms() {
	}

}
