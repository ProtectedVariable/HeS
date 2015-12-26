package xyz.hes.core;

import static org.lwjgl.glfw.GLFW.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import fontMeshCreator.*;
import fontRendering.*;
import me.soldier.graphics.*;
import me.soldier.math.*;
import me.soldier.util.FileReader;
import me.soldier.util.MousePicker;
import xyz.hes.players.Player;
import xyz.hes.space.Background;
import xyz.hes.space.MasterRenderer;
import xyz.hes.space.MasterRenderer.LevelOfDetail;
import xyz.hes.space.galaxy.Galaxy;
import xyz.hes.space.planets.Planet;
import xyz.hes.space.solarsystems.SolarSystem;
import xyz.hes.space.universe.Universe;

public class Game {

	private Universe universe;
	private MasterRenderer renderer;
	private Object observed;
	private Camera pov;
	public static String LANG_PATH = "src/res/lang/en/";
	private List<Player> enemies;
	private MousePicker mousePicker;
	//Space backgrounds
	private Background universeBack;
	private Background galaxyBack;
	//Text
	FontType font;
	GUIText text;

	public Game() {
		this.renderer = new MasterRenderer(LevelOfDetail.DEBUG);
		this.universe = new Universe(10);
		this.observed = this.universe;
		this.pov = new Camera(0, 0, 330);

		this.enemies = new ArrayList<Player>();
		
		this.universeBack = new Background(500, 500);
		this.galaxyBack = new Background(300, 100);

		List<String> f = FileReader.readFile(LANG_PATH + "E_N1.txt");
		List<String> s = FileReader.readFile(LANG_PATH + "E_N2.txt");
		List<String> t = FileReader.readFile(LANG_PATH + "E_N3.txt");
		List<String> l = FileReader.readFile(LANG_PATH + "E_N4.txt");

		for (int i = 0; i < 10; i++) {
			String name = Player.generateName(f, s, t, l);
			System.out.println(name);
			this.enemies.add(new Player(new ArrayList<Galaxy>(), new ArrayList<Planet>(), name));
		}
		TextMaster.init();
//		font = new FontType(new Texture("res/fonts/ethno.png").getId(), new File("src/res/fonts/ethno.fnt"));
//		text = new GUIText("this is a test text!", 5, font, new Vector2f(0, 0), 1, true);
//		text.setColour(1, 1, 1);
		mousePicker = new MousePicker(pov.vw_matrix, this.renderer.getPerspective());
	}

	public void Render() {

		pov.lookThrough();

		if (observed instanceof Universe) {
			renderer.RenderUniverse(pov, (Universe) observed, universeBack);
		} else if (observed instanceof Galaxy) {
			renderer.RenderGalaxy(pov, (Galaxy) observed, galaxyBack);
		} else if (observed instanceof SolarSystem) {
			renderer.RenderSystem(pov, (SolarSystem) observed);
		} else if (observed instanceof Planet) {
			renderer.RenderPlanet(pov, (Planet) observed);
		} else {
			throw new IllegalStateException("Observed object isn't a renderable object");
		}
		TextMaster.render();
	}

	public void Update() {
		mousePicker.Update(Main.mouseX, Main.mouseY);
		if (observed instanceof Universe) {
			UpdateUniverse();
		} else if (observed instanceof Galaxy) {
			galaxyBack.Update();
			for(SolarSystem ss : ((Galaxy)observed).getSystems()) {
				ss.Update();
			}
			if(Input.isKeyPressed(GLFW_KEY_ESCAPE)) {
				observed = this.universe;
			}
		}
	}
	
	private void UpdateUniverse() {
		universeBack.Update();
		if ((pov.position.z > -20 && ScrollHandler.getdY() < 0) || (pov.position.z < 330 && ScrollHandler.getdY() > 0)) {
			pov.position.z += ScrollHandler.getdY();
		}
		
		for (int i = 0; i < universe.getGalaxies().length; i++) {
			if (mousePicker.collideWithObj(universe.getGalaxies()[i].getPosition(), pov.position, 10)) {
				universe.getGalaxies()[i].MouseHover = true;
				if(MouseHandler.isButtonDown(0)) {
					observed = universe.getGalaxies()[i];
				}
			}
			else
				universe.getGalaxies()[i].MouseHover = false;
		}
		
		if (Input.isKeyDown(GLFW_KEY_LEFT)) {
			pov.position.x -= 1.2f;
		}
		if (Input.isKeyDown(GLFW_KEY_RIGHT)) {
			pov.position.x += 1.2f;
		}
		if (Input.isKeyDown(GLFW_KEY_UP)) {
			pov.position.y += 1.2f;
		}
		if (Input.isKeyDown(GLFW_KEY_DOWN)) {
			pov.position.y -= 1.2f;
		}
	}
	
	public void onGameClosing() {
		TextMaster.cleanUp();
	}
}
