package me.it.lib.postprocess.fx;

import me.it.lib.graphics.*;

public class BlurShader extends Shader {	
	
	private final static String VERT_FILE = "src/me/it/lib/postprocess/fx/scrquad.vert";
	private final static String FRAG_FILE = "src/me/it/lib/postprocess/fx/blur.frag";

	
	public BlurShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	public void loadUniforms() {
	}

}
