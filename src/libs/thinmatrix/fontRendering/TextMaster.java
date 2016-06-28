package libs.thinmatrix.fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libs.thinmatrix.fontMeshCreator.FontType;
import libs.thinmatrix.fontMeshCreator.GUIText;
import libs.thinmatrix.fontMeshCreator.TextMeshData;
import me.it.lib.math.ProjectionMatrix;
import me.it.lib.math.ViewMatrix;
import me.it.lib.meshutil.*;

public class TextMaster {

    private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
    private static FontRenderer renderer;

    public static void init(ProjectionMatrix pr) {
	renderer = new FontRenderer(pr);
    }

    public static void loadText(GUIText text) {
	FontType font = text.getFont();
	TextMeshData data = font.loadText(text);
	int vao = Loader.createTextVAO(data.getVertexPositions(), data.getTextureCoords());
	text.setMeshInfo(vao, data.getVertexCount());
	List<GUIText> textBatch = texts.get(font);
	if (textBatch == null) {
	    textBatch = new ArrayList<GUIText>();
	    texts.put(font, textBatch);
	}
	textBatch.add(text);
    }

    public static void render(ViewMatrix vw) {
	renderer.render(texts, vw);
    }

    public static void removeText(GUIText text) {
	List<GUIText> textBatch = texts.get(text.getFont());
	textBatch.remove(text);
	if (textBatch.isEmpty()) {
	    texts.remove(text.getFont());
	}
    }

    public static void cleanUp() {
	renderer.cleanUp();
    }
}
