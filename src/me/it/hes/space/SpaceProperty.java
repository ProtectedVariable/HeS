package me.it.hes.space;

import libs.thinmatrix.fontMeshCreator.GUIText;
import me.it.hes.players.*;

public class SpaceProperty {
	/** name of the object */
	private String name;
	/** owner of the property */
	private Player owner;
	
	private long population;
	
	private GUIText displayText;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public GUIText getDisplayText() {
	    return displayText;
	}

	public void setDisplayText(GUIText displayText) {
	    this.displayText = displayText;
	}
}
