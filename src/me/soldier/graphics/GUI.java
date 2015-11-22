package me.soldier.graphics;

import org.lwjgl.opengl.*;

import xyz.hes.core.Main;
import me.soldier.math.*;
import static org.lwjgl.opengl.GL11.*;

public class GUI {

	private Texture texture;
	private Vector2f position;
	private Vector2f size;
	private static Shader shader;
	public static ProjectionMatrix pr_matrix;
	
	static {
		pr_matrix = new ProjectionMatrix(0, Main.width, Main.height, 0, -1, 1);
		shader = new Shader("res/shaders/gui/gui.vert", "res/shaders/gui/gui.frag") {
			
			@Override
			public void loadUniforms() {
				this.setUniform(this.getUniformLocation("pr_matrix"), pr_matrix);
			}
			
			@Override
			protected void bindAttributes() {
				
			}
		};
	}

	public GUI(Texture texture, Vector2f position, Vector2f size) {
		this.texture = texture;
		this.position = position;
		this.size = size;
	}

	public void render() {
		glDisable(GL_CULL_FACE);
		GL13.glActiveTexture(GL13.GL_TEXTURE0+texture.getId());
		texture.bind();
		shader.start();
		shader.setUniform(shader.getUniformLocation("textr"), texture.getId());
		shader.loadUniforms();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(position.x, position.y);
		glTexCoord2f(1, 0);
		glVertex2f(position.x + size.x, position.y);
		glTexCoord2f(1, 1);
		glVertex2f(position.x + size.x, position.y + size.y);
		glTexCoord2f(0, 1);
		glVertex2f(position.x, position.y + size.y);
		glEnd();
		shader.stop();
		glEnable(GL_CULL_FACE);
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
