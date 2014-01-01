package ch.andefgassm.adventuregame.game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class TextureCache {

	private static Map<String, Texture> cache = new HashMap<String, Texture>();

	public static Texture getTexture(String path) {
		Texture texture = cache.get(path);
		if (texture == null) {
			FileHandle fileHandle = Gdx.files.internal("img/" + path);
			texture = new Texture(fileHandle);
		}
		return texture;
	}
}
