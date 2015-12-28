package me.soldier.postprocess.fx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

public class HDRFramebuffer {

	private int id;
	private ByteBuffer tex;
	private ByteBuffer attachments;
	
	public HDRFramebuffer(int w, int h) {
		this.id = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, id);
		tex = BufferUtils.createByteBuffer(8);
		glGenTextures(2, tex);
		for(int i = 0; i < 2;i++) {
			glBindTexture(GL_TEXTURE_2D, tex.get(i*4));
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB16F, w, h, 0, GL_RGB, GL_FLOAT, 0);
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR); 
		    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		    
			glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0+i, GL_TEXTURE_2D,  tex.get(i*4), 0);  
		}
		
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Error: Bloom Framebuffer isn't complete");
		}
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		attachments = BufferUtils.createByteBuffer(8);
		attachments.putInt(0, GL30.GL_COLOR_ATTACHMENT0);
		attachments.putInt(4, GL30.GL_COLOR_ATTACHMENT1);
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
	
	public ByteBuffer getTexture() {
		return this.tex;
	}

	public ByteBuffer getAttachments() {
		return attachments;
	}
}
