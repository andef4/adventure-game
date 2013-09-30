package ch.andefgassm.adventuregame.game.resources;

import ch.andefgassm.adventuregame.combat.IResource;

public class Energy implements IResource {
    public int getMax() {
        return 100;
    }
    public int getMin() {
        return 0;
    }
}
