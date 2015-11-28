package xyz.hes.space.galaxy;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import me.soldier.meshutil.Loader;
import me.soldier.meshutil.Model;
import xyz.hes.core.Camera;

public class GalaxyRenderer {

	private Model GalaxyModel_1;
	private GalaxyShader shader;

	static float[] cubeVertexData = new float[] {
			-1.0f, -1.0f, 1.0f,
			1.0f, -1.0f, 1.0f,
			1.0f, 1.0f, 1.0f,
			-1.0f, 1.0f, 1.0f,

			-1.0f, -1.0f, -1.0f,
			1.0f, -1.0f, -1.0f,
			1.0f, 1.0f, -1.0f,
			-1.0f, 1.0f, -1.0f
	};

	static int[] indicesVboData = new int[] {
			0, 1, 2, 2, 3, 0, // FRONT
			3, 2, 6, 6, 7, 3, // TOP
			7, 6, 5, 5, 4, 7, // BACK
			4, 0, 3, 3, 7, 4, // LEFT
			0, 4, 5, 5, 1, 0, // BOTTOM
			1, 5, 6, 6, 2, 1, // RIGHT
	};

	static float[] texCoords = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1,
			0, 0,
			1, 0,
			1, 1,
			0, 1
	};

	Vector3f nulVec = new Vector3f();
	Vector3f oneVec = new Vector3f(1, 1, 1);
	private ProjectionMatrix pr_mat;
	
	public GalaxyRenderer(ProjectionMatrix pr) {
		this.pr_mat = pr;
		GalaxyModel_1 = Loader.createModelVAO(cubeVertexData, cubeVertexData, texCoords, indicesVboData);
		shader = new GalaxyShader();
	}

	public void renderGalaxies(Camera camera, Galaxy[] galaxies) {
		shader.setPr_mat(this.getPr_mat());
		shader.setVw_mat(camera.vw_matrix);
		
		shader.start();
		glBindVertexArray(GalaxyModel_1.getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		shader.loadOnceUniforms();
		for (int i = 0; i < galaxies.length;i++) {
			shader.getMl_mat().Transform(galaxies[i].getPosition(), 0, nulVec, oneVec);
			shader.loadUniforms();		
			shader.setUniform(shader.getUniformLocation("color"), galaxies[i].getColor());
			glDrawElements(GL_TRIANGLES, GalaxyModel_1.getVertexCount(), GL_UNSIGNED_INT, 0);
		}
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glBindVertexArray(0);
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
