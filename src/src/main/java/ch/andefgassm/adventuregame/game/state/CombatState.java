package ch.andefgassm.adventuregame.game.state;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.andefgassm.adventuregame.combat.AbstractAICombatant;
import ch.andefgassm.adventuregame.combat.CombatSystem;
import ch.andefgassm.adventuregame.combat.Combatant;
import ch.andefgassm.adventuregame.combat.Skill;
import ch.andefgassm.adventuregame.game.AdventureGameException;
import ch.andefgassm.adventuregame.game.Enemy;
import ch.andefgassm.adventuregame.game.Enemy.Drop;
import ch.andefgassm.adventuregame.game.Resource;
import ch.andefgassm.adventuregame.game.assets.Graphics;
import ch.andefgassm.adventuregame.game.inventory.Item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class CombatState extends AbstractGameState {

    private GameStateContext context = null;
    private CombatSystem system = null;
    private Combatant player = null;
    private AbstractAICombatant enemy = null;
    private CurrentCombatState state = null;
    private Enemy baseEnemy = null;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private BitmapFont font = Graphics.getFont();
	private BitmapFont boldFont = Graphics.getBoldFont();
	private SpriteBatch batch = new SpriteBatch();

	private List<Skill> allSkills = null;
	private List<Skill> availableSkills = null;
	private int selectedSkill = 0;
	private StringBuilder combatText = null;

	private int width;
	private int height;

	private static final int LINE_HEIGHT = 20;
	private static final int PADDING = 5;
	private static final int PADDING_TOP = 25;

	private static final int HEALTH_HEIGHT = 30;
	private static final int HEALTH_WIDTH = 420;

	private static final int COMBO_RADIUS = 15;

	private static final int SPELL_WIDTH = 600;
	private static final int SPELL_ICON_SIZE = 64;

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

        loadSkills();

        state = CurrentCombatState.FIGHTING;
        combatText = new StringBuilder();
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

		// render titles
		batch.begin();
		boldFont.setColor(Color.BLACK);
		boldFont.draw(batch, "Du", PADDING, height - PADDING);
		boldFont.draw(batch, baseEnemy.getName(), offsetRight + PADDING, height - PADDING);
		batch.end();

		renderPlayer(PADDING, height - PADDING_TOP);

		renderSkills(PADDING, height - PADDING_TOP - 100);

		renderEnemy(offsetRight + PADDING, height - PADDING_TOP);

		renderCombatText();

		renderTutorial();
	}

    // render portions of the screen
	private void renderPlayer(int x, int y) {
		batch.begin();
		font.setColor(Color.BLACK);
		font.draw(batch, "Gesundheit:", x, y - PADDING);
		font.draw(batch, "Combo Punkte:", x, y - HEALTH_HEIGHT - PADDING*3);
		batch.end();

		renderHealth(x + 180, y, player.getMaxLife(), player.getCurrentLife());
		renderComboPoints(x + 180, y - HEALTH_HEIGHT - PADDING*2, 3, player.getResources().get(Resource.COMBO_POINT));
	}

	private void renderSkills(int x, int y) {
		int i = 0;
		for (Skill skill : allSkills) {
			boolean selected = i == selectedSkill;
			boolean available = availableSkills.contains(skill);
			renderSkill(skill, x, y - (i * (SPELL_ICON_SIZE + PADDING)), available, selected);
			i++;
		}
	}


	private void renderEnemy(int x, int y) {
		batch.begin();
		font.setColor(Color.BLACK);
		font.draw(batch, "Gesundheit:", x, y - PADDING);
		batch.end();

		renderHealth(x + 180, y, enemy.getMaxLife(), enemy.getCurrentLife());
	}


	private void renderCombatText() {
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

	// render single elements

	private void renderHealth(int x, int y, int max, int current) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.rect(x, y - HEALTH_HEIGHT, HEALTH_WIDTH, HEALTH_HEIGHT);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(x, y - HEALTH_HEIGHT, (float)HEALTH_WIDTH / max * current, HEALTH_HEIGHT);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(x, y - HEALTH_HEIGHT, HEALTH_WIDTH, HEALTH_HEIGHT);
		shapeRenderer.end();

		String health = String.format("%d/%d", current, max);
		TextBounds bounds = font.getBounds(health);

		batch.begin();
		font.setColor(Color.BLACK);
		font.draw(batch, health, x + (HEALTH_WIDTH - bounds.width) / 2, y - (HEALTH_HEIGHT - bounds.height) / 2);
		batch.end();
	}

	private void renderComboPoints(int x, int y, int max, int current) {
		for (int i = 0; i < max; i++) {
			shapeRenderer.begin(ShapeType.Filled);
			if (i < current) {
				shapeRenderer.setColor(Color.YELLOW);
			} else {
				shapeRenderer.setColor(Color.WHITE);
			}
			shapeRenderer.circle(x + i*(COMBO_RADIUS*2 + PADDING) + COMBO_RADIUS, y - COMBO_RADIUS, COMBO_RADIUS);
			shapeRenderer.end();


			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.circle(x + i*(COMBO_RADIUS*2 + PADDING) + COMBO_RADIUS, y - COMBO_RADIUS, COMBO_RADIUS);
			shapeRenderer.end();
		}
	}

	private void renderSkill(Skill skill, int x, int y, boolean available, boolean selected) {
		shapeRenderer.begin(ShapeType.Filled);
		if (selected) {
			shapeRenderer.setColor(Color.LIGHT_GRAY);
		} else if (available){
			shapeRenderer.setColor(Color.WHITE);
		} else {
			shapeRenderer.setColor(Color.DARK_GRAY);
		}
		shapeRenderer.rect(x, y - SPELL_ICON_SIZE, SPELL_WIDTH, 64);
		shapeRenderer.end();

		batch.begin();
		batch.draw(Graphics.getTexture(skill.getIcon()), x - 1, y - SPELL_ICON_SIZE);
		batch.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(x, y - SPELL_ICON_SIZE, SPELL_WIDTH, 64);
		shapeRenderer.line(x + SPELL_ICON_SIZE, y, x + SPELL_ICON_SIZE, y - SPELL_ICON_SIZE);
		shapeRenderer.end();

		int textX = x + SPELL_ICON_SIZE + PADDING;
		int textY = y - PADDING;

		batch.begin();
		font.setColor(Color.BLACK);
		boldFont.setColor(Color.BLACK);
		boldFont.draw(batch, skill.getName(), textX, textY);
		font.draw(batch, skill.getRequiredResources().toString(), textX, textY - LINE_HEIGHT);
		font.draw(batch, skill.getDescription(), textX, textY - LINE_HEIGHT*2);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	private void loadSkills() {
		allSkills = new ArrayList<Skill>();
		List<String> allSkillIds = context.getPlayer().getSkills();
		for (String skillId : allSkillIds) {
			allSkills.add(system.getSkill(skillId));
		}

		availableSkills = new ArrayList<Skill>();
		List<String> availableSkillIds = player.getAvailableSkills();
		for (String skillId : availableSkillIds) {
			availableSkills.add(system.getSkill(skillId));
		}

		if (!availableSkillIds.contains(allSkillIds.get(selectedSkill))) {
			selectedSkill = 0;
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.E:
			context.changeState(GameStateContext.MAP);
			return true;
		case Keys.Q:
			context.changeState(null);
			return true;
		}

		if (state == CurrentCombatState.FIGHTING) {
			switch (keycode) {
			case Keys.UP:
				/*
				 * if (selectedItem == 0 && currentItems.size() != 0) {
				 * selectedItem = currentItems.size() - 1; } else {
				 * selectedItem--; }
				 */
				break;
			case Keys.DOWN:
				/*
				 * if (selectedItem == currentItems.size() - 1 ||
				 * currentItems.size() == 0) { selectedItem = 0; } else {
				 * selectedItem++; }
				 */
				break;
			case Keys.ENTER:
				castSkill();
				break;
			}
		}
		return true;
	}


	private void castSkill() {
		Skill skill = allSkills.get(selectedSkill);
		if (skill.isHarmful()) {
            player.cast(skill.getId(), enemy);
        } else {
            player.cast(skill.getId(), player);
        }
        player.applyHelpfulEffects();
        enemy.applyHarmfulEffects();

        if (enemy.getCurrentLife() == 0) {
            state = CurrentCombatState.WON;
            combatText.append("Du hast " + enemy.getName() + " besiegt!\n");

            Random r = new Random();
            List<Drop> drops = baseEnemy.getDrops();
            for (Drop drop : drops) {
                float rnd = r.nextFloat();
                if (rnd <= drop.getDropRate()) {
                    Item item = context.getItem(drop.getItemId());
                    if (!context.getPlayer().getInventory().contains(item.getId())) {
                    	combatText.append("Du hast ein Gegenstand gewwonnen: " + item.getName() + "\n");
                        context.getPlayer().getInventory().add(item.getId());
                    }
                }
            }
        }

        // enemy's turn
        String skillId = enemy.getNextSkill();
        if (system.getSkill(skillId).isHarmful()) {
            enemy.cast(skillId, player);
        } else {
            enemy.cast(skillId, enemy);
        }
        enemy.applyHelpfulEffects();
        player.applyHarmfulEffects();

        if (player.getCurrentLife() == 0) {
            state = CurrentCombatState.LOST;
            combatText.append(enemy.getName() + " hat dich besiegt!\n");
        }
        loadSkills();
	}
}
