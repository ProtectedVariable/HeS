package xyz.hes.space;

import me.soldier.meshutil.Loader;
import me.soldier.meshutil.Model;

public class Background {

	private Model model;
	private float[] vertices;
	private ShootingStar[] ssa;
	
	public Background(int nb, int size) {
		vertices = new float[nb];
		setSsa(new ShootingStar[MasterRenderer.LOD.getExposant()*3]);
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = (float) ((Math.random()-0.5)*size);
		}
		this.setModel(Loader.createModelVAO(vertices));
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public ShootingStar[] getSsa() {
		return ssa;
	}

	public void setSsa(ShootingStar[] ssa) {
		this.ssa = ssa;
	}
	
}
