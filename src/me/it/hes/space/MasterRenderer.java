package me.it.hes.space;

import me.it.hes.core.*;
import me.it.hes.core.menus.*;
import me.it.hes.space.galaxy.*;
import me.it.hes.space.planets.*;
import me.it.hes.space.solarsystems.*;
import me.it.hes.space.universe.*;
import me.it.lib.math.*;
import me.it.lib.menu.*;
import me.it.lib.postprocess.fx.*;

public class MasterRenderer {

	public static LevelOfDetail LOD;

	private GalaxyRenderer galaxyRenderer;
	private SolarSystemRenderer systemRenderer;
	private PlanetRender planetRenderer;
	private MenuRenderer menuRenderer;

	private ProjectionMatrix perspective;
	private ProjectionMatrix othographic;
	
	private BloomFX bloomFX;

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
		this.menuRenderer = new MenuRenderer();

		this.bloomFX = new BloomFX();
	}

	public void RenderUniverse(Camera camera, Universe u, Background b) {
		this.galaxyRenderer.renderGalaxies(camera, u.getGalaxies());
		this.galaxyRenderer.renderBackground(camera, b);
	}

	public void RenderGalaxy(Camera camera, Galaxy g, Background b) {
		this.bloomFX.getHDRFBO().bind();
		this.bloomFX.prepare();
		this.galaxyRenderer.renderBackground(camera, b);
		this.galaxyRenderer.renderGalaxy(camera, g);
		this.bloomFX.getHDRFBO().release();
		this.bloomFX.processBuffer(10*LOD.exp);
		this.bloomFX.renderBlendBuffers();
	}

	public void RenderSystem(Camera camera, SolarSystem system) {
		this.systemRenderer.renderSolarSystem(camera, system);
	}

	public void RenderPlanet(Camera camera, Planet p) {
		this.planetRenderer.renderPlanet(camera, p);
	}
	
	public void renderMenu(MainMenu m) {
		this.menuRenderer.renderMenu(m);
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
