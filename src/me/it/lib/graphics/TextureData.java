package me.it.lib.graphics;

import java.nio.ByteBuffer;

public class TextureData {

	private int width, height;
	private ByteBuffer buffer;
	
	public TextureData(ByteBuffer buffer,int width, int height) {
		this.width = width;
		this.height = height;
		this.buffer = buffer;
	}

	public TextureData(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

}
