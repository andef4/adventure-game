package ch.andefgassm.adventuregame.game.inventory;

import java.util.ArrayList;
import java.util.List;

public class Character {
	private List<Item> inventory = new ArrayList<Item>();
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public void equip(Item item) {
	}
}
