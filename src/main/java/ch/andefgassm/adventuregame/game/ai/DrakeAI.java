package ch.andefgassm.adventuregame.game.ai;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;

public class DrakeAI extends AbstractAICombatant{

    
    public DrakeAI(CombatSystem system) {
        
    }
    
    @Override
    public void getNextSkill() {
        return getAvailableSkills()[0];
    }

}
