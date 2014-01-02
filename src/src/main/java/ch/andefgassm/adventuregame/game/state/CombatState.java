package ch.andefgassm.adventuregame.game.state;

import java.lang.reflect.Constructor;
import java.util.List;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.AdventureGameException;
import ch.andefgassm.adventuregame.game.Enemy;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.assets.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class CombatState extends AbstractGameState {

    private GameStateContext context = null;
    private CombatSystem system = null;
    private Combatant player = null;
    private AbstractAICombatant enemy = null;
    private CurrentCombatState state = CurrentCombatState.FIGHTING;
    private Enemy baseEnemy;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private BitmapFont font = Graphics.getFont();
	private BitmapFont boldFont = Graphics.getBoldFont();
	private SpriteBatch batch = new SpriteBatch();

	private int currentSkill = 0;

	private int width;
	private int height;

	private static final int LINE_HEIGHT = 20;
	private static final int PADDING = 5;
	private static final int PADDING_TOP = 25;

	private static final int ITEM_WIDTH = 600;
	private static final int ITEM_TAB_SIZE = 150;
	private static final int ITEM_ICON_SIZE = 64;

	private static final int SELECTOR_WIDTH = 120;
	private static final int SELECTOR_HEIGHT = 30;

	private static final int HUD_HEIGHT = 150;

    enum CurrentCombatState {
        FIGHTING, LOST, WON, BAD_INPUT, GIVE_UP
    }

    public void init(GameStateContext context, String enemyId) {
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

        baseEnemy = context.getEnemy(enemyId);

        try {
            Class<?> aiClass = Class.forName(baseEnemy.getAiClass());
            Constructor<?> constructor = aiClass.getConstructor(CombatSystem.class, String.class, Integer.TYPE);
            enemy = (AbstractAICombatant) constructor.newInstance(context.getCombatSystem(), baseEnemy.getName(), baseEnemy.getLife());
        } catch (Exception e) {
            throw new AdventureGameException("Can't load enemy ai class", e);
        }

        enemy.getBaseStats().putAll(baseEnemy.getStats());

        state = CurrentCombatState.FIGHTING;
    }


    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(255f/255, 200f/255, 120f/255, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		int offsetRight = width/2;

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(offsetRight, 0, offsetRight, height);
		shapeRenderer.end();

		renderSkills();

		renderEnemy();

		renderCombatText();

		// render tutorial
		renderTutorial();
	}

	private void renderSkills() {
		context.getPlayer().getSkills();
		player.getAvailableSkills();
	}

	private void renderSkill(Skill skill, int x, int y, boolean available) {

	}


	private void renderCombatText() {

	}


	private void renderEnemy() {

	}





	private void renderTutorial() {
		// render hud rectangle
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(new Color(255f/255, 200f/255, 120f/255, 1f));
		shapeRenderer.rect(0, 0, width, 150);
		shapeRenderer.end();
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(0, HUD_HEIGHT, width, HUD_HEIGHT);
		shapeRenderer.end();

		// render hud text
		batch.begin();
		font.setColor(Color.BLACK);
		font.draw(batch, "[↑], [↓] Zauber auswählen", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT);
		font.draw(batch, "[Enter] Zauber ausführen", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT*2);
		font.draw(batch, "[e] Fliehen", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT*3);
		font.draw(batch, "[q] Spiel beenden", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT*4);
		batch.end();
	}


	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.UP:
			/*if (selectedItem == 0 && currentItems.size() != 0) {
				selectedItem = currentItems.size() - 1;
			} else {
				selectedItem--;
			}/*
			break;
		case Keys.DOWN:
			/*if (selectedItem == currentItems.size() - 1 || currentItems.size() == 0) {
				selectedItem = 0;
			} else {
				selectedItem++;
			}*/
			break;
		case Keys.ENTER:
			/*if (currentItems.size() != 0) {
				String itemId = currentItems.get(selectedItem).getId();
				context.getPlayer().equip(itemId);
			}*/
			break;
		case Keys.E:
			context.changeState(GameStateContext.MAP);
		case Keys.Q:
			context.changeState(null);
			break;
		}
		return true;
	}





    /*private void draw() {
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

            // drop items
            Random r = new Random();
            List<Drop> drops = baseEnemy.getDrops();
            for (Drop drop : drops) {
                float rnd = r.nextFloat();
                if (rnd <= drop.getDropRate()) {
                    Item item = context.getItem(drop.getItemId());
                    if (!context.getPlayer().getInventory().contains(item.getId())) {
                        println("You have won an item: " + item.getName());
                        context.getPlayer().getInventory().add(item.getId());
                    }
                }
            }
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
    }*/
/*
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
                context.changeState(GameStateContext.MAP);
                return;
            }
            break;
        }
        draw();
    }
    */
}
