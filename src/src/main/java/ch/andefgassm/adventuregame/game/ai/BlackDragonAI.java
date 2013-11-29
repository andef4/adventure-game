package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class BlackDragonAI extends AbstractAICombatant{

    public BlackDragonAI(CombatSystem system) {
        super(system, "The Black Dragon", 500);
        addSkill("black_dragon_breath");
    }
    
    @Override
    public String getNextSkill() {
        return getAvailableSkills().get(0);
    }
}
