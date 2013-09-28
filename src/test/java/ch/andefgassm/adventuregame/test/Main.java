package ch.andefgassm.adventuregame.test;

import ch.andefgassm.adventuregame.CombatSystem;

public class Main {
    
    public static void main(String[] args) {
        
        CombatSystem system = new CombatSystem();
        system.load(".");
        
        Combat combat = system.createCombat();
        
        Combatant player = combat.createCombatant(playerBaseStats);
        Combatant enemy = combat.createCombatant(enemyBaseStats);
        
    }
    

}
