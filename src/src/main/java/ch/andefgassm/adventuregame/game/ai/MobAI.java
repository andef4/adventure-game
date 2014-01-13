package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class MobAI extends AbstractAICombatant{

    public MobAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
        addSkill("mob_attack");
    }

    @Override
    public String getNextSkill() {
        return getAvailableSkills().get(0);
    }
}
