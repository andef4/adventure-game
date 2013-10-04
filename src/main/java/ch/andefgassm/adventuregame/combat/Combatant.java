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
    private List<ActiveEffect> activeEffects = new ArrayList<ActiveEffect>();
    private String name;
    
	public Combatant(CombatSystem system, String name) {
	    this.system = system;
	    this.name = name;
    }
	
	public String getName() {
        return name;
    }

    public List<String> getAvailableSkills() {
        List<String> availableSkills = new ArrayList<String>();
        for(String skill : skills) {
            Map<IResource, Integer> requiredResources = system.getSkill(skill).getRequiredResources();
            boolean add = true;
            for (Entry<IResource, Integer> requiredResource : requiredResources.entrySet()) {
                // check if there are enough resources use this skill
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

    public void cast(String skillName, Combatant enemy) {
        Skill skill = system.getSkill(skillName);
        // add effects to the player
        for (Effect playerEffect : skill.getPlayerEffects()) {
            activeEffects.add(new ActiveEffect(playerEffect));
        }
        // add effects to the enemy
        for (Effect enemyEffect : skill.getEnemyEffects()) {
            enemy.activeEffects.add(new ActiveEffect(enemyEffect));
        }
        // remove required resources from player
        for (Entry<IResource, Integer> requiredResource : skill.getRequiredResources().entrySet()) {
            Integer resource = resources.get(requiredResource.getKey());
            if (resource == null || resource - requiredResource.getValue() < 0) {
                throw new IllegalArgumentException(String.format("%s can't cast ability %s, not enough %s", name, skill.getName(), requiredResource.getKey().getName()));
            }
            resources.put(requiredResource.getKey(), resource - requiredResource.getValue());
        }
    }
    
    /**
     * applies all effects on this combatant one round
     */
    public void applyEffects() {
        // effects to delete after this round
        List<ActiveEffect> effectsToRemove = new ArrayList<ActiveEffect>();
        for (ActiveEffect activeEffect : activeEffects) {
            for (Entry<IResource, Integer> resourceChange : activeEffect.getEffect().getResourceChanges().entrySet()) {
                IResource resource = resourceChange.getKey();
                int change = resourceChange.getValue();
                Integer current = resources.get(resource);
                if (current != null) {
                    current += change;
                    if (current < resource.getMin()) {
                        current = resource.getMin();
                    }
                    if (current > resource.getMax()) {
                        current = resource.getMax();
                    }
                    resources.put(resource, current);
                }
            }
            for (Entry<IStat, Integer> statChange : activeEffect.getEffect().getStatChanges().entrySet()) {
                IStat stat = statChange.getKey();
                int change = statChange.getValue();
                Integer current = stats.get(stat);
                if (current != null) {
                    current += change;
                    if (current < 0) {
                        current = 0;
                    }
                    stats.put(stat, current);
                }
            }
            
            if (activeEffect.decrementInterval()) {
                effectsToRemove.add(activeEffect);
            }
        }
        activeEffects.removeAll(effectsToRemove);
    }
}