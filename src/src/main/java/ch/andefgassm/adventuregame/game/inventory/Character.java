package ch.andefgassm.adventuregame.game.inventory;

import java.util.ArrayList;
import java.util.List;

public class Character {
	
	private static Character instance;
	
	public static Character getInstance()
	{
		if (instance == null){
			instance = new Character();
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
