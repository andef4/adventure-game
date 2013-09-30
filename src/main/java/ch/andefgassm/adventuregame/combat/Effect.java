package ch.andefgassm.adventuregame.combat;

import java.util.Map;

public class Effect {
	
	private int intervalsRunning = 1;
	private int everyInterval = 1;
	
	private Map<IResource, Integer> resourceChanges;
	private Map<IStat, Integer> statChanges;
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
}
