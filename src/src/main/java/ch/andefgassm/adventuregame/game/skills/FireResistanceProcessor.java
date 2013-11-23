package ch.andefgassm.adventuregame.game.skills;

import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.StatProcessor;
import ch.andefgassm.adventuregame.game.DamageType;
import ch.andefgassm.adventuregame.game.Stat;

public class FireResistanceProcessor implements StatProcessor {

	@Override
	public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage) {
		int effectiveDamage = baseDamage;
		if (effect.getDamageType() == DamageType.FIRE) {
			Integer fireResistance = target.getCurrentStats().get(Stat.FIRE_RESISTANCE);
			if (fireResistance != null) {
				effectiveDamage = baseDamage - fireResistance;
				if (effectiveDamage < 0) {
					effectiveDamage = 0;
				}
			}
		}
		return effectiveDamage;
	}
}
