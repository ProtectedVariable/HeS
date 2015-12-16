package xyz.hes.space.solarsystems;

import me.soldier.graphics.Shader;
import me.soldier.math.ModelMatrix;
import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import me.soldier.math.ViewMatrix;

public class SolarSystemShader extends Shader {

	private final static String VERT_FILE = "src/xyz/hes/space/solarsystems/ss.vert";
	private final static String FRAG_FILE = "src/xyz/hes/space/solarsystems/ss.frag";
	
	private int mlMatLoc, vwMatLoc, prMatLoc, lightLoc, sunLoc;
	private ModelMatrix mlMat;
	private ViewMatrix vwMat;
	private ProjectionMatrix prMat;
	private Vector3f lightPosition;
	private int sun;
	
	public SolarSystemShader() {
		super(VERT_FILE, FRAG_FILE);
		this.setMlMat(new ModelMatrix());
		this.vwMatLoc = this.getUniformLocation("vw_mat");
		this.mlMatLoc = this.getUniformLocation("ml_mat");
		this.prMatLoc = this.getUniformLocation("pr_mat");
		this.lightLoc = this.getUniformLocation("lightPos");
		this.sunLoc = this.getUniformLocation("sun");
	}
	
	public void loadOnceUniforms() {
		this.setUniform(vwMatLoc, vwMat);
		this.setUniform(prMatLoc, prMat);
		this.setUniform(lightLoc, lightPosition);
	}
	
	@Override
	public void loadUniforms() {
		this.setUniform(mlMatLoc, mlMat);
		this.setUniform(sunLoc, sun);
	}

	public ModelMatrix getMlMat() {
		return mlMat;
	}

	public void setMlMat(ModelMatrix mlMat) {
		this.mlMat = mlMat;
	}

	public ViewMatrix getVwMat() {
		return vwMat;
	}

	public void setVwMat(ViewMatrix vwMat) {
		this.vwMat = vwMat;
	}

	public ProjectionMatrix getPrMat() {
		return prMat;
	}

	public void setPrMat(ProjectionMatrix prMat) {
		this.prMat = prMat;
	}

	public Vector3f getLightPosition() {
		return lightPosition;
	}

	public void setLightPosition(Vector3f lightPosition) {
		this.lightPosition = lightPosition;
	}

	public int getSun() {
		return sun;
	}

	public void setSun(int sun) {
		this.sun = sun;
	}


	
}
