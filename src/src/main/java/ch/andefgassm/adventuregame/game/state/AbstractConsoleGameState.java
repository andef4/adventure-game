package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.assets.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractConsoleGameState extends AbstractGameState {

	private SpriteBatch batch = new SpriteBatch();
	private StringBuffer buffer = new StringBuffer();
	private BitmapFont font = Graphics.getFont();
	private int displayHeight = Gdx.graphics.getHeight();

	private static int LINE_HEIGHT = 20;

	public void clear() {
		buffer.setLength(0);
	}

	public void print(String str) {
		buffer.append(str);
	}

	public void println(String str) {
		print(str + "\n");
	}

	public abstract void handleInput(int input);

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		int currentHeight = 0;
		for(String line : buffer.toString().split("\n")) {
			font.draw(batch, line, 0, displayHeight - currentHeight);
			currentHeight += LINE_HEIGHT;
		}
		font.setColor(1, 1, 1, 1);
		font.draw(batch, buffer, 1, 1);

		batch.end();
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9) {
			handleInput(keycode - Input.Keys.NUM_0);
		}
		return true;
	}
}
