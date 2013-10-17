package ch.andefgassm.adventuregame.combat;

public class ActiveEffect {
    private Effect effect = null;
    private int intervalsLeft = 0;
    
    public ActiveEffect(Effect effect) {
        this.effect = effect;
        this.intervalsLeft = effect.getIntervalsRunning();
    }
    
    public Effect getEffect() {
        return effect;
    }
    public int getIntervalsLeft() {
        return intervalsLeft;
    }
    /**
     * reduce interval by 1
     * @return true if no intervals are left
     */
    public boolean decrementInterval() {
        intervalsLeft--;
        return intervalsLeft <= 0;
    }

}
