package xyz.hes.space;

import me.soldier.math.*;
import me.soldier.meshutil.*;
import me.soldier.postprocess.*;

import org.lwjgl.opengl.*;

import xyz.hes.core.*;
import xyz.hes.space.galaxy.*;
import xyz.hes.space.planets.*;
import xyz.hes.space.solarsystems.*;
import xyz.hes.space.universe.*;

public class MasterRenderer {

	public static LevelOfDetail LOD;

	private GalaxyRenderer galaxyRenderer;
	private SolarSystemRenderer systemRenderer;
	private PlanetRender planetRenderer;

	private ProjectionMatrix perspective;
	private ProjectionMatrix othographic;

	private Framebuffer HDRfbo;
	private PostShader hdrShader;
	private int hdrQuad;

	private float fov = 50;
	private float aspect = -1f;
	private float near = 0.1f;
	private float far = 1000f;

	public MasterRenderer(LevelOfDetail lod) {
		LOD = lod;
		this.aspect = (float) Main.width / (float) Main.height;
		this.perspective = new ProjectionMatrix(fov, aspect, near, far);

		this.galaxyRenderer = new GalaxyRenderer(this.perspective);
		this.systemRenderer = new SolarSystemRenderer(this.perspective);
		this.planetRenderer = new PlanetRender(this.perspective);

		this.HDRfbo = new Framebuffer(Main.width, Main.height);
		this.hdrShader = new PostShader();
		float[] pos = { -1, 1, -1, -1, 1, -1, -1, 1, 1, -1, 1, 1 };
		float[] tex = { 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1 };
		this.hdrQuad = Loader.createGUIVAO(pos, tex);
	}

	public void RenderUniverse(Camera camera, Universe u, Background b) {
		this.galaxyRenderer.renderGalaxies(camera, u.getGalaxies());
		this.galaxyRenderer.renderBackground(camera, b);
	}

	public void RenderGalaxy(Camera camera, Galaxy g, Background b) {
		this.HDRfbo.bind();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		this.galaxyRenderer.renderGalaxy(camera, g);
		this.HDRfbo.release();
		this.hdrShader.start();
		GL30.glBindVertexArray(this.hdrQuad);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.HDRfbo.getTexture());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
		GL30.glBindVertexArray(0);
		this.hdrShader.stop();
		this.galaxyRenderer.renderBackground(camera, b);
	}

	public void RenderSystem(Camera camera, SolarSystem system) {
		this.systemRenderer.renderSolarSystem(camera, system);
	}

	public void RenderPlanet(Camera camera, Planet p) {
		this.planetRenderer.renderPlanet(camera, p);
	}

	public LevelOfDetail getLOD() {
		return LOD;
	}

	public void setLOD(LevelOfDetail lOD) {
		LOD = lOD;
	}

	public ProjectionMatrix getPerspective() {
		return perspective;
	}

	public ProjectionMatrix getOthographic() {
		return othographic;
	}

	public enum LevelOfDetail {
		ULTRA(10), HIGH(5), MEDIUM(3), LOW(1), DEBUG(1);

		int exp;

		LevelOfDetail(int exp) {
			this.exp = exp;
		}

		public int getExposant() {
			return exp;
		}
	}
}
