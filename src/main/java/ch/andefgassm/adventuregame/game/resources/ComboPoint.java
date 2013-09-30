package ch.andefgassm.adventuregame.game.resources;

import ch.andefgassm.adventuregame.combat.IResource;

public class ComboPoint implements IResource {

    public int getMax() {
        return 5;
    }

    public int getMin() {
        return 0;
    }

}
