package ch.andefgassm.adventuregame.game.state;


public class CombatMenuState extends AbstractConsoleGameState {

	private GameStateContext context;

	public void init(GameStateContext context) {
		this.context = context;
		clear();
		println("Combat Menu");
		println("=========");
		println("");
		println("1) Black Dragon");
		println("");
		println("2) Back to main menu");
		println("");
		println("Your choice:");
	}

	@Override
	public void handleInput(int input) {
		if (input == 1) {
			context.changeState(GameStateContext.COMBAT);
		} else {
			context.changeState(GameStateContext.MAIN_MENU);
		}
	}

}
