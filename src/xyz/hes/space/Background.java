package xyz.hes.space;

import me.soldier.meshutil.Loader;
import me.soldier.meshutil.Model;

public class Background {

	private Model model;
	private float[] vertices;
	private ShootingStar[] ssa;

	public Background(int nb, int size) {
		vertices = new float[nb*3];
		setSsa(new ShootingStar[MasterRenderer.LOD.getExposant() * 3]);
		for (int i = 0; i < vertices.length; i+=3) {
			vertices[i] = (float) ((Math.random() - 0.5) * size);
			vertices[i+1] = (float) ((Math.random() - 0.5) * size);
			vertices[i+2] = (float) (i);
		}
		this.setModel(Loader.createModelVAO(vertices));
	}

	public void Update() {
		boolean add = Math.random() > 0.985;
		for (int i = 0; i < ssa.length; i++) {
			ShootingStar star = ssa[i];
			if (star == null) {
				if (add) {
					ssa[i] = new ShootingStar(-((float)Math.random()*100f), ((float)Math.random()*100f));
					add = false;
				}
			} else {
				if(!star.update()) ssa[i] = null;
			}
		}

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
