package ch.andefgassm.adventuregame.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.andefgassm.adventuregame.combat.IStat;

public class Player {
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

	public Map<IStat, Integer> getStats() {
		Map<IStat, Integer> stats = new HashMap<IStat, Integer>();
		for (Item item : equipment.values()) {
			for (Entry<IStat, Integer> stat : item.getStats().entrySet()) {
				if (stats.containsKey(stat.getKey())) {
					Integer value = stats.get(stat.getKey());
					value += stat.getValue();
					stats.put(stat.getKey(), value);
				} else {
					stats.put(stat.getKey(), stat.getValue());
				}
			}
		}
		return stats;
	}
}
