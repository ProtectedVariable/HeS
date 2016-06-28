package libs.thinmatrix.fontRendering;

import me.it.lib.graphics.*;
import me.it.lib.math.*;

public class FontShader extends Shader {

    private static final String VERTEX_FILE = "src/libs/thinmatrix/fontRendering/fontVertex.txt";
    private static final String FRAGMENT_FILE = "src/libs/thinmatrix/fontRendering/fontFragment.txt";

    private int colour_loc, trans_loc, view_loc, proj_loc, td_loc;

    public FontShader() {
	super(VERTEX_FILE, FRAGMENT_FILE);
	colour_loc = this.getUniformLocation("colour");
	trans_loc = this.getUniformLocation("translation");
	view_loc = this.getUniformLocation("view");
	proj_loc = this.getUniformLocation("projection");
	td_loc = this.getUniformLocation("threeD");
    }

    @Override
    public void loadUniforms() {
    }

    protected void loadColour(Vector3f colour) {
	this.setUniform(colour_loc, colour);
    }

    protected void loadTranslation(Vector3f translation) {
	this.setUniform(trans_loc, translation);
    }

    protected void loadView(Matrix4f view) {
	this.setUniform(view_loc, view);
    }

    protected void loadProj(Matrix4f proj) {
	this.setUniform(proj_loc, proj);
    }

    protected void load3D(boolean td) {
	this.setUniform(td_loc, td ? 1 : 0);
    }

}
