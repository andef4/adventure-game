package ch.andefgassm.adventuregame.combat;


/*
 * examples:
 * Reduce all damage by 10 %
 * Reduce fire damage by 10 %
 * 
 * These modifiers are added as buffs/talents/potions
 */
public interface ISpellModifier {	
	public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage, float value);
}
