package ch.andefgassm.adventuregame.game;

import ch.andefgassm.adventuregame.combat.IResource;


public enum Resource implements IResource {
    ENERGY(0, 100),
    COMBO_POINT(0, 5);
    
    private int min;
    private int max;
    
    private Resource(int min, int max) {
        this.min = min;
        this.max = max;
    }
    
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
}