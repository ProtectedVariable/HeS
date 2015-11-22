package xyz.hes.enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xyz.hes.space.galaxy.Galaxy;
import xyz.hes.space.objects.Planet;

public class Enemy {

	private static Random rand = new Random();
	private static ArrayList<String> usedNames = new ArrayList<String>();
	
	ArrayList<Galaxy> galaxies;
	ArrayList<Planet> planets;
	String name;
	
	public Enemy(ArrayList<Galaxy> galaxies, ArrayList<Planet> planets, String name) {
		this.galaxies = galaxies;
		this.planets = planets;
		this.name = name;
	}

	public static String generateName(List<String> f, List<String> s, List<String> t, List<String> l) {
		String cname;
		String name;
		do {
			name = t.get(rand.nextInt(t.size()));
			cname = f.get(rand.nextInt(f.size()))+" "+s.get(rand.nextInt(s.size()))+" of "+name+" "+l.get(rand.nextInt(l.size()));
		} while (usedNames.contains(name));
		usedNames.add(name);
		return cname;
	}
}
