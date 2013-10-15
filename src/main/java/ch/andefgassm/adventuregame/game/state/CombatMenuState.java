package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.ui.Console;

public class CombatMenuState implements IGameState {

	public void handle(GameStateContext context, Console console) {
		console.clear();
		console.println("Combat Menu");
		console.println("=========");
		console.println("");
		console.println("1) Black Dragon");
		console.println("");
		console.getInt("Your choice:", 1, 1);
		context.changeState(GameStateContext.MAIN_MENU);
	}

}
