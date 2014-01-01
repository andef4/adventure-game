package ch.andefgassm.adventuregame.game.assets;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Graphics {

	private static Map<String, Texture> cache = new HashMap<String, Texture>();

	public static Texture getTexture(String path) {
		Texture texture = cache.get(path);
		if (texture == null) {
			FileHandle fileHandle = Gdx.files.internal("img/" + path);
			texture = new Texture(fileHandle);
		}
		return texture;
	}

	private static BitmapFont font = null;
	private static BitmapFont boldFont = null;

	public static BitmapFont getFont() {
		if (font == null) {
			font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
		}
		return font;
	}

	public static BitmapFont getBoldFont() {
		if (boldFont == null) {
			boldFont = new BitmapFont(Gdx.files.internal("font/font_bold.fnt"));
		}
		return boldFont;
	}
}
