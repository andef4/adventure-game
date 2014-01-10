package ch.andefgassm.adventuregame.game.spellmodifiers;

import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.ISpellModifier;

public class AuraEnhanceDamage implements ISpellModifier {

    @Override
    public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage, float value) {
        return baseDamage;
    }

}
