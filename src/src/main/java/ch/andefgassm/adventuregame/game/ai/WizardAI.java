package ch.andefgassm.adventuregame.game.ai;

import java.util.Random;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class WizardAI extends AbstractAICombatant {

    enum Phase {DAMAGE, HEAL};
    private Phase phase = Phase.DAMAGE;
    private Random random = new Random();
    private int currentRound = 0;

    public WizardAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
        addSkill("wizard_flash");
        addSkill("wizard_tempest");
        addSkill("wizard_heal");
    }

    @Override
    public String getNextSkill() {
        String skill = null;
        if (currentRound >= 3) {
            if (phase == Phase.DAMAGE) {
                phase = Phase.HEAL;
            } else {
                phase = Phase.DAMAGE;
            }
            currentRound = 0;
        }

        switch (phase) {
        case DAMAGE:
            if (random.nextFloat() > 0.5) {
                skill = "wizard_tempest";
            } else {
                skill = "wizard_flash";
            }
            break;
        case HEAL:
            if (random.nextFloat() > 0.5) {
                skill = "wizard_heal";
            } else {
                skill = "wizard_flash";
            }
        }
        currentRound++;
        return skill;
    }
}
