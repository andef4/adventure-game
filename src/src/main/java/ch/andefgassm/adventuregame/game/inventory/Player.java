package ch.andefgassm.adventuregame.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private Map<ItemType, Item> equipment = new HashMap<ItemType, Item>();
	
	public Map<ItemType, Item> getEquipment() {
		return equipment;
	}
	
	public void equip(Item item) {
		this.getEquipment().put(item.getType(), item);
	}
}
