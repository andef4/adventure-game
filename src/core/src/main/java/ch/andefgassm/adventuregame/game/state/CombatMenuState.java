package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.ui.Console;

public class CombatMenuState implements IGameState {

	public void handle(GameStateContext context) {
		Console console = context.getConsole();
		console.clear();
		console.println("Combat Menu");
		console.println("=========");
		console.println("");
		console.println("1) Black Dragon");
		console.println("");
		console.println("2) Back to main menu");
		console.println("");
		int choice = console.getInt("Your choice:", 1, 2);
		if (choice == 1) {
			context.changeState(GameStateContext.COMBAT);
		} else {
			context.changeState(GameStateContext.MAIN_MENU);
		}
	}

}
