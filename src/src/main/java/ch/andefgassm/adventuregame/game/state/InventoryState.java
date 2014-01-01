package ch.andefgassm.adventuregame.game.state;

import java.util.ArrayList;
import java.util.List;

import ch.andefgassm.adventuregame.game.Stat;
import ch.andefgassm.adventuregame.game.assets.Graphics;
import ch.andefgassm.adventuregame.game.inventory.Item;
import ch.andefgassm.adventuregame.game.inventory.ItemType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class InventoryState extends AbstractGameState {

	private GameStateContext context = null;
	private int currentType = 0;
	private int selectedItem = 0;
	private List<Item> currentItems = null;


	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private BitmapFont font = Graphics.getFont();
	private BitmapFont boldFont = Graphics.getBoldFont();
	private SpriteBatch batch = new SpriteBatch();
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

	@Override
	public void init(GameStateContext context, String param) {
		this.context = context;
		reloadItems();
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
		boldFont.draw(batch, "Angelegte Ausrüstung", PADDING, height - PADDING);
		boldFont.draw(batch, "Gegenstände im Inventar", offsetRight + PADDING, height - PADDING);
		batch.end();

		// render equipped items
		drawEquippedItems();

		// render inventory
		drawInventorySelectors(offsetRight + PADDING, height - PADDING_TOP);
		drawInventory(offsetRight + PADDING, height - PADDING_TOP -  SELECTOR_HEIGHT - PADDING);

		// render tutorial
		drawTutorial();
	}

	private void drawItem(Item item, int x, int y) {
		drawItem(item, x, y, false);
	}

	private void drawItem(Item item, int x, int y, boolean selected) {
		shapeRenderer.begin(ShapeType.Filled);
		if (selected) {
			shapeRenderer.setColor(Color.LIGHT_GRAY);
		} else {
			shapeRenderer.setColor(Color.WHITE);
		}
		shapeRenderer.rect(x, y - ITEM_ICON_SIZE, ITEM_WIDTH, 64);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(x, y - ITEM_ICON_SIZE, ITEM_WIDTH, 64);
		shapeRenderer.line(x + ITEM_ICON_SIZE, y, x + ITEM_ICON_SIZE, y - ITEM_ICON_SIZE);
		shapeRenderer.end();

		batch.begin();
		batch.draw(Graphics.getTexture(item.getIcon()), x - 1, y - ITEM_ICON_SIZE);

		int textX = x + ITEM_ICON_SIZE + PADDING;
		int textY = y - PADDING;

		font.setColor(Color.BLACK);
		boldFont.setColor(Color.BLACK);

		boldFont.draw(batch, item.getName(), textX, textY);

		font.draw(batch, String.format("Leben:   %d", item.getStat(Stat.LIFE)), textX, textY - LINE_HEIGHT);
		font.draw(batch, String.format("Rüstung: %d", item.getStat(Stat.ARMOR)), textX, textY - LINE_HEIGHT*2);

		font.draw(batch, String.format("Schaden: %d", item.getStat(Stat.DAMAGE)), textX + ITEM_TAB_SIZE, textY - LINE_HEIGHT);
		font.draw(batch, String.format("Heilung: %d", item.getStat(Stat.HEAL)), textX + ITEM_TAB_SIZE, textY - LINE_HEIGHT*2);

		font.draw(batch, "Magie-", textX + ITEM_TAB_SIZE*2, textY - LINE_HEIGHT);
		font.draw(batch, String.format("Wiederstand: %d", item.getStat(Stat.MAGIC_RESISTANCE)), textX + ITEM_TAB_SIZE*2, textY - LINE_HEIGHT*2);

		batch.end();
	}

	private void drawEquippedItems() {
		List<String> equippedItems = new ArrayList<String>();
		String weapon = context.getPlayer().getEquippedItem(ItemType.WEAPON);
		if (weapon != null) {
			equippedItems.add(weapon);
		}
		String chest = context.getPlayer().getEquippedItem(ItemType.CHEST);
		if (chest != null) {
			equippedItems.add(chest);
		}
		String hands = context.getPlayer().getEquippedItem(ItemType.HANDS);
		if (hands != null) {
			equippedItems.add(hands);
		}
		String legs = context.getPlayer().getEquippedItem(ItemType.LEGS);
		if (legs != null) {
			equippedItems.add(legs);
		}
		String feet = context.getPlayer().getEquippedItem(ItemType.FEET);
		if (feet != null) {
			equippedItems.add(feet);
		}

		int i = 0;
		for (String itemId : equippedItems) {
			Item item = context.getItem(itemId);
			drawItem(item, PADDING, height - PADDING_TOP - (i * (ITEM_ICON_SIZE + PADDING)));
			i++;
		};
	}

	private void drawInventorySelectors(int x, int y) {
		int i = 0;
		for (ItemType itemType : ItemType.values()) {
			// filling
			shapeRenderer.begin(ShapeType.Filled);
			if (itemType == ItemType.values()[currentType]) {
				shapeRenderer.setColor(Color.LIGHT_GRAY);
			} else {
				shapeRenderer.setColor(Color.WHITE);
			}
			shapeRenderer.rect(x + i*(SELECTOR_WIDTH + PADDING), y - SELECTOR_HEIGHT, SELECTOR_WIDTH, SELECTOR_HEIGHT);
			shapeRenderer.end();

			// border
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.rect(x + i*(SELECTOR_WIDTH + PADDING), y - SELECTOR_HEIGHT, SELECTOR_WIDTH, SELECTOR_HEIGHT);
			shapeRenderer.end();

			// text
			String name = "";
			if (itemType.name().equals("WEAPON")) {
				name = "Waffen";
			} else if (itemType.name().equals("HANDS")) {
				name = "Handschuhe";
			} if (itemType.name().equals("CHEST")) {
				name = "Brust";
			} if (itemType.name().equals("LEGS")) {
				name = "Beine";
			} if (itemType.name().equals("FEET")) {
				name = "Füsse";
			}

			batch.begin();
			font.draw(batch, name, x + i*(SELECTOR_WIDTH + PADDING) + PADDING, y - PADDING - 3);
			batch.end();

			i++;
		}
	}

	private void drawInventory(int x, int y) {
		int i = 0;
		for (String itemId : context.getPlayer().getInventory()) {
			Item item = context.getItem(itemId);
			if (item.getType() == ItemType.values()[currentType]) {
				drawItem(item, x, y - i * (ITEM_ICON_SIZE + PADDING), i == selectedItem);
				i++;
			}
		}
	}

	private void drawTutorial() {
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
		font.draw(batch, "[←], [→] Gegenstands-Typ wechseln", PADDING, HUD_HEIGHT - PADDING);
		font.draw(batch, "[↑], [↓] Gegenstand auswählen", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT);
		font.draw(batch, "[Enter] Gegenstand ausrüsten", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT*2);
		font.draw(batch, "[i] Inventar schliessen", PADDING, HUD_HEIGHT - PADDING - LINE_HEIGHT*3);
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
			if (selectedItem == 0 && currentItems.size() != 0) {
				selectedItem = currentItems.size() - 1;
			} else {
				selectedItem--;
			}
			break;
		case Keys.DOWN:
			if (selectedItem == currentItems.size() - 1 || currentItems.size() == 0) {
				selectedItem = 0;
			} else {
				selectedItem++;
			}
			break;
		case Keys.LEFT:
			if (currentType == 0) {
				currentType = ItemType.values().length - 1;
			} else {
				currentType--;
			}
			reloadItems();
			break;
		case Keys.RIGHT:
			if (currentType == ItemType.values().length - 1) {
				currentType = 0;
			} else {
				currentType++;
			}
			reloadItems();
			break;
		case Keys.ENTER:
			if (currentItems.size() != 0) {
				String itemId = currentItems.get(selectedItem).getId();
				context.getPlayer().equip(itemId);
			}
			break;
		case Keys.I:
			context.changeState(GameStateContext.MAP);
			break;
		case Keys.Q:
			context.changeState(null);
			break;
		}
		return true;
	}

	private void reloadItems() {
		currentItems = new ArrayList<Item>();
		for (String itemId : context.getPlayer().getInventory()) {
			Item item = context.getItem(itemId);
			if (item.getType() ==  ItemType.values()[currentType]) {
				currentItems.add(item);
			}
		}
		selectedItem = 0;
	}
}
