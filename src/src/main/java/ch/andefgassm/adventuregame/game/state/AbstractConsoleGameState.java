package ch.andefgassm.adventuregame.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractConsoleGameState extends AbstractGameState {

	Texture texture = new Texture(Gdx.files.internal("libgdx-logo.png"));
	SpriteBatch batch = new SpriteBatch();
	
	private StringBuffer buffer = new StringBuffer();
	
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
		batch.draw(texture, 100, 100);
		batch.end();
	}

}
