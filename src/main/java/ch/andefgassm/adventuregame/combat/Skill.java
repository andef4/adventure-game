package ch.andefgassm.adventuregame.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A skill usable by players an enemies
 * @author andef4
 */
public class Skill {
	private String name = null;
	private Map<String, Integer> requiredResources = new HashMap<String, Integer>();
	private List<Effect> enemyEffects = new ArrayList<Effect>();
	private List<Effect> playerEffects = new ArrayList<Effect>();
	
	public Skill(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public Map<String, Integer> getRequiredResources() {
		return requiredResources;
	}
	public List<Effect> getEnemyEffects() {
		return enemyEffects;
	}
	public List<Effect> getPlayerEffects() {
		return playerEffects;
	}
}
