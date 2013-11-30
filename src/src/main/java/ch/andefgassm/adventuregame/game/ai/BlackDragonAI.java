package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class BlackDragonAI extends AbstractAICombatant{

    public BlackDragonAI(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
        addSkill("black_dragon_breath");
    }
    
    @Override
    public String getNextSkill() {
        return getAvailableSkills().get(0);
    }
}
