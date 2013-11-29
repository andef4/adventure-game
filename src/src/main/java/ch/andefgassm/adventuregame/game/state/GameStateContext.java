package ch.andefgassm.adventuregame.game.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.assets.AssetLoader;
import ch.andefgassm.adventuregame.game.inventory.Item;
import ch.andefgassm.adventuregame.game.inventory.Player;
import ch.andefgassm.adventuregame.game.skills.FireResistanceProcessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GameStateContext {
	
	public static final AbstractGameState COMBAT_MENU = new CombatMenuState();
	public static final AbstractGameState MAIN_MENU = new MainMenuState();
	public static final AbstractGameState INVENTORY_MENU = new InventoryMenuState();
	public static final AbstractGameState COMBAT = new CombatState();
	
	private Game game = null;
	
	private CombatSystem combatSystem = new CombatSystem();
	private Player player = new Player();
	
	public GameStateContext(Game game) {
		this.game = game;
	}

	public void init() {
		List<Item> items = AssetLoader.getInstance().load("assets/data/items", Item.class);
		for (Item item : items) {
			this.items.put(item.getId(), item);
		}
		
		List<Skill> skills = AssetLoader.getInstance().load("assets/data/skills", Skill.class);
		for (Skill skill : skills) {
			combatSystem.registerSkill(skill.getId(), skill);
		}
		
		// TODO: add enemy loader
		
        player.getInventory().add(getItem("Greatsword of the Red Dragon"));
        player.getInventory().add(getItem("Donnerbalken"));
        
		combatSystem.getStatProcessors().add(new FireResistanceProcessor());
		changeState(MAIN_MENU);
	}
	
	public void changeState(AbstractGameState newState) {
		if (newState == null) {
			Gdx.app.exit();
			return;
		}
		newState.init(this);
		game.setScreen(newState);
		Gdx.input.setInputProcessor(newState);
	}
	
	/*
	private void initSkills() {
		Skill strikeSkill = new Skill("player_strike", "Strike", true);
        strikeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        Effect strikeDamageEffect = new Effect();
        strikeDamageEffect.setBaseLifeChange(-50);
        strikeDamageEffect.getStatScaling().put(Stat.STRENGTH, -1.0f);
        strikeSkill.getTargetEffects().add(strikeDamageEffect);
        Effect strikeComboPointEffect = new Effect();
        strikeComboPointEffect.getResourceChanges().put(Resource.COMBO_POINT, 1);
        strikeSkill.getCasterEffects().add(strikeComboPointEffect);
        combatSystem.registerSkill("warrior_strike", strikeSkill);
        
        Skill executeSkill = new Skill("player_execute", "Execute", true);
        executeSkill.getRequiredResources().put(Resource.ENERGY, 20);
        executeSkill.getRequiredResources().put(Resource.COMBO_POINT, 3);
        Effect executeEffect = new Effect();
        executeEffect.setBaseLifeChange(-100);
        executeSkill.getTargetEffects().add(executeEffect);
        combatSystem.registerSkill("warrior_execute", executeSkill);
        
        Skill breathSkill = new Skill("black_dragon_breath", "Breath", true);
        Effect breathEffect = new Effect();
        breathEffect.setBaseLifeChange(-70);
        breathEffect.setDamageType(DamageType.FIRE);
        breathSkill.getTargetEffects().add(breathEffect);
        combatSystem.registerSkill("drake_breath", breathSkill);
	}
	*/
		
	public CombatSystem getCombatSystem() {
		return combatSystem;
	}
	
    private Map<String, Item> items = new HashMap<String, Item>();
    
    public Item getItem(String item) {
        return items.get(item);
    }

	public Player getPlayer() {
		return player;
	}

}
