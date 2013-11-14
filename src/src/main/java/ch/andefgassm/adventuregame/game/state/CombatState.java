package ch.andefgassm.adventuregame.game.state;

import java.util.List;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.ai.BlackDragonAI;


public class CombatState extends AbstractConsoleGameState {

	private GameStateContext context;

	public void init(GameStateContext context) {
		this.context = context;
		CombatSystem system = context.getCombatSystem();
		
        Combatant player = new Combatant(system, "Player", 500);
        player.addSkill("warrior_strike");
        player.addSkill("warrior_execute");
        player.getResources().put(Resource.ENERGY, 500);
        player.getResources().put(Resource.COMBO_POINT, 0);
        
        BlackDragonAI enemy = new BlackDragonAI(system);
        
        while(true) {
        	clear();
        	println("Enemy: " + enemy.getCurrentLife() + " life");
        	println("Player: " + player.getCurrentLife() + " life");
        	print("Player resources: ");
        	println(player.getResources().toString());
        	
            List<String> skills = player.getAvailableSkills();
            if (skills.size() == 0) {
                println("Player has no resources left.");
            }
            
            for(int i = 0; i < skills.size(); i++) {
                Skill skill = system.getSkill(skills.get(i));
                println(String.format("[%d] %s", i + 1, skill.getName()));
            }
            //int i = getInt("Which skill do you use?", 1, skills.size()) - 1;
            
            //player.cast(skills.get(i), enemy); // add effects to player and enemy
            enemy.applyEffects(); // calculate stats
            enemy.cast(enemy.getNextSkill(), player); // add effects to enemy and player
            player.applyEffects();  // calculate stats
            
            if (enemy.getCurrentLife() == 0) {
                println(player.getName() + " has defeated " + enemy.getName());
                break;
            }
            
            if (player.getCurrentLife() == 0) {
            	println(enemy.getName() + " has defeated " + player.getName());
                break;
            }
        }
        println("Press 1 to go back to main menu");
	}

	@Override
	public void handleInput(int input) {
		context.changeState(GameStateContext.MAIN_MENU);
	}

}
