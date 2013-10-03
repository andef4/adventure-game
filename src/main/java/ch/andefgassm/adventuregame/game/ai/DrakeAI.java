package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class DrakeAI extends AbstractAICombatant{

    
    public DrakeAI(CombatSystem system) {
        super(system);
        addSkill("drake_breath");
    }
    
    

    @Override
    public String getNextSkill() {
        return getAvailableSkills().get(0);
    }

}
