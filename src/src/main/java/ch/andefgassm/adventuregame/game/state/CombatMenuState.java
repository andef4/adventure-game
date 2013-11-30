package ch.andefgassm.adventuregame.game.state;

import java.util.ArrayList;
import java.util.List;

import ch.andefgassm.adventuregame.game.Enemy;


public class CombatMenuState extends AbstractConsoleGameState {

	private GameStateContext context;
	private List<Enemy> enemies;
	
	public void init(GameStateContext context, String param) {
		this.context = context;
		enemies = new ArrayList<Enemy>(context.getEnemies().values());
		clear();
		println("Combat Menu");
		println("=========");
		println("");
		for (int i = 0; i < enemies.size(); i++) {
			println(String.format("%d) %s", i+1, enemies.get(i).getName()));
		}
		println("");
		println("0) Back to main menu");
		println("");
		println("Your choice:");
	}

	@Override
	public void handleInput(int input) {
		if (input == 0) {
			context.changeState(GameStateContext.MAIN_MENU);
		} else if (input > 0 && input <= enemies.size()) {
			context.changeState(GameStateContext.COMBAT, enemies.get(input - 1).getId());
		}
	}
}
