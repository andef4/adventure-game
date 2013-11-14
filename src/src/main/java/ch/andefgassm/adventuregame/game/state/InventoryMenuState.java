package ch.andefgassm.adventuregame.game.state;

import java.util.List;

import ch.andefgassm.adventuregame.game.inventory.Item;
import ch.andefgassm.adventuregame.game.inventory.Player;
import ch.andefgassm.adventuregame.game.ui.Console;

public class InventoryMenuState implements IGameState {

	
	//character mit items ausruesten	
	
	public void handle(GameStateContext context) {
		
		new Player();
		Player p = Player.getInstance();
		
		Console console = context.getConsole();
		console.clear();
		console.println("Inventory Menu");
		console.println("=========");
		console.println("");
		console.println("Inventory (press button to equip item)");
		console.println("--------------------------------------");
		
		List<Item> inventory = p.getInventory();
		
		//testItem
		Item testItem = new Item();
		testItem.setName("Todes-Messer-NunJackOh");
		inventory.add(testItem);		
		//testItem
				
		for(int i=1; inventory.size() <= i; i++)
		{
			
			console.println(i + ") " + inventory.get(i-1).getName().toString());
		}
		

//		console.println("1) [x] Axe [20 Stamina, 30 Strength]");
//		console.println("2) [ ] Shield [25 Stamina, 20 Strength]");
//		console.println("");
		
		console.getInt("Your choice:", 1, 1);
		
		context.changeState(GameStateContext.MAIN_MENU);
	}

}
