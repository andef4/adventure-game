package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Effect;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Resource;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GameStateContext {
	
	public static final AbstractGameState COMBAT_MENU = new CombatMenuState();
	public static final AbstractGameState MAIN_MENU = new MainMenuState();
	public static final AbstractGameState INVENTORY_MENU = new InventoryMenuState();
	public static final AbstractGameState COMBAT = new CombatState();
	
	private CombatSystem combatSystem = new CombatSystem();
	private Game game = null;
	
	public GameStateContext(Game game) {
		this.game = game;
	}

	public void run() {
		initSkills();
		changeState(MAIN_MENU);
	}
	
	public void changeState(AbstractGameState newState) {
		if (newState == null) {
			Gdx.app.exit();
		}
		newState.init(this);
		game.setScreen(newState);
		Gdx.input.setInputProcessor(newState);
	}
	
	private void initSkills() {
		Skill strikeSkill = new Skill("Strike");
        strikeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        Effect strikeDamageEffect = new Effect();
        strikeDamageEffect.setBaseLifeChange(-50);
        strikeSkill.getTargetEffects().add(strikeDamageEffect);
        Effect strikeComboPointEffect = new Effect();
        strikeComboPointEffect.getResourceChanges().put(Resource.COMBO_POINT, 1);
        strikeSkill.getCasterEffects().add(strikeComboPointEffect);
        combatSystem.registerSkill("warrior_strike", strikeSkill);
        
        Skill executeSkill = new Skill("Execute");
        executeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        executeSkill.getRequiredResources().put(Resource.COMBO_POINT, 3);
        Effect executeEffect = new Effect();
        executeEffect.setBaseLifeChange(-100);
        executeSkill.getTargetEffects().add(executeEffect);
        combatSystem.registerSkill("warrior_execute", executeSkill);
        
        Skill breathSkill = new Skill("Breath");
        Effect breathEffect = new Effect();
        breathEffect.setBaseLifeChange(-70);
        breathSkill.getTargetEffects().add(breathEffect);
        combatSystem.registerSkill("drake_breath", breathSkill);
	}
		
	public CombatSystem getCombatSystem() {
		return combatSystem;
	}

}
