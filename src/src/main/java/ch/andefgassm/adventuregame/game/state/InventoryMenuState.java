package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.ui.Console;

public class InventoryMenuState implements IGameState {

	public void handle(GameStateContext context) {
		Console console = context.getConsole();
		console.clear();
		console.println("Inventory Menu");
		console.println("=========");
		console.println("");
		console.println("Inventory (press button to equip item)");
		console.println("--------------------------------------");
		console.println("1) [x] Axe [20 Stamina, 30 Strength]");
		console.println("2) [ ] Shield [25 Stamina, 20 Strength]");
		console.println("");
		console.getInt("Your choice:", 1, 1);
		context.changeState(GameStateContext.MAIN_MENU);
	}

}
