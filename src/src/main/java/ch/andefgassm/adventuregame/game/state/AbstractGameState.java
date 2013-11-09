package ch.andefgassm.adventuregame.game.state;

import com.badlogic.gdx.ScreenAdapter;


public abstract class AbstractGameState extends ScreenAdapter {
	
	public abstract void init(GameStateContext context);

}
