package me.soldier.postprocess.fx;

import me.soldier.meshutil.*;
import me.soldier.postprocess.*;

import org.lwjgl.opengl.*;

import xyz.hes.core.*;

public class BloomFX {

	private Framebuffer pingpongbuf1, pingpongbuf2;
	private HDRFramebuffer HDRFBO;
	private BlurShader blurShader;
	private BlendShader blendShader;
	private int screenQuad;
	
	public BloomFX() {
		this.pingpongbuf1 = new Framebuffer(Main.width, Main.height, false);
		this.pingpongbuf2 = new Framebuffer(Main.width, Main.height, false);
		this.HDRFBO = new HDRFramebuffer(Main.width, Main.height);
		this.blurShader = new BlurShader();
		this.blendShader = new BlendShader();
		float[] pos = { -1, 1, -1, -1, 1, -1, -1, 1, 1, -1, 1, 1 };
		float[] tex = { 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1 };
		this.screenQuad = Loader.createGUIVAO(pos, tex);
	}
	
	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDrawBuffers(2, this.getHDRFBO().getAttachments());		
	}
	
	public void renderBlendBuffers() {
		this.blendShader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.HDRFBO.getTexture().get(0));
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.pingpongbuf1.getTexture());
		this.blendShader.loadUniforms();
		renderBuffer();
		this.blendShader.stop();
	}
	
	public void renderBuffer() {
		GL30.glBindVertexArray(this.screenQuad);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(2);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void processBuffer(int amount) {
		boolean horizontal = true, first = true;
		this.blurShader.start();
		Framebuffer fboH,fboNH = null;
		for(int i = 0; i < amount;i++) {
			fboH = horizontal?this.pingpongbuf1:this.pingpongbuf2;
			fboNH = horizontal?this.pingpongbuf2:this.pingpongbuf1;
			fboH.bind();
			this.blurShader.setUniform(blurShader.getUniformLocation("horizontal"), horizontal);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, first?this.HDRFBO.getTexture().get(4):fboNH.getTexture());
			renderBuffer();
			horizontal = !horizontal;
			if(first)
				first = false;
		}
		this.HDRFBO.release();
		this.blurShader.stop();
	}

	public Framebuffer getPingpongbuf1() {
		return pingpongbuf1;
	}

	public void setPingpongbuf1(Framebuffer pingpongbuf1) {
		this.pingpongbuf1 = pingpongbuf1;
	}

	public Framebuffer getPingpongbuf2() {
		return pingpongbuf2;
	}

	public void setPingpongbuf2(Framebuffer pingpongbuf2) {
		this.pingpongbuf2 = pingpongbuf2;
	}

	public HDRFramebuffer getHDRFBO() {
		return HDRFBO;
	}

	public void setHDRFBO(HDRFramebuffer hDRFBO) {
		HDRFBO = hDRFBO;
	}	
}
