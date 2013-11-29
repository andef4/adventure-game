package ch.andefgassm.adventuregame.game.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Enemy;
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
	private Map<String, Item> items = new HashMap<String, Item>();
	private Map<String, Enemy> enemies = new HashMap<String, Enemy>();
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
		
		List<Enemy> enemies = AssetLoader.getInstance().load("assets/data/enemies", Enemy.class);
		for (Enemy enemy : enemies) {
			this.enemies.put(enemy.getId(), enemy);
		}
        
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
		
	public CombatSystem getCombatSystem() {
		return combatSystem;
	}
	
    public Item getItem(String id) {
        return items.get(id);
    }
    
    public Enemy getEnemy(String id) {
    	return enemies.get(id);
    }

	public Player getPlayer() {
		return player;
	}

}
