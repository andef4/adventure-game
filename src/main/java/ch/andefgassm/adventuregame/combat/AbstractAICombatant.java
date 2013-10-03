package ch.andefgassm.adventuregame.combat;

public abstract class AbstractAICombatant extends Combatant {
    
    public AbstractAICombatant(CombatSystem system) {
        super(system);
    }

    public abstract String getNextSkill();

}
