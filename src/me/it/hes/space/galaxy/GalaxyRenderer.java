package me.it.hes.space.galaxy;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.opengl.GL13;

import me.it.hes.core.*;
import me.it.hes.space.*;
import me.it.hes.space.solarsystems.*;
import me.it.lib.graphics.*;
import me.it.lib.math.*;

public class GalaxyRenderer {

	private GalaxyShader gShader;
	private SolarSystemShader sShader;
	private PointShader pShader;

	private ProjectionMatrix pr_mat;
	private Texture pointTexture;
	private Texture galaxyTexture;

	public GalaxyRenderer(ProjectionMatrix pr) {
		this.pr_mat = pr;
		gShader = new GalaxyShader();
		sShader = new SolarSystemShader();
		pShader = new PointShader();
		pointTexture = new Texture("res/particle.png", 0);
		this.galaxyTexture = new Texture("res/galaxy.png", -1.5f);
	}

	public void renderGalaxies(Camera camera, Galaxy[] galaxies) {
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE); 
		glDisable(GL_CULL_FACE);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		galaxyTexture.bind();
		
		gShader.setPr_mat(this.getPr_mat());
		gShader.setVw_mat(camera.vw_matrix);
		gShader.start();
		gShader.loadOnceUniforms();
		
		glBindVertexArray(Galaxy.getVAOID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(2);
		for (int i = 0; i < galaxies.length; i++) {
			Galaxy galaxy = galaxies[i];
			gShader.getMl_mat().Transform(galaxy.getPosition(), 0, 0, 0, new Vector3f(50, 50, 50));
			gShader.loadUniforms();
			glDrawArrays(GL_TRIANGLES, 0, 6);
		}
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
		gShader.stop();
	}
	
	public void renderGalaxy(Camera camera, Galaxy g) {
		glDisable(GL_BLEND);
		glEnable(GL_CULL_FACE);
		sShader.start();
		
		camera.position.x = 0;
		camera.position.y = 0;
		camera.position.z = 90;
		camera.lookThrough();
		
		sShader.setPrMat(this.getPr_mat());
		sShader.setVwMat(camera.vw_matrix);
		sShader.setLightPosition(Vector3f.oneVec);
		sShader.loadOnceUniforms();

		//TODO Add dust and stuff
		for(SolarSystem ss : g.getSystems()) {
			sShader.getMlMat().Transform(ss.getPosition(), 0, 0, 0, Vector3f.oneVec);
			sShader.getMlMat().Rotate(20, 1, 0, 0);
			sShader.setSource(0);
			sShader.loadUniforms();
			
			renderStarModel();

		}
		
		sShader.getMlMat().Transform(Vector3f.oneVec, 0, 0, 0, new Vector3f(3, 3, 3));
		sShader.setSource(1);
		sShader.loadUniforms();
		
		renderStarModel();
		
		gShader.stop();
		glDisable(GL_CULL_FACE);
	}
	
	private void renderStarModel() {
		glDisable(GL_BLEND);
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
	
	Vector3f electricBlue = new Vector3f(0.49019607843f, 0.97647058823f, 1);
	public void renderBackground(Camera c, Background back) {
		pointTexture.bind();
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		
		pShader.start();
		pShader.setPr_mat(pr_mat);
		pShader.setVw_mat(c.vw_matrix);
		pShader.loadOnceUniforms();
		pShader.setColor(electricBlue);
		pShader.getMl_mat().Transform(Vector3f.nulVec, 0, 0, 0, Vector3f.oneVec);
		pShader.loadUniforms();
		glBindVertexArray(back.getModel().getVaoID());
		glEnableVertexAttribArray(0);
		glDrawArrays(GL_POINTS, 0, back.getModel().getVertexCount());
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
//	
//		for(ShootingStar ss : back.getSsa()) {
//			if(ss != null) {
//				pShader.getMl_mat().Transform(new Vector3f(ss.getX(), ss.getY(), 0), 0, 0, 0, Vector3f.oneVec);
//				pShader.loadUniforms();
//				glBindVertexArray(ss.getModel().getVaoID());
//				glEnableVertexAttribArray(0);
//				glDrawArrays(GL_POINTS, 0, ss.getModel().getVertexCount());
//				glDisableVertexAttribArray(0);
//				glBindVertexArray(0);
//			}
//		}

		pShader.stop();
	}

	public ProjectionMatrix getPr_mat() {
		return pr_mat;
	}

	public void setPr_mat(ProjectionMatrix pr_mat) {
		this.pr_mat = pr_mat;
	}

}