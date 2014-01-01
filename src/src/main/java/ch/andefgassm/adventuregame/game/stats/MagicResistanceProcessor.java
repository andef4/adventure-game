package ch.andefgassm.adventuregame.game.stats;

import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.IStatProcessor;
import ch.andefgassm.adventuregame.game.DamageType;
import ch.andefgassm.adventuregame.game.Stat;

public class MagicResistanceProcessor implements IStatProcessor {

	@Override
	public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage) {
		int effectiveDamage = baseDamage;
		if (effect.getDamageType() == DamageType.FIRE) {
			Integer magicResistance = target.getCurrentStats().get(Stat.MAGIC_RESISTANCE);
			if (magicResistance != null) {
				effectiveDamage = baseDamage - magicResistance;
				if (effectiveDamage < 0) {
					effectiveDamage = 0;
				}
			}
		}
		return effectiveDamage;
	}
}
