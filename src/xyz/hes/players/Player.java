package xyz.hes.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import xyz.hes.space.galaxy.Galaxy;
import xyz.hes.space.objects.Planet;

public class Player {

	private static Random rand = new Random();
	private static ArrayList<String> usedNames = new ArrayList<String>();
	
	private String name;
	
	public Player(ArrayList<Galaxy> galaxies, ArrayList<Planet> planets, String name) {
		for(Galaxy g : galaxies) {
			g.setOwner(this);
		}
		for(Planet p : planets) {
			p.setOwner(this);
		}
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
