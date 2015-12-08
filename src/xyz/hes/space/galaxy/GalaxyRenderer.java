package xyz.hes.space.galaxy;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL13;

import me.soldier.graphics.Texture;
import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import xyz.hes.core.Camera;

public class GalaxyRenderer {

	private GalaxyShader shader;

	Vector3f nulVec = new Vector3f();
	Vector3f oneVec = new Vector3f(1, 1, 1);
	private ProjectionMatrix pr_mat;
	private Texture tex;

	public GalaxyRenderer(ProjectionMatrix pr) {
		this.pr_mat = pr;
		shader = new GalaxyShader();
		//tex = new Texture("res/particle.png");
	}

	public void renderGalaxies(Camera camera, Galaxy[] galaxies) {
		shader.setPr_mat(this.getPr_mat());
		shader.setVw_mat(camera.vw_matrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//tex.bind();
		shader.start();
		shader.loadOnceUniforms();
		for (int i = 0; i < galaxies.length; i++) {
			Galaxy galaxy = galaxies[i];
			glBindVertexArray(galaxy.getModel().getVaoID());
			glEnableVertexAttribArray(0);
			shader.getMl_mat().Transform(galaxy.getPosition(), galaxy.getRx(), galaxy.getRy(), galaxy.getRz(), oneVec);
			if(galaxy.MouseHover) {
				shader.setColor(Vector3f.Multiply(galaxy.getColor(), 1.5f));
			} else {
				shader.setColor(galaxy.getColor());
			}
			shader.loadUniforms();
			glDrawArrays(GL_POINTS, 0, galaxies[i].getModel().getVertexCount());
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
		}
		shader.stop();
	}

	public void renderGalaxy(Camera camera, Galaxy g) {
		// rendu d'une galaxie, peu de vertice donc peu dépendent du LOD
		// on peux voir chaque sysème solaire selectionable, entourés de H2 et
		// de poussiere
	}

	public ProjectionMatrix getPr_mat() {
		return pr_mat;
	}

	public void setPr_mat(ProjectionMatrix pr_mat) {
		this.pr_mat = pr_mat;
	}

}