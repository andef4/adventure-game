package ch.andefgassm.adventuregame.game.state;


public class MainMenuState extends AbstractConsoleGameState {

	private GameStateContext context;

	public void init(GameStateContext context, String param) {
		this.context = context;
		clear();
		println("Main Menu");
		println("=========");
		println("");
		println("1) Combat");
		println("2) Inventory");
		println("3) Exit");
		println("");
		println("Your choice:");
		
	}

	@Override
	public void handleInput(int input) {
		if (input == 1) {
			context.changeState(GameStateContext.COMBAT_MENU);
		} else if (input == 2) {
			context.changeState(GameStateContext.INVENTORY_MENU);
		} else {
			context.changeState(null);
		}		
	}
}
