package ch.andefgassm.adventuregame.game.inventory;

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private static Player instance;
	
	public static Player getInstance()
	{
		if (instance == null){
			instance = new Player();
		}
		
		return instance;
	}
	
	private List<Item> inventory = new ArrayList<Item>();
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public void equip(Item item) {
	}
}
