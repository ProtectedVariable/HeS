package fontRendering;

import me.soldier.graphics.Shader;
import me.soldier.math.Vector2f;
import me.soldier.math.Vector3f;

public class FontShader extends Shader {

	private static final String VERTEX_FILE = "src/fontRendering/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/fontRendering/fontFragment.txt";
	
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
