package me.soldier.graphics;

import static org.lwjgl.opengl.GL11.*;
import me.soldier.math.*;

import org.lwjgl.opengl.*;

public class BitmapFont {

	private Texture texture;
	private Vector3f backcolor;
	private int l, h, L, H, startAscii;
	private static TextShader shader = new TextShader("res/shaders/text/text.vert", "res/shaders/text/text.frag");

	public BitmapFont(Texture texture, Vector3f backcolor, int l, int h, int L, int H, int startAscii) {
		this.texture = texture;
		this.l = l;
		this.h = h;
		this.L = L;
		this.H = H;
		this.startAscii = startAscii;
		this.backcolor = backcolor;
	}

	public Vector2f getAbsolutePos(char letter) {
		int n = ((int) letter) - startAscii;
		int line = n * l / L;
		int x = (n * l) - (L * line);
		int y = line * h;
		return new Vector2f(x + 2, y + 2);
	}

	public Vector2f getTexCoord(Vector2f absolutePos, int s, int t) {
		return new Vector2f((absolutePos.x + (s * l) - s * 2) / (float) L, (absolutePos.y + (t * h) - t * 4) / (float) H);
	}

	public void renderString(float x, float y, String text) {
		glCullFace(GL_FRONT);
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + texture.getId());
		shader.CurrentTexture = texture;
		shader.BackColor = backcolor;
		shader.loadUniforms();
		int xOff = 0;
		for (char c : text.toCharArray()) {
			Vector2f texCoord00 = getTexCoord(getAbsolutePos(c), 0, 0);
			Vector2f texCoord10 = getTexCoord(getAbsolutePos(c), 1, 0);
			Vector2f texCoord11 = getTexCoord(getAbsolutePos(c), 1, 1);
			Vector2f texCoord01 = getTexCoord(getAbsolutePos(c), 0, 1);
			glBegin(GL_QUADS);
			glTexCoord2f(texCoord00.x, texCoord00.y);
			glVertex2f(xOff + x, y);
			glTexCoord2f(texCoord10.x, texCoord10.y);
			glVertex2f(xOff + x + l, y);
			glTexCoord2f(texCoord11.x, texCoord11.y);
			glVertex2f(xOff + x + l, y + h);
			glTexCoord2f(texCoord01.x, texCoord01.y);
			glVertex2f(xOff + x, y + h);
			glEnd();
			xOff += l/2;
		}
		shader.stop();
		glCullFace(GL_BACK);
	}

//	private int CharOffset(char c) {
//		if (Character.isDigit(c)) {
//			return 15;
//		}
//		if (Character.isWhitespace(c)) {
//			return 10;
//		}
//		if (Contains(Small, c)) {
//			return 20;
//		}
//		if (Contains(Medium, c)) {
//			return 25;
//		}
//		return 10;
//	}
//
//	private boolean Contains(char[] haystack, char needle) {
//		for (char c : haystack) {
//			if (Character.toLowerCase(needle) == c) {
//				return true;
//			}
//		}
//		return false;
//	}

}
