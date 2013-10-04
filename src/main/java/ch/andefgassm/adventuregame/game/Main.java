package ch.andefgassm.adventuregame.game;

import java.util.List;
import java.util.Scanner;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.ai.BlackDragonAI;


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
        
        Combatant player = new Combatant(system, "Player");
        player.addSkill("warrior_strike");
        player.addSkill("warrior_execute");
        player.getResources().put(Resource.ENERGY, 500);
        player.getResources().put(Resource.COMBO_POINT, 0);
        player.getStats().put(Stat.LIFE, 1000);
        
        BlackDragonAI enemy = new BlackDragonAI(system);
        
        while(true) {
        	System.out.println("--------------------------------------");
        	System.out.println("########## Enemy stats ###############");
        	System.out.println(enemy.getStats());
        	System.out.println("########## Player stats ##############");
        	System.out.println(player.getStats());
        	System.out.println("########## Player resources ##########");
        	System.out.println(player.getResources());
        	
            List<String> skills = player.getAvailableSkills();
            if (skills.size() == 0) {
                System.out.println("Player has no resources left.");
                return;
            }
            
            for(int i = 0; i < skills.size(); i++) {
                Skill skill = system.getSkill(skills.get(i));
                System.out.println(String.format("[%d] %s", i + 1, skill.getName()));
            }
            Scanner scanner = new Scanner(System.in);
            int i = 0;
            do {
                i = scanner.nextInt() - 1;
            } while (i < 0 || i >= skills.size());
            
            player.cast(skills.get(i), enemy); // add effects to player and enemy
            enemy.applyEffects(); // calculate stats
            enemy.cast(enemy.getNextSkill(), player); // add effects to enemy and player
            player.applyEffects();  // calculate stats
            
            if (enemy.getStats().get(Stat.LIFE) == 0) {
                System.out.println(player.getName() + " has defeated " + enemy.getName());
                break;
            }
            
            if (player.getStats().get(Stat.LIFE) == 0) {
                System.out.println(enemy.getName() + " has defeated " + player.getName());
                break;
            }
        }
    }
}
