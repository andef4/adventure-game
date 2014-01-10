package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.combat.ActiveEffect;
import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;

public class CombatPlayer extends Combatant {

    public static final String PLAYER_NAME = "Spieler";

    public CombatPlayer(CombatSystem system, int maxLife) {
        super(system, PLAYER_NAME, maxLife);
    }

    @Override
    public void cast(String skillId, Combatant target) {


        Skill aura1 = system.getSkill("aura_reduce_damage");
        Skill aura2 = system.getSkill("aura_enhance_damage");

        // only allow one active aura
        if (skillId.equals(aura1.getId()) || skillId.equals(aura2.getId())) {
            ActiveEffect effectToRemove = null;
            for (ActiveEffect activeEffect : activeHelpfulEffects) {
                if (activeEffect.getSkillName().equals(aura1.getName()) ||
                        activeEffect.getSkillName().equals(aura2.getName())) {
                    effectToRemove = activeEffect;
                    break;
                }
            }
            if (effectToRemove != null) {
                activeHelpfulEffects.remove(effectToRemove);
            }
        }
        super.cast(skillId, target);
    }
}
