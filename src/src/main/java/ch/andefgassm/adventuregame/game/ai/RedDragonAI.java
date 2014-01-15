package ch.andefgassm.adventuregame.game.ai;

import java.util.Random;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class RedDragonAI extends AbstractAICombatant {

    public RedDragonAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
        addSkill("red_dragon_rake");
        addSkill("red_dragon_flame_breath");
    }

    @Override
    public String getNextSkill() {
        Random r = new Random();
        float rnd = r.nextFloat();

        if (rnd < 0.7) {
            return "red_dragon_rake";
        } else {
            return "red_dragon_flame_breath";
        }
    }
}
