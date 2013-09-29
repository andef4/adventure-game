package ch.andefgassm.adventuregame.game;

import java.util.List;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;
import old.Combat;


public class Main {
    
    public static void main(String[] args) {
        
        CombatSystem system = new CombatSystem();
        system.load(".");
        
        Combat combat = system.createCombat();
        
        Combatant player = new Combatant();
        
        
        
        
        //Combatant enemy = combat.createCombatant(enemyBaseStats);
        
        
        
        while(true) {
        	
        	List<Skill> skills = player.getAvailableSkills();
        	
        }
        
        
        
    }
    

}
