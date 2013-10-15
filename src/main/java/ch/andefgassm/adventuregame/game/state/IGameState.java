package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.ui.Console;

public interface IGameState {
	
	public void handle(GameStateContext context, Console console);

}
