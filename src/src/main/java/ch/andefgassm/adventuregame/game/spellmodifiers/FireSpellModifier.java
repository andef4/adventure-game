package ch.andefgassm.adventuregame.game.spellmodifiers;

import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.ISpellModifier;
import ch.andefgassm.adventuregame.game.DamageType;

public class FireSpellModifier implements ISpellModifier {

	@Override
	public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage, float value) {
		int effectiveDamage = baseDamage;
		if (effect.getDamageType() == DamageType.FIRE) {
			effectiveDamage *= 1 + value;
		}
		return effectiveDamage;
	}
}
