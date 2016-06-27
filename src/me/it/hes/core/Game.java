package me.it.hes.core;

import static org.lwjgl.glfw.GLFW.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import libs.thinmatrix.fontMeshCreator.FontType;
import libs.thinmatrix.fontRendering.TextMaster;
import me.it.hes.core.menus.*;
import me.it.hes.players.*;
import me.it.hes.space.*;
import me.it.hes.space.MasterRenderer.*;
import me.it.hes.space.galaxy.*;
import me.it.hes.space.planets.*;
import me.it.hes.space.solarsystems.*;
import me.it.hes.space.universe.*;
import me.it.lib.graphics.*;
import me.it.lib.util.*;

public class Game {

	private Universe universe;
	private MasterRenderer renderer;
	private Object observed;
	private Camera pov;
	//TODO load from option file
	public static String LANG_PATH = "src/res/lang/en/";
	private List<Player> enemies;
	private MousePicker mousePicker;
	//Space backgrounds
	private Background universeBack;
	private Background galaxyBack;
	//Text
	public static FontType font;
	//Menus
	private MainMenu mainMenu;

	public Game() {
		
		TextMaster.init();
		font = new FontType(new Texture("res/fonts/ethno.png", 0).getId(), new File("src/res/fonts/ethno.fnt"));
		
		this.renderer = new MasterRenderer(LevelOfDetail.DEBUG);
		this.universe = new Universe(10);
		this.observed = this.universe;
		this.pov = new Camera(0, 0, 330);

		this.enemies = new ArrayList<Player>();
		
		this.universeBack = new Background(200, 1000);
		this.galaxyBack = new Background(100, 100);

		List<String> f = FileReader.readFile(LANG_PATH + "E_N1.txt");
		List<String> s = FileReader.readFile(LANG_PATH + "E_N2.txt");
		List<String> t = FileReader.readFile(LANG_PATH + "E_N3.txt");
		List<String> l = FileReader.readFile(LANG_PATH + "E_N4.txt");

		for (int i = 0; i < 10; i++) {
			String name = Player.generateName(f, s, t, l);
			System.out.println(name);
			this.enemies.add(new Player(new ArrayList<Galaxy>(), new ArrayList<Planet>(), name));
		}
		mousePicker = new MousePicker(pov.vw_matrix, this.renderer.getPerspective());
		mainMenu = new MainMenu();
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
		//renderer.renderMenu(mainMenu);
		TextMaster.render();
	}

	public void Update() {
		mousePicker.Update(Main.mouseX, Main.mouseY);
		mainMenu.Update();
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
		
		if (330-ScrollHandler.getY() > -20 && 30-ScrollHandler.getY() < 330) {
			pov.position.z = 330-ScrollHandler.getY();
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
