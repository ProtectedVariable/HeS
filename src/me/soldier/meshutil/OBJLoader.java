package me.soldier.meshutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.soldier.math.Vector2f;
import me.soldier.math.Vector3f;

public class OBJLoader {

	public static Model loadObjModel(String fileName) {
		InputStream is = null;
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Vector2f> textures = new ArrayList<Vector2f>();
		ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		float[] verticesArray, normalsArray = null, textureArray = null;
		int[] indicesArray;

		try {
			is = ClassLoader.getSystemResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line;

			while (true) {
				line = reader.readLine();
				String[] currentline = line.split(" ");
				// vertex
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentline[1]), Float.parseFloat(currentline[2]), Float.parseFloat(currentline[3]));
					vertices.add(vertex);
				}
				// texture
				else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentline[1]), Float.parseFloat(currentline[2]));
					textures.add(texture);
				}
				// normal
				else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentline[1]), Float.parseFloat(currentline[2]), Float.parseFloat(currentline[3]));
					normals.add(normal);
				}
				// index
				else if (line.startsWith("f ")) {
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");

				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
				line = reader.readLine();

			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];

		int vertexPointer = 0;
		for (Vector3f vertex : vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}

		for (int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		return Loader.createModelVAO(verticesArray, normalsArray, textureArray, indicesArray);
	}

	private static void processVertex(String[] vertexData, ArrayList<Integer> indices, ArrayList<Vector2f> textures, ArrayList<Vector3f> normals, float[] texturesArray, float[] normalArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		texturesArray[currentVertexPointer * 2] = currentTex.x;
		texturesArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalArray[currentVertexPointer * 3] = currentNorm.x;
		normalArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}
}
