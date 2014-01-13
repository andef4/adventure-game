package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class WizardAI extends AbstractAICombatant{

    public WizardAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
        addSkill("wizard_flash");
        addSkill("wizard_tempest");
        addSkill("wizard_heal");
    }

    @Override
    public String getNextSkill() {
        return getAvailableSkills().get(0);
    }
}
