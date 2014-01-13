package ch.andefgassm.adventuregame.game.ai;

import java.util.Random;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class BlackDragonAI extends AbstractAICombatant {

    private int round = 0;
    private boolean berserk = false;

    public BlackDragonAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
        addSkill("black_dragon_split");
        addSkill("black_dragon_tail_sweep");
        addSkill("black_dragon_berserker");
    }

    @Override
    public String getNextSkill() {
        Random r = new Random();
        float rnd = r.nextFloat();

        round++;

        if (!berserk && round >= 10) {
            berserk = true;
            return "black_dragon_berserker";
        }

        if (rnd < 0.5) {
            return "black_dragon_split";
        } else {
            return "black_dragon_tail_sweep";
        }

    }
}
