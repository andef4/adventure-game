package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.ui.Console;
import ch.andefgassm.adventuregame.game.ui.LanternaConsole;

public class GameStateContext {
	
	public static final IGameState COMBAT_MENU = new CombatMenuState();
	public static final IGameState MAIN_MENU = new MainMenuState();
	public static final IGameState INVENTORY_MENU = new InventoryMenuState();
	public static final IGameState COMBAT = new CombatState();
	
	private IGameState currentState;
	private Console console = new LanternaConsole();
	private CombatSystem combatSystem = new CombatSystem();
	
	public void run() {
		
		initSkills();
		
		currentState = MAIN_MENU;
		
		while (true) {
			if (currentState == null) {
				console.close();
				return;
			}
			currentState.handle(this);
		}
	}
	
	public void changeState(IGameState newState) {
		currentState = newState;
	}
	
	private void initSkills() {
		Skill strikeSkill = new Skill("Strike");
        strikeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        Effect strikeDamageEffect = new Effect();
        strikeDamageEffect.setLifeChange(-50);
        strikeSkill.getTargetEffects().add(strikeDamageEffect);
        Effect strikeComboPointEffect = new Effect();
        strikeComboPointEffect.getResourceChanges().put(Resource.COMBO_POINT, 1);
        strikeSkill.getCasterEffects().add(strikeComboPointEffect);
        combatSystem.registerSkill("warrior_strike", strikeSkill);
        
        Skill executeSkill = new Skill("Execute");
        executeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        executeSkill.getRequiredResources().put(Resource.COMBO_POINT, 3);
        Effect executeEffect = new Effect();
        executeEffect.setLifeChange(-100);
        executeSkill.getTargetEffects().add(executeEffect);
        combatSystem.registerSkill("warrior_execute", executeSkill);
        
        Skill breathSkill = new Skill("Breath");
        Effect breathEffect = new Effect();
        breathEffect.setLifeChange(-70);
        breathSkill.getTargetEffects().add(breathEffect);
        combatSystem.registerSkill("drake_breath", breathSkill);
	}
	
	public Console getConsole() {
		return console;
	}
	
	public CombatSystem getCombatSystem() {
		return combatSystem;
	}

}
