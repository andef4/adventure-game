package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.game.Stat;

public class BlackDragonAI extends AbstractAICombatant{

    public BlackDragonAI(CombatSystem system) {
        super(system, "The Black Dragon");
        addSkill("drake_breath");
        getStats().put(Stat.LIFE, 1000);
    }
    
    @Override
    public String getNextSkill() {
        return getAvailableSkills().get(0);
    }
}
