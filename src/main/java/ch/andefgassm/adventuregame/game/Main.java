package ch.andefgassm.adventuregame.game;

import java.util.List;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.resources.ComboPoint;
import ch.andefgassm.adventuregame.game.resources.Energy;
import ch.andefgassm.adventuregame.game.stats.Life;
import old.Combat;


public class Main {
    
    public static void main(String[] args) {
        CombatSystem system = new CombatSystem();
        
        Skill strikeSkill = new Skill("warrior_stike", "Strike");
        strikeSkill.getRequiredResources().put(new Energy(), 20);
        Effect strikeDamageEffect = new Effect();
        strikeDamageEffect.getStatChanges().put(new Life(), -50);
        strikeSkill.getEnemyEffects().add(strikeDamageEffect);
        Effect strikeComboPointEffect = new Effect();
        strikeComboPointEffect.getResourceChanges().put(new ComboPoint(), 1);
        strikeSkill.getPlayerEffects().add(strikeComboPointEffect);
        system.registerSkill(strikeSkill);
        
        Skill executeSkill = new Skill("warrior_execute", "Execute");
        executeSkill.getRequiredResources().put(new Energy(), 20);
        executeSkill.getRequiredResources().put(new ComboPoint(), 3);
        Effect executeEffect = new Effect();
        executeEffect.getStatChanges().put(new Life(), -200);
        executeSkill.getEnemyEffects().add(executeEffect);
        system.registerSkill(executeSkill);
        
        Skill breathSkill = new Skill("drake_breath", "Breath");
        Effect breathEffect = new Effect();
        breathEffect.getStatChanges().put(new Life(), -70);
        breathSkill.getEnemyEffects().add(breathEffect);
        system.registerSkill(breathSkill);
        
        Combat combat = system.createCombat();
        
        Combatant player = new Combatant();
        player.addSkill("warrior_strike");
        player.addSKill("warrior_execute");
        
        
        
        //Combatant enemy = combat.createCombatant(enemyBaseStats);
        
        while(true) {
        	System.out.println("--------------------------------------");
            List<Skill> skills = player.getAvailableSkills();
            
            for(Skill skill : skills) {
                System.out.println(skill);
            }
            
            
            
        	
        }
        
        
        
    }
    

}
