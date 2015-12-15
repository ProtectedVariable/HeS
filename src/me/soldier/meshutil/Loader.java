package me.soldier.meshutil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.*;

import me.soldier.graphics.*;

public class Loader {

	public static Model createModelVAO(float[] pos, float[] normals, float[] tex, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		storeDataInAttributeList(1, 3, normals);
		storeDataInAttributeList(3, 2, tex);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}
	
	public static Model createModelVAO(float[] pos) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		unbindVAO();
		return new Model(vaoID, pos.length);
	}
	
	public static Model createTexturedModelVAO(float[] pos, float[] textures) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		storeDataInAttributeList(3, 2, textures);
		unbindVAO();
		return new Model(vaoID, pos.length);
	}
	
	public static Model createModelVAO(float[] pos, float[] normals, int[] indices) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		storeDataInAttributeList(1, 3, normals);
		bindIndicesBuffer(indices);
		unbindVAO();
		return new Model(vaoID, indices.length);
	}


	public static int createGUIVAO(float[] pos) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		storeDataInAttributeList(3, 2, new float[] { 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0 });
		unbindVAO();
		return vaoID;
	}
	
	public static int createTextVAO(float[] pos, float[] texCoords) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, pos);
		storeDataInAttributeList(3, 2, texCoords);
		unbindVAO();
		return vaoID;
	}

	private static List<Integer> vaos = new ArrayList<Integer>();
	private static List<Integer> vbos = new ArrayList<Integer>();
	private static List<Integer> textures = new ArrayList<Integer>();

	private static int createVAO() {
		int vaoID = glGenVertexArrays();
		vaos.add(vaoID);
		glBindVertexArray(vaoID);
		return vaoID;
	}

	public static Model loadToVAO(float[] positions, int dimensions) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, dimensions, positions);
		unbindVAO();
		return new Model(vaoID, positions.length / dimensions);
	}

	private static void storeDataInAttributeList(int attributeNumber, int size, float[] data) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, VBOUtil.createFloatBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private static void bindIndicesBuffer(int[] indices) {
		int vboID = glGenBuffers();
		vbos.add(vboID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, VBOUtil.createIntBuffer(indices), GL_STATIC_DRAW);
	}
	
//	/**
//	 * 
//	 * @param textureFiles Order = R_L_T_B_BA_F
//	 * @return
//	 */
//	public static int loadCubeMap(String[] textureFiles) {
//		int texID = glGenTextures();
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		
//		glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texID);
//		for(int i = 0; i < textureFiles.length;i++) {
//			TextureData data = decodeTextureFile(textureFiles[i]+".png");
//			glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, data.getWidth(), data.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getBuffer());
//		}
//		glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//		glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
//		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
//		textures.add(texID);
//		glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, 0);
//		return texID;
//	}

//	private static TextureData decodeTextureFile(String fileName) {
//		int width = 0;
//		int height = 0;
//		ByteBuffer buffer = null;
//		try {
//			FileInputStream in = new FileInputStream(fileName);
//			PNGDecoder decoder = new PNGDecoder(in);
//			width = decoder.getWidth();
//			height = decoder.getHeight();
//			buffer = ByteBuffer.allocateDirect(4 * width * height);
//			decoder.decode(buffer, width * 4, Format.RGBA);
//			buffer.flip();
//			in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("Tried to load texture " + fileName + ", didn't work");
//			System.exit(-1);
//		}
//		return new TextureData(buffer, width, height);
//	}
	
	private static void unbindVAO() {
		glBindVertexArray(0);
	}

	public static void cleanUp() {
		for (int i : vaos)
			glDeleteVertexArrays(i);
		for (int i : vbos)
			glDeleteBuffers(i);
		for(int i : textures)
			glDeleteTextures(i);
	}

}
