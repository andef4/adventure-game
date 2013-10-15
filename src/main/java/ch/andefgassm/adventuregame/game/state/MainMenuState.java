package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.ui.Console;

public class MainMenuState implements IGameState {

	public void handle(GameStateContext context, Console console) {
		console.clear();
		console.println("Main Menu");
		console.println("=========");
		console.println("");
		console.println("1) Combat");
		console.println("2) Inventory");
		console.println("3) Exit");
		console.println("");
		int choice = console.getInt("Your choice:", 1, 3);
		if (choice == 1) {
			context.changeState(GameStateContext.COMBAT_MENU);
		} else if (choice == 2) {
			context.changeState(GameStateContext.INVENTORY_MENU);
		} else {
			context.changeState(null);
		}
	}
}
