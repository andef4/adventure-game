package ch.andefgassm.adventuregame.combat;

public abstract class AbstractAICombatant extends Combatant {
    
    
    public AbstractAICombatant(CombatSystem system, String name, int maxLife) {
        super(system, name, maxLife);
    }

    public abstract String getNextSkill();

}
