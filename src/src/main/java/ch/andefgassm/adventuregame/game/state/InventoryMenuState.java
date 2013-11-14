package ch.andefgassm.adventuregame.game.state;

import java.util.List;

import ch.andefgassm.adventuregame.game.inventory.Item;
import ch.andefgassm.adventuregame.game.inventory.Player;

public class InventoryMenuState extends AbstractConsoleGameState {
	
	//character mit items ausruesten	
	
	public void init(GameStateContext context) {
		
		new Player();
		Player p = Player.getInstance();
		
	    clear();
		println("Inventory Menu");
		println("=========");
		println("");
		println("Inventory (press button to equip item)");
		println("--------------------------------------");
		
		List<Item> inventory = p.getInventory();
		
		//testItem
		Item testItem = new Item();
		testItem.setName("Todes-Messer-NunJackOh");
		inventory.add(testItem);		
		//testItem
				
		for(int i=1; inventory.size() <= i; i++)
		{
			
			println(i + ") " + inventory.get(i-1).getName().toString());
		}
		

//		println("1) [x] Axe [20 Stamina, 30 Strength]");
//		println("2) [ ] Shield [25 Stamina, 20 Strength]");
//		println("");
		
		//getInt("Your choice:", 1, 1);
		
		context.changeState(GameStateContext.MAIN_MENU);
	}

	@Override
	public void handleInput(int input) {
		
	}

}
