package me.it.hes.space.solarsystems;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.opengl.GL13;

import me.it.hes.core.*;
import me.it.hes.space.planets.*;
import me.it.lib.math.*;

public class SolarSystemRenderer {

	private SolarSystemShader shader;
	private ProjectionMatrix prMat;

	public SolarSystemRenderer(ProjectionMatrix pr) {
		shader = new SolarSystemShader();
	}

	public void renderSolarSystem(Camera camera, SolarSystem system) {
		shader.setPrMat(this.getPrMat());
		shader.setVwMat(camera.vw_matrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		shader.start();
		shader.loadOnceUniforms();

		for (Planet p : system.getPlanets()) {
			shader.getMlMat().Transform(p.getPosition(), p.getRx(), p.getRy(), p.getRz(), new Vector3f(p.getSize(), p.getSize(), p.getSize()));
			shader.loadUniforms();

			glBindVertexArray(Planet.getModel().getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);
			glDrawElements(GL_TRIANGLES, Planet.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glDisableVertexAttribArray(2);
			glBindVertexArray(0);
		}
		Vector3f scale = new Vector3f(system.getSun().getSize(), system.getSun().getSize(), system.getSun().getSize());
		//the star is on the middle of the screen and doesn't rotate (the moving effect will only come from the dynamic texture)
		shader.getMlMat().Transform(Vector3f.nulVec, 0, 0, 0, scale);
		shader.loadUniforms();
		
		glBindVertexArray(Star.getModel().getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glDrawArrays(GL_TRIANGLES, 0, Star.getModel().getVertexCount());
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
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
