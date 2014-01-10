package ch.andefgassm.adventuregame.game.skillmodifiers;

import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.ISkillModifier;

public class AuraEnhanceDamage implements ISkillModifier {

    @Override
    public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage, float value) {
        return baseDamage;
    }

}
