package me.soldier.graphics;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;

public class HDRFrameBuffer {

	private int id;
	
	public HDRFrameBuffer() {
		this.id = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, 800, 600, 0, GL_RGB, GL_FLOAT, 0);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR); 
		
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, tex, 0);  

	}
	
	public void clear() {
		glDeleteFramebuffers(id);
	}
	
}
