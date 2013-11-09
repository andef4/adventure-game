package ch.andefgassm.adventuregame.game.state;


public class InventoryMenuState extends AbstractConsoleGameState {

	private GameStateContext context;

	public void init(GameStateContext context) {
		this.context = context;
		clear();
		println("Inventory Menu");
		println("=========");
		println("");
		println("Inventory (press button to equip item)");
		println("--------------------------------------");
		println("1) [x] Axe [20 Stamina, 30 Strength]");
		println("2) [ ] Shield [25 Stamina, 20 Strength]");
		println("");
		println("Your choice:");
	}

	@Override
	public void handleInput(int input) {
		context.changeState(GameStateContext.MAIN_MENU);		
	}

}
