package ch.andefgassm.adventuregame.game.state;

import java.util.List;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.ai.BlackDragonAI;


public class CombatState extends AbstractConsoleGameState {

    private GameStateContext context = null;
    private CombatSystem system = null;
    private Combatant player = null;
    private AbstractAICombatant enemy = null;
    private CurrentCombatState state = CurrentCombatState.FIGHTING;
    
    enum CurrentCombatState {
        FIGHTING, LOST, WON, BAD_INPUT, GIVE_UP
    }

    public void init(GameStateContext context) {
        this.context = context;
        system = context.getCombatSystem();
        
        player = new Combatant(system, "Player", 500);
        List<String> skills = context.getPlayer().getSkills();
        for (String skill : skills) {
			player.addSkill(skill);
		}
        player.getBaseStats().putAll(context.getPlayer().getStats());
        player.getResources().put(Resource.ENERGY, 500);
        player.getResources().put(Resource.COMBO_POINT, 0);
        
        enemy = new BlackDragonAI(system);
        
        state = CurrentCombatState.FIGHTING;
        
        draw();
    }
    
    private void draw() {
        clear();
        println("Enemy: " + enemy.getCurrentLife() + " life");
        println("Player: " + player.getCurrentLife() + " life");
        print("Player resources: ");
        println(player.getResources().toString());
        
        switch(state) {
        case FIGHTING:
        case BAD_INPUT:
            List<String> skills = player.getAvailableSkills();
            if (skills.size() == 0) {
                println("Player has no resources left.");
            }
            println("[0] give up");
            
            for(int i = 0; i < skills.size(); i++) {
                Skill skill = system.getSkill(skills.get(i));
                println(String.format("[%d] %s", i + 1, skill.getName()));
            }
            if (state == CurrentCombatState.BAD_INPUT) {
                println("Unknown Skill");
            }
            break;
        case WON:
            println(player.getName() + " has defeated " + enemy.getName());
            println("[0] back to main menu");
            break;
        case LOST:
            println(enemy.getName() + " has defeated " + player.getName());
            println("[0] back to main menu");
            break;
        case GIVE_UP:
            println("You have given up!");
            println("[0] back to main menu");
            break;
        }
    }

    @Override
    public void handleInput(int input) {
        switch(state) {
        case BAD_INPUT:
        case FIGHTING:
            List<String> skills = player.getAvailableSkills();
            if (input == 0) {
                state = CurrentCombatState.GIVE_UP;
                break;
            } else if (input < 0 || input > skills.size()) {
                state = CurrentCombatState.BAD_INPUT;
                break;
            } else {
                state = CurrentCombatState.FIGHTING;
            }
            
            // player's turn
            String skill = skills.get(input - 1);
            if (system.getSkill(skill).isHarmful()) {
                player.cast(skill, enemy);
            } else {
                player.cast(skill, player);
            }
            player.applyHelpfulEffects();
            enemy.applyHarmfulEffects();
            if (enemy.getCurrentLife() == 0) {
                state = CurrentCombatState.WON;
                break;
            }
            
            // enemy's turn
            skill = enemy.getNextSkill();
            if (system.getSkill(skill).isHarmful()) {
                enemy.cast(skill, player);
            } else {
                enemy.cast(skill, enemy);
            }
            enemy.applyHelpfulEffects();
            player.applyHarmfulEffects();
    
            if (player.getCurrentLife() == 0) {
                state = CurrentCombatState.LOST;
            }
            break;
        case WON:
        case LOST:
        case GIVE_UP:
            if (input == 0) {
                context.changeState(GameStateContext.MAIN_MENU);
                return;
            }
            break;
        }
        draw();
    }
}
