package ch.andefgassm.adventuregame.game.inventory;

import java.util.HashMap;
import java.util.Map;

import ch.andefgassm.adventuregame.combat.IStat;

public class Item {
	private Map<IStat, Integer> stats = new HashMap<IStat, Integer>();
	private ItemType type;
	private String name;
	public ItemType getType() {
		return type;
	}
	public void setType(ItemType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<IStat, Integer> getStats() {
		return stats;
	}
}
