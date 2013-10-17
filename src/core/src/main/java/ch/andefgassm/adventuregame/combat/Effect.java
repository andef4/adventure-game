package ch.andefgassm.adventuregame.combat;

import java.util.HashMap;
import java.util.Map;

import ch.andefgassm.adventuregame.game.Stat;

public class Effect {
	private int intervalsRunning = 1;
	private int everyInterval = 1;
	
	private Map<IResource, Integer> resourceChanges = new HashMap<IResource, Integer>();
	private Map<IStat, Integer> statChanges = new HashMap<IStat, Integer>();

	private int lifeChange = 0;
	private IStat scalingStat = null;
	private double scalingValue = 0.0;
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
    
    
    // TODO: implement effects scaling with stats
    protected void setLifeChange(int lifeChange, Stat scalingStat, double scalingValue) {
        this.lifeChange = lifeChange;
        this.scalingStat = scalingStat;
        this.scalingValue = scalingValue;
    }
    
    public void setLifeChange(int lifeChange) {
        this.lifeChange = lifeChange;
    }
    
    public int getLifeChange() {
        return lifeChange;
    }
    public IStat getScalingStat() {
        return scalingStat;
    }
    public double getScalingValue() {
        return scalingValue;
    }
    
}
