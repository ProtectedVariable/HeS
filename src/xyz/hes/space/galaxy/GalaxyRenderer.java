package xyz.hes.space.galaxy;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL13;

import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import xyz.hes.core.Camera;
import xyz.hes.space.Background;
import xyz.hes.space.ShootingStar;
import xyz.hes.space.solarsystems.SolarSystem;
import xyz.hes.space.solarsystems.SolarSystemShader;
import xyz.hes.space.solarsystems.Star;

public class GalaxyRenderer {

	private GalaxyShader gShader;
	private SolarSystemShader sShader;

	private ProjectionMatrix pr_mat;
	//private Texture tex;

	public GalaxyRenderer(ProjectionMatrix pr) {
		this.pr_mat = pr;
		gShader = new GalaxyShader();
		sShader = new SolarSystemShader();
		//tex = new Texture("res/particle.png");
	}

	public void renderGalaxies(Camera camera, Galaxy[] galaxies) {
		gShader.setPr_mat(this.getPr_mat());
		gShader.setVw_mat(camera.vw_matrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//tex.bind();
		gShader.start();
		gShader.loadOnceUniforms();
		for (int i = 0; i < galaxies.length; i++) {
			Galaxy galaxy = galaxies[i];
			glBindVertexArray(galaxy.getModel().getVaoID());
			glEnableVertexAttribArray(0);
			gShader.getMl_mat().Transform(galaxy.getPosition(), galaxy.getRx(), galaxy.getRy(), galaxy.getRz(), Vector3f.oneVec);
			if(galaxy.MouseHover) {
				gShader.setColor(Vector3f.Multiply(galaxy.getColor(), 1.5f));
			} else {
				gShader.setColor(galaxy.getColor());
			}
			gShader.loadUniforms();
			glDrawArrays(GL_POINTS, 0, galaxies[i].getModel().getVertexCount());
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
		}
		gShader.stop();
	}
	
	public void renderGalaxy(Camera camera, Galaxy g) {
		sShader.start();
		
		camera.position.x = 0;
		camera.position.y = 0;
		camera.position.z = 70;
		camera.lookThrough();
		
		sShader.setPrMat(this.getPr_mat());
		sShader.setVwMat(camera.vw_matrix);
		sShader.loadOnceUniforms();
		
		//TODO Add dust and stuff
		for(SolarSystem ss : g.getSystems()) {
			sShader.getMlMat().Transform(ss.getPosition(), 0, 0, 0, Vector3f.oneVec);
			sShader.loadUniforms();
			
			glBindVertexArray(Star.getModel().getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);
			glDrawElements(GL_TRIANGLES, Star.getModel().getVertexCount(), GL_UNSIGNED_INT, 0);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glDisableVertexAttribArray(2);
			glBindVertexArray(0);
		}
		
		gShader.stop();
	}
	
	public void renderBackground(Background back) {
		gShader.start();
		gShader.setColor(Vector3f.oneVec);
		gShader.getMl_mat().Transform(Vector3f.nulVec, 0, 0, 0, Vector3f.oneVec);
		gShader.loadUniforms();
		glBindVertexArray(back.getModel().getVaoID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_POINTS, 0, back.getModel().getVertexCount());
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	
		for(ShootingStar ss : back.getSsa()) {
			if(ss != null) {
				gShader.getMl_mat().Transform(new Vector3f(ss.getX(), ss.getY(), -1), 0, 0, 0, Vector3f.oneVec);
				gShader.loadUniforms();
				glBindVertexArray(ss.getModel().getVaoID());
				glEnableVertexAttribArray(0);
				glDrawArrays(GL_POINTS, 0, ss.getModel().getVertexCount());
				glDisableVertexAttribArray(0);
				glBindVertexArray(0);
			}
		}

		gShader.stop();
	}

	public ProjectionMatrix getPr_mat() {
		return pr_mat;
	}

	public void setPr_mat(ProjectionMatrix pr_mat) {
		this.pr_mat = pr_mat;
	}

}