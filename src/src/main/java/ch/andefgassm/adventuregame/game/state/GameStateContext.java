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
import ch.andefgassm.adventuregame.game.spellmodifiers.FireSpellModifier;
import ch.andefgassm.adventuregame.game.stats.MagicResistanceProcessor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GameStateContext {
	public static final AbstractGameState INVENTORY = new InventoryState();
	public static final AbstractGameState COMBAT = new CombatState();
	public static final AbstractGameState MAP = new MapState();

	private Game game = null;

	private CombatSystem combatSystem = new CombatSystem();
	private Map<String, Item> items = new HashMap<String, Item>();
	private Map<String, Enemy> enemies = new HashMap<String, Enemy>();
	private Player player = new Player(this);


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

		combatSystem.getStatProcessors().add(new MagicResistanceProcessor());
		combatSystem.getSpellModifiers().put("fire", new FireSpellModifier());

        player.getSkills().add("player_strike");
        player.getSkills().add("player_execute");

        player.addItem("weapon1");
        player.addItem("weapon2");
        player.addItem("weapon3");
        player.addItem("chest1");
        //player.addItem("legs1");
        player.addItem("hands1");
        player.addItem("feet1");

        player.equip("weapon1");
        //player.equip("chest1");
        player.equip("hands1");
        //player.equip("legs1");
        player.equip("feet1");

		changeState(new CombatState(), "black_dragon");
	}

	public void changeState(AbstractGameState newState) {
		changeState(newState, null);
	}

	public void changeState(AbstractGameState newState, String param) {
		if (newState == null) {
			Gdx.app.exit();
			return;
		}
		newState.init(this, param);
		game.setScreen(newState);
		Gdx.input.setInputProcessor(newState.getInputProcessor());
	}

	public CombatSystem getCombatSystem() {
		return combatSystem;
	}

    public Item getItem(String itemId) {
    	Item item = items.get(itemId);
		if (item == null) {
			throw new IllegalArgumentException(String.format("Item with ID %s does not exist.", itemId));
		}
		return item;
    }

    public Enemy getEnemy(String id) {
    	return enemies.get(id);
    }

	public Player getPlayer() {
		return player;
	}

	public Map<String, Enemy> getEnemies() {
		return enemies;
	}
}
