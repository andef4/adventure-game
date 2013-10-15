package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.ui.Console;
import ch.andefgassm.adventuregame.game.ui.LanternaConsole;

public class GameStateContext {
	
	public static final IGameState COMBAT_MENU = new CombatMenuState();
	public static final IGameState MAIN_MENU = new MainMenuState();
	public static final IGameState INVENTORY_MENU = new InventoryMenuState();
	
	private IGameState currentState;
	
	public void run() {
		currentState = MAIN_MENU;
		Console console = new LanternaConsole();
		while (true) {
			if (currentState == null) {
				console.close();
				return;
			}
			currentState.handle(this, console);
		}
	}
	
	public void changeState(IGameState newState) {
		currentState = newState;
	}

}
