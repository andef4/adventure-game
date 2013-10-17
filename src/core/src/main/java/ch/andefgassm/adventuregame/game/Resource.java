package ch.andefgassm.adventuregame.game;

import ch.andefgassm.adventuregame.combat.IResource;


public enum Resource implements IResource {
    ENERGY("Energy", 0, 100),
    COMBO_POINT("Combo Point", 0, 5);
    
    private int min;
    private int max;
    private String name;
    
    private Resource(String name, int min, int max) {
        this.min = min;
        this.max = max;
        this.name = name;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public String getName() {
        return name;
    }
}