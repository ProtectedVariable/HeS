package xyz.hes.space.planets;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL13;

import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import xyz.hes.core.Camera;

public class PlanetRender {

	private PlanetShader shader;
	private ProjectionMatrix prMat;

	public PlanetRender(ProjectionMatrix pr) {
		this.prMat = pr;
		shader = new PlanetShader();
	}

	public void renderPlanet(Camera camera, Planet p) {
		shader.setPrMat(this.getPrMat());
		shader.setVwMat(camera.vw_matrix);
		shader.getMlMat().Transform(p.getPosition(), p.getRx(), p.getRy(), p.getRz(), Vector3f.oneVec);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		shader.start();
		shader.loadOnceUniforms();
		
		shader.loadUniforms();
		
		glBindVertexArray(Planet.getModel().getVaoID());
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, Planet.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
		shader.stop();
	}

	public ProjectionMatrix getPrMat() {
		return prMat;
	}

	public void setPrMat(ProjectionMatrix prMat) {
		this.prMat = prMat;
	}

}
