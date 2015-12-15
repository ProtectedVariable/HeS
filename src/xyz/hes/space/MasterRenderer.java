package xyz.hes.space;

import me.soldier.math.ProjectionMatrix;
import xyz.hes.core.Camera;
import xyz.hes.core.Main;
import xyz.hes.space.galaxy.Galaxy;
import xyz.hes.space.galaxy.GalaxyRenderer;
import xyz.hes.space.planets.Planet;
import xyz.hes.space.planets.PlanetRender;
import xyz.hes.space.solarsystems.SolarSystem;
import xyz.hes.space.solarsystems.SolarSystemRenderer;
import xyz.hes.space.universe.Universe;

public class MasterRenderer {
	
	public static LevelOfDetail LOD;

	private GalaxyRenderer galaxyRenderer;
	private SolarSystemRenderer systemRenderer;
	private PlanetRender planetRenderer;
	private ProjectionMatrix perspective;
	private ProjectionMatrix othographic;
	
	private float fov = 50;
	private float aspect = -1f;
	private float near = 0.1f;
	private float far = 1000f;
	
	public MasterRenderer(LevelOfDetail lod) {
		LOD = lod;
		this.aspect = (float)Main.width/(float)Main.height;
		this.perspective = new ProjectionMatrix(fov, aspect, near, far);
		
		this.galaxyRenderer = new GalaxyRenderer(this.perspective);
		this.systemRenderer = new SolarSystemRenderer(this.perspective);
		this.planetRenderer = new PlanetRender(this.perspective);
	}
	
	public void RenderUniverse(Camera camera, Universe u, Background b) {
		this.galaxyRenderer.renderGalaxies(camera, u.getGalaxies());
		this.galaxyRenderer.renderBackground(camera, b);
	}
	
	public void RenderGalaxy(Camera camera, Galaxy g, Background b) {
		this.galaxyRenderer.renderGalaxy(camera, g);
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
