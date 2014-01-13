package ch.andefgassm.adventuregame.game.ai;

import java.util.Random;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

/*
Balduin
-------
Phase 1 (100-75%) {
 * Pummel/Zuschlagen (33%)
 * Slam/Zerschmettern (66%)
}

Phase 2 (75-50%) {
// extemer schaden
 * Pummel/Zuschlagen (33%)
 * Firestorm/Feuersturm (66%)
}

Phase 3 (50-25%) {
// heal
 * Pummel/Zuschlagen (50%)
 * Heal/Heilen (50%)
}

Phase 4 (25-0%) {
 * Firestorm/Feuersturm (33%)
 * Pummel/Zuschlagen  (33%)
 * Heal/Heilen (33%)
}
 */
public class BalduinAI extends AbstractAICombatant {

    enum Phase {START, DAMAGE, HEAL, END};
    private Phase phase = Phase.START;
    private Random random = new Random();

    private static final String PUMMEL = "balduin_pummel";
    private static final String SLAM = "balduin_slam";
    private static final String FIRESTORM = "balduin_firestorm";
    private static final String HEAL = "balduin_heal";

    public BalduinAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
    }

    @Override
    public String getNextSkill() {
        String skill = null;
        switch (phase) {
        case START:
            if (getLivePercent() < 75) {
                phase = Phase.DAMAGE;
            } else {
                if (random.nextFloat() < 0.67f) {
                    skill = SLAM;
                } else {
                    skill = PUMMEL;
                }
                break;
            }
        case DAMAGE:
            if (getLivePercent() < 50) {
                phase = Phase.HEAL;
            } else {
                if (random.nextFloat() < 0.67) {
                    skill = FIRESTORM;
                } else {
                    skill = PUMMEL;
                }
                break;
            }
        case HEAL:
            if (getLivePercent() < 25) {
                phase = Phase.END;
            } else {
                if (random.nextFloat() < 0.50) {
                    skill = HEAL;
                } else {
                    skill = PUMMEL;
                }
                break;
            }
        case END:
            float rnd = random.nextFloat();
            if (rnd < 0.34) {
                skill = FIRESTORM;
            } else if (rnd < 0.67) {
                skill = HEAL;
            } else {
                skill = PUMMEL;
            };
        }
        return skill;
    }

    double getLivePercent() {
        return 100.0 / getMaxLife()  * getCurrentLife();
    }

}
