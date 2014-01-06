package ch.andefgassm.adventuregame.combat;

/*
 * Calculate physical damage reduction based on log(armor)
 * Reduce fire damage based on fire resistance
 * Calculate blocking using block value and block chance
 * These modifiers are register on the combatSystem and modifies damage based on stats
 */
public interface IStatProcessor {
    public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage);
}
