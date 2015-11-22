package xyz.hes.core;

import java.util.ArrayList;
import java.util.List;

import me.soldier.util.FileReader;
import xyz.hes.enemy.Enemy;
import xyz.hes.space.MasterRenderer;
import xyz.hes.space.MasterRenderer.LevelOfDetail;
import xyz.hes.space.galaxy.Galaxy;
import xyz.hes.space.objects.Planet;
import xyz.hes.space.objects.SolarSystem;
import xyz.hes.space.universe.Universe;

public class Game {

	private Universe universe;
	private MasterRenderer renderer;
	private Object observed;
	private Camera pov;
	public static String LANG_PATH = "src/res/lang/en/";
	private List<Enemy> enemies;

	public Game() {
		this.renderer = new MasterRenderer();
		this.renderer.setLOD(LevelOfDetail.DEBUG);
		this.universe = new Universe(10);
		this.observed = this.universe;
		this.pov = new Camera(0, 0, 10);

		this.enemies = new ArrayList<Enemy>();

		List<String> f = FileReader.readFile(LANG_PATH + "E_N1.txt");
		List<String> s = FileReader.readFile(LANG_PATH + "E_N2.txt");
		List<String> t = FileReader.readFile(LANG_PATH + "E_N3.txt");
		List<String> l = FileReader.readFile(LANG_PATH + "E_N4.txt");

		for (int i = 0; i <= 10; i++) {
			String name = Enemy.generateName(f, s, t, l);
			System.out.println(name);
			this.enemies.add(new Enemy(null, null, name));
		}
	}

	public void Render() {
		
		pov.lookThrough();
		
		if (observed instanceof Universe) {
			renderer.RenderUniverse(pov, (Universe) observed);
		} else if (observed instanceof Galaxy) {
			renderer.RenderGalaxy(pov, (Galaxy) observed);
		} else if (observed instanceof SolarSystem) {
			renderer.RenderSystem(pov, (SolarSystem) observed);
		} else if (observed instanceof Planet) {
			renderer.RenderPlanet(pov, (Planet) observed);
		} else {
			throw new IllegalArgumentException("Observed object isn't a renderable object");
		}
	}

	public void Update() {
		pov.position.z += ScrollHandler.getdY();
	}
}
