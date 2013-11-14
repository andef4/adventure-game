package ch.andefgassm.adventuregame.game;

import java.awt.Dimension;
import java.awt.Toolkit;

import ch.andefgassm.adventuregame.game.state.GameStateContext;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main extends Game {
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = screenSize.width;
		config.height = screenSize.height;
		config.useGL20 = true;
		config.fullscreen = false;
		new LwjglApplication(new Main(), config);
	}

	@Override
	public void create() {
		new GameStateContext(this).run();
	}

}
