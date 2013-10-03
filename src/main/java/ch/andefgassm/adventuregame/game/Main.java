package ch.andefgassm.adventuregame.game;

import java.util.List;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.Skill;


public class Main {
    
    public static void main(String[] args) {
        CombatSystem system = new CombatSystem();
        
        Skill strikeSkill = new Skill("Strike");
        strikeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        Effect strikeDamageEffect = new Effect();
        strikeDamageEffect.getStatChanges().put(Stat.LIFE, -50);
        strikeSkill.getEnemyEffects().add(strikeDamageEffect);
        Effect strikeComboPointEffect = new Effect();
        strikeComboPointEffect.getResourceChanges().put(Resource.COMBO_POINT, 1);
        strikeSkill.getPlayerEffects().add(strikeComboPointEffect);
        system.registerSkill("warrior_strike", strikeSkill);
        
        Skill executeSkill = new Skill("Execute");
        executeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        executeSkill.getRequiredResources().put(Resource.COMBO_POINT, 3);
        Effect executeEffect = new Effect();
        executeEffect.getStatChanges().put(Stat.LIFE, -200);
        executeSkill.getEnemyEffects().add(executeEffect);
        system.registerSkill("warrior_execute", executeSkill);
        
        Skill breathSkill = new Skill("Breath");
        Effect breathEffect = new Effect();
        breathEffect.getStatChanges().put(Stat.LIFE, -70);
        breathSkill.getEnemyEffects().add(breathEffect);
        system.registerSkill("drake_breath", breathSkill);
        
        Combatant player = new Combatant(system);
        player.addSkill("warrior_strike");
        player.addSkill("warrior_execute");
        player.getResources().put(Resource.ENERGY, 100);
        player.getResources().put(Resource.COMBO_POINT, 0);
        
        //DrakeAI enemy = new DrakeAI(system);
        
        while(true) {
        	System.out.println("--------------------------------------");
            List<String> skills = player.getAvailableSkills();
            
            for(int i = 0; i < skills.size(); i++) {
                Skill skill = system.getSkill(skills.get(i));
                System.out.println(String.format("[%d] %s", i + 1, skill.getName()));
            }
            
            System.out.println(player.getStats());
            break;
        }
    }
}
