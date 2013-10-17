package ch.andefgassm.adventuregame.game;

import ch.andefgassm.adventuregame.game.state.GameStateContext;

public class Main {

	public static void main(String[] args) {
		GameStateContext context = new GameStateContext();
		context.run();
	}

}
