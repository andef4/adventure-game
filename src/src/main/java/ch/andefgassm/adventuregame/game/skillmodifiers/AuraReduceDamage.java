package ch.andefgassm.adventuregame.game.skillmodifiers;

import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.ISkillModifier;
import ch.andefgassm.adventuregame.game.state.CombatPlayer;

public class AuraReduceDamage implements ISkillModifier {

    @Override
    public int modify(Combatant caster, Combatant target, Effect effect, int baseDamage, float value) {
        if (target.getName().equals(CombatPlayer.PLAYER_NAME) && baseDamage < 0) {
            return (int) ((1.0f - value) * baseDamage);
        }
        return baseDamage;
    }
}
