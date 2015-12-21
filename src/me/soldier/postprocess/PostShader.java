package me.soldier.postprocess;

import me.soldier.graphics.*;

public class PostShader extends Shader {	
	
	private final static String VERT_FILE = "src/xyz/hes/space/solarsystems/hdr.vert";
	private final static String FRAG_FILE = "src/xyz/hes/space/solarsystems/hdr.frag";

	
	public PostShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	public void loadUniforms() {
	}

}
