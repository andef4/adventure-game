package ch.andefgassm.adventuregame.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Combatant {
	
    private Map<IStat, Integer> stats = new HashMap<IStat, Integer>();
    private Map<IResource, Integer> resources = new HashMap<IResource, Integer>();
    private List<String> skills = new ArrayList<String>();
    private CombatSystem system = null;
	
	public Combatant(CombatSystem system) {
	    this.system = system;
    }

    public List<String> getAvailableSkills() {
        List<String> availableSkills = new ArrayList<String>();
        for(String skill : skills) {
            Map<IResource, Integer> requiredResources = system.getSkill(skill).getRequiredResources();
            boolean add = true;
            for (Entry<IResource, Integer> requiredResource : requiredResources.entrySet()) {
                if (!resources.containsKey(requiredResource.getKey()) ||
                        resources.get(requiredResource.getKey()) < requiredResource.getValue()) {
                    add = false;
                    break;
                }
            }
            if (add) {
                availableSkills.add(skill);
            }
        }
	    return availableSkills;
	}
	
    /**
     * @return the stats
     */
    public Map<IStat, Integer> getStats() {
        return stats;
    }
    
    /**
     * @return the resources
     */
    public Map<IResource, Integer> getResources() {
        return resources;
    }
    
    public void addSkill(String skill) {
        if (system.getSkill(skill) == null) {
            throw new IllegalArgumentException("Unknown skill " + skill);
        }
        skills.add(skill);
    }
}
