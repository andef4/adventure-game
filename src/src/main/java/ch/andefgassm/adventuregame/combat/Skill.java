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
	private boolean harmful;
	private Map<IResource, Integer> requiredResources = new HashMap<IResource, Integer>();
	private List<Effect> enemyEffects = new ArrayList<Effect>();
	private List<Effect> playerEffects = new ArrayList<Effect>();
	
	
	public Skill(String name, boolean harmful) {
		this.name = name;
		this.harmful= harmful;
	}
	public String getName() {
		return name;
	}
	public boolean isHarmful() {
		return harmful;
	}
	public Map<IResource, Integer> getRequiredResources() {
		return requiredResources;
	}
	public List<Effect> getTargetEffects() {
		return enemyEffects;
	}
	public List<Effect> getCasterEffects() {
		return playerEffects;
	}
}
