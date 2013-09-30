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
	private String displayName = null;
	private Map<IResource, Integer> requiredResources = new HashMap<IResource, Integer>();
	private List<Effect> enemyEffects = new ArrayList<Effect>();
	private List<Effect> playerEffects = new ArrayList<Effect>();
	
	public Skill(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}
	
	public String getName() {
		return name;
	}
	public String getDisplayName() {
        return displayName;
    }
	public Map<IResource, Integer> getRequiredResources() {
		return requiredResources;
	}
	public List<Effect> getEnemyEffects() {
		return enemyEffects;
	}
	public List<Effect> getPlayerEffects() {
		return playerEffects;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Skill [name=" + name + ", displayName=" + displayName
                + ", requiredResources=" + requiredResources
                + ", enemyEffects=" + enemyEffects + ", playerEffects="
                + playerEffects + "]";
    }
}
