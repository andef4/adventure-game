package ch.andefgassm.adventuregame.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Combatant {

    private Map<IStat, Integer> baseStats = new HashMap<IStat, Integer>();
    private Map<IResource, Integer> resources = new HashMap<IResource, Integer>();
    private List<String> skills = new ArrayList<String>();
    protected CombatSystem system = null;

    protected List<ActiveEffect> activeHarmfulEffects = new ArrayList<ActiveEffect>();
    protected List<ActiveEffect> activeHelpfulEffects = new ArrayList<ActiveEffect>();

    private String name = null;
    private int currentLife = 0;
    private int maxLife = 0;

    public Combatant(CombatSystem system, String name, int maxLife) {
        this.system = system;
        this.name = name;
        this.maxLife = maxLife;
        this.currentLife = maxLife;
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

    public void addSkill(String skill) {
        if (system.getSkill(skill) == null) {
            throw new IllegalArgumentException("Unknown skill " + skill);
        }
        skills.add(skill);
    }

    public void cast(String skillId, Combatant target) {
        Skill skill = system.getSkill(skillId);
        // add effects to the caster
        for (Effect casterEffect : skill.getCasterEffects()) {
            activeHelpfulEffects.add(new ActiveEffect(skill.getName(), casterEffect, this, this, system));
        }
        // add effects to the target
        if (this == target) {
            for (Effect targetEffect : skill.getTargetEffects()) {
                activeHelpfulEffects.add(new ActiveEffect(skill.getName(), targetEffect, this, target, system));
            }
        } else {
            for (Effect targetEffect : skill.getTargetEffects()) {
                target.activeHarmfulEffects.add(new ActiveEffect(skill.getName(), targetEffect, this, target, system));
            }
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


    public Map<IStat, Integer> getCurrentStats() {
        Map<IStat, Integer> currentStats = new HashMap<IStat, Integer>();
        for (Entry<IStat, Integer> stat : baseStats.entrySet()) {
            currentStats.put(stat.getKey(), stat.getValue());
        }
        calculateStats(activeHarmfulEffects, currentStats);
        calculateStats(activeHelpfulEffects, currentStats);
        return currentStats;
    }

    private void calculateStats(List<ActiveEffect> effects, Map<IStat, Integer> currentStats) {
        for (ActiveEffect activeEffect : effects) {
            // calculate current stats by applying the effects to the baseStats
            for (Entry<IStat, Integer> statChange : activeEffect.getEffect().getStatChanges().entrySet()) {
                IStat stat = statChange.getKey();
                int change = statChange.getValue();
                Integer current = currentStats.get(stat);
                if (current != null) {
                    current += change;
                    if (current < 0) {
                        current = 0;
                    }
                    currentStats.put(stat, current);
                }
            }
        }
    }

    /**
     * applies all effects on this combatant
     */
    public List<CombatEvent> applyHelpfulEffects() {
        return applyEffects(activeHelpfulEffects);
    }

    public List<CombatEvent> applyHarmfulEffects() {
        return applyEffects(activeHarmfulEffects);
    }

    private List<CombatEvent> applyEffects(List<ActiveEffect> effects) {
        List<CombatEvent> events = new ArrayList<CombatEvent>();
        // calculate damage and healing and remove running out effects
        List<ActiveEffect> effectsToRemove = new ArrayList<ActiveEffect>(); // effects to delete after this round
        for (ActiveEffect activeEffect : effects) {
            int effectiveLiveChange = activeEffect.calculateEffectiveLifeChange();
            currentLife += effectiveLiveChange;
            if (activeEffect.decrementInterval()) {
                effectsToRemove.add(activeEffect);
            }

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
            if (effectiveLiveChange != 0) {
                events.add(new CombatEvent(activeEffect.getCaster(), activeEffect.getTarget(),
                        activeEffect.getSkillName(), effectiveLiveChange));
            }
        }
        if (currentLife < 0) {
            currentLife = 0;
        }
        effects.removeAll(effectsToRemove);
        return events;
    }


    /**
     * @return the stats
     */
    public Map<IStat, Integer> getBaseStats() {
        return baseStats;
    }

    /**
     * @return the resources
     */
    public Map<IResource, Integer> getResources() {
        return resources;
    }

    public List<ActiveEffect> getActiveHarmfulEffects() {
        return activeHarmfulEffects;
    }

    public List<ActiveEffect> getActiveHelpfulEffects() {
        return activeHelpfulEffects;
    }

    public String getName() {
        return name;
    }

    public int getCurrentLife() {
        return currentLife;
    }
    public int getMaxLife() {
        return maxLife;
    }
}
