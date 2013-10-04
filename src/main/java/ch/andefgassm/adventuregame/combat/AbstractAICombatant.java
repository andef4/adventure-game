package ch.andefgassm.adventuregame.combat;

public abstract class AbstractAICombatant extends Combatant {
    
    
    public AbstractAICombatant(CombatSystem system, String name) {
        super(system, name);
    }

    public abstract String getNextSkill();

}
