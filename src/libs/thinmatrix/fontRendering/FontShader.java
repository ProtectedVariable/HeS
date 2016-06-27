package libs.thinmatrix.fontRendering;

import me.it.lib.graphics.*;
import me.it.lib.math.*;

public class FontShader extends Shader {

	private static final String VERTEX_FILE = "src/libs/thinmatrix/fontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/libs/thinmatrix/fontRendering/fontFragment.txt";
	
	private int colour_loc, trans_loc;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		colour_loc = this.getUniformLocation("colour");
		trans_loc = this.getUniformLocation("translation");
	}
	
	@Override
	public void loadUniforms() {
	}
	
	protected void loadColour(Vector3f colour) {
		this.setUniform(colour_loc, colour);
	}
	
	protected void loadTranslation(Vector2f translation) {
		this.setUniform(trans_loc, translation);
	}

}
