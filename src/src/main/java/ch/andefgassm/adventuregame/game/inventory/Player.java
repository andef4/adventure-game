package ch.andefgassm.adventuregame.game.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.andefgassm.adventuregame.combat.IStat;
import ch.andefgassm.adventuregame.game.state.GameStateContext;

public class Player {
	private List<String> inventory = new ArrayList<String>();

	public List<String> getInventory() {
		return inventory;
	}

	private Map<ItemType, String> equipment = new HashMap<ItemType, String>();
	private List<String> skills = new ArrayList<String>();
	private GameStateContext context;

	public Player(GameStateContext context) {
		this.context = context;
        skills.add("player_strike");
        skills.add("player_execute");
    }

	public Map<ItemType, String> getEquipment() {
		return equipment;
	}

	public void equip(String item) {
		this.getEquipment().put(context.getItem(item).getType(), item);
	}

	public Map<IStat, Integer> getStats() {
		Map<IStat, Integer> stats = new HashMap<IStat, Integer>();
		for (String itemId : equipment.values()) {
			Item item = context.getItem(itemId);
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

	public List<String> getSkills() {
		return skills ;
	}
}
