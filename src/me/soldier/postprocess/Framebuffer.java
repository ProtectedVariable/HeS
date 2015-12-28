package me.soldier.postprocess;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.opengl.GL12;

public class Framebuffer {

	private int id;
	private int tex;

	public Framebuffer(int w, int h, boolean renderbuffer) {
		this.id = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, w, h, 0, GL_RGB, GL_FLOAT, 0);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tex, 0);
		if (renderbuffer) {
			int rbo = glGenRenderbuffers();
			glBindRenderbuffer(GL_RENDERBUFFER, rbo);
			glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, w, h);
			glBindRenderbuffer(GL_RENDERBUFFER, 0);
			glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo);
		}
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Error: Framebuffer isn't complete");
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void bind() {
		glBindFramebuffer(GL_FRAMEBUFFER, id);
	}

	public void release() {
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	public void clear() {
		glDeleteFramebuffers(id);
	}

	public int getTexture() {
		return this.tex;
	}
	
	public void setTexture(int t) {
		this.tex = t;
	}
}
