package ch.andefgassm.adventuregame.game.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.Enemy;
import ch.andefgassm.adventuregame.game.MagicResistanceProcessor;
import ch.andefgassm.adventuregame.game.Savegame;
import ch.andefgassm.adventuregame.game.assets.AssetLoader;
import ch.andefgassm.adventuregame.game.inventory.Item;
import ch.andefgassm.adventuregame.game.inventory.Player;
import ch.andefgassm.adventuregame.game.spellmodifiers.AuraEnhanceDamage;
import ch.andefgassm.adventuregame.game.spellmodifiers.AuraReduceDamage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class GameStateContext {
    public static final AbstractGameState INVENTORY = new InventoryState();
    public static final AbstractGameState COMBAT = new CombatState();
    public static final MapState MAP = new MapState();

    private Game game = null;

    private CombatSystem combatSystem = new CombatSystem();
    private Map<String, Item> items = new HashMap<String, Item>();
    private Map<String, Enemy> enemies = new HashMap<String, Enemy>();
    private Player player = new Player(this);
    private List<String> livingBosses = new ArrayList<String>();

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
        combatSystem.getSpellModifiers().put("aura_reduce_damage", new AuraReduceDamage());
        combatSystem.getSpellModifiers().put("aura_enhance_damage", new AuraEnhanceDamage());

        player.getSkills().add("instant_damage");
        player.getSkills().add("instant_damage_combo");
        player.getSkills().add("dot");
        player.getSkills().add("heal");
        player.getSkills().add("aura_enhance_damage");
        player.getSkills().add("aura_reduce_damage.");

        loadSavegame();

        changeState(MAP);
    }

    private void loadSavegame() {
        // load json
        Savegame savegame = Savegame.load();

        // inventory & equipped items
        player.getInventory().addAll(savegame.getInventoryItems());
        for (String itemId : savegame.getEquippedItems()) {
            player.equip(itemId);
        }

        // player position
        MAP.setPlayerPosition(savegame.getPositionX(), savegame.getPositionY());


        // killed bosses
        for (Enemy enemy : enemies.values()) {
            if (enemy.isBoss() && !savegame.getKilledBosses().contains(enemy.getId())) {
                livingBosses.add(enemy.getId());
            }
        }
    }

    public void saveSavegame() {
        Savegame savegame = new Savegame();

        // inventory & equipped items
        savegame.getInventoryItems().addAll(player.getInventory());
        savegame.getEquippedItems().addAll(player.getEquipment());

        // player position
        savegame.setPositionX(MAP.getPlayerPositionX());
        savegame.setPositionY(MAP.getPlayerPositionY());

        // killed bosses
        for (Enemy enemy : enemies.values()) {
            if (enemy.isBoss() && !livingBosses.contains(enemy.getId())) {
                savegame.getKilledBosses().add(enemy.getId());
            }
        }

        Savegame.save(savegame);
    }


    public void changeState(AbstractGameState newState) {
        changeState(newState, null);
    }

    public void changeState(AbstractGameState newState, String param) {
        if (newState == null) {
            saveSavegame();
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
    public List<String> getLivingBosses() {
        return livingBosses;
    }
}
