package ch.andefgassm.adventuregame.game;

import ch.andefgassm.adventuregame.game.state.GameStateContext;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main extends Game {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		new LwjglApplication(new Main(), config);
	}

	@Override
	public void create() {
		new GameStateContext(this);
	}

}
