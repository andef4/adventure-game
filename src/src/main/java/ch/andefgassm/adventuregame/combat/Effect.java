package ch.andefgassm.adventuregame.combat;

import java.util.HashMap;
import java.util.Map;

public class Effect {
    private int intervalsRunning = 1;
    private int everyInterval = 1;
    private IDamageType damageType = null;
    private int baseLifeChange = 0;

    private Map<IResource, Integer> resourceChanges = new HashMap<IResource, Integer>();
    private Map<IStat, Integer> statChanges = new HashMap<IStat, Integer>();
    private Map<IStat, Float> statScaling = new HashMap<IStat, Float>();
    private Map<String, Float> spellModifiers = new HashMap<String, Float>();

    /**
     * @return the intervalsRunning
     */
    public int getIntervalsRunning() {
        return intervalsRunning;
    }
    /**
     * @param intervalsRunning the intervalsRunning to set
     */
    public void setIntervalsRunning(int intervalsRunning) {
        this.intervalsRunning = intervalsRunning;
    }
    /**
     * @return the everyInterval
     */
    public int getEveryInterval() {
        return everyInterval;
    }
    /**
     * @param everyInterval the everyInterval to set
     */
    public void setEveryInterval(int everyInterval) {
        this.everyInterval = everyInterval;
    }

    public IDamageType getDamageType() {
        return damageType;
    }
    public void setDamageType(IDamageType damageType) {
        this.damageType = damageType;
    }
    public int getBaseLifeChange() {
        return baseLifeChange;
    }
    public void setBaseLifeChange(int baseLifeChange) {
        this.baseLifeChange = baseLifeChange;
    }
    /**
     * @return the resourceChanges
     */
    public Map<IResource, Integer> getResourceChanges() {
        return resourceChanges;
    }
    /**
     * @return the statChanges
     */
    public Map<IStat, Integer> getStatChanges() {
        return statChanges;
    }

    public Map<IStat, Float> getStatScaling() {
        return statScaling;
    }

    public Map<String, Float> getSpellModifiers() {
        return spellModifiers;
    }
}
