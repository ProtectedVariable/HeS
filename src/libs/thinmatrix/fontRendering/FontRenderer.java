package libs.thinmatrix.fontRendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import libs.thinmatrix.fontMeshCreator.FontType;
import libs.thinmatrix.fontMeshCreator.GUIText;
import me.it.lib.math.ProjectionMatrix;
import me.it.lib.math.ViewMatrix;

public class FontRenderer {

    private FontShader shader;
    private ProjectionMatrix pr;

    public FontRenderer(ProjectionMatrix pr) {
	shader = new FontShader();
	this.pr = pr;
    }

    public void render(Map<FontType, List<GUIText>> texts, ViewMatrix vw) {
	prepare(vw);
	for (FontType font : texts.keySet()) {
	    GL13.glActiveTexture(GL13.GL_TEXTURE0);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
	    for (GUIText text : texts.get(font)) {
		if (text.isRendering())
		    renderText(text);
	    }
	}
	endRendering();
    }

    public void cleanUp() {
	shader.cleanUp();
    }

    private void prepare(ViewMatrix vw) {
	GL11.glEnable(GL11.GL_BLEND);
	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	GL11.glDisable(GL11.GL_DEPTH_TEST);
	shader.start();
	shader.loadProj(pr);
	shader.loadView(vw);
    }

    private void renderText(GUIText text) {
	GL30.glBindVertexArray(text.getMesh());
	GL20.glEnableVertexAttribArray(0);
	GL20.glEnableVertexAttribArray(1);
	shader.loadColour(text.getColour());
	shader.loadTranslation(text.getPosition());
	shader.load3D(text.is3D());
	GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
	GL20.glDisableVertexAttribArray(0);
	GL20.glDisableVertexAttribArray(1);
	GL30.glBindVertexArray(0);
    }

    private void endRendering() {
	shader.stop();
	GL11.glDisable(GL11.GL_BLEND);
	GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

}
