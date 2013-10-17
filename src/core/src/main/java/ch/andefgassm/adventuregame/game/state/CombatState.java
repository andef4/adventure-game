package ch.andefgassm.adventuregame.game.state;

import java.util.List;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.ai.BlackDragonAI;
import ch.andefgassm.adventuregame.game.ui.Console;


public class CombatState implements IGameState {

	public void handle(GameStateContext context) {
		CombatSystem system = context.getCombatSystem();
		Console console = context.getConsole();
		
        Combatant player = new Combatant(system, "Player", 500);
        player.addSkill("warrior_strike");
        player.addSkill("warrior_execute");
        player.getResources().put(Resource.ENERGY, 500);
        player.getResources().put(Resource.COMBO_POINT, 0);
        
        BlackDragonAI enemy = new BlackDragonAI(system);
        
        while(true) {
        	console.clear();
        	console.println("Enemy: " + enemy.getCurrentLife() + " life");
        	console.println("Player: " + player.getCurrentLife() + " life");
        	console.print("Player resources: ");
        	console.println(player.getResources().toString());
        	
            List<String> skills = player.getAvailableSkills();
            if (skills.size() == 0) {
                console.println("Player has no resources left.");
            }
            
            for(int i = 0; i < skills.size(); i++) {
                Skill skill = system.getSkill(skills.get(i));
                console.println(String.format("[%d] %s", i + 1, skill.getName()));
            }
            int i = console.getInt("Which skill do you use?", 1, skills.size()) - 1;
            
            player.cast(skills.get(i), enemy); // add effects to player and enemy
            enemy.applyEffects(); // calculate stats
            enemy.cast(enemy.getNextSkill(), player); // add effects to enemy and player
            player.applyEffects();  // calculate stats
            
            if (enemy.getCurrentLife() == 0) {
                console.println(player.getName() + " has defeated " + enemy.getName());
                break;
            }
            
            if (player.getCurrentLife() == 0) {
            	console.println(enemy.getName() + " has defeated " + player.getName());
                break;
            }
        }
        context.changeState(GameStateContext.MAIN_MENU);
        console.getInt("Press 1 to go back to main menu", 1, 1);
	}
}
