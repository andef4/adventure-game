package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.Stat;
import ch.andefgassm.adventuregame.game.TextureCache;
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
	private ItemType currentType = ItemType.WEAPON;
	private int currentItem = 0;

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private BitmapFont font = new BitmapFont();
	private SpriteBatch batch = new SpriteBatch();
	private int width;
	private int height;

	private static final int LINE_HEIGHT = 20;
	private static final int PADDING = 5;
	private static final int PADDING_TOP = 25;

	private static final int ITEM_TAB_SIZE = 100;
	private static final int ITEM_ICON_SIZE = 64;

	private static final int SELECTOR_WIDTH = 100;
	private static final int SELECTOR_HEIGHT = 30;

	@Override
	public void init(GameStateContext context, String param) {
		this.context = context;
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

		// render equipped items
		batch.begin();
		font.setColor(Color.BLACK);
		font.draw(batch, "Angelegte Ausrüstung", PADDING, height - PADDING);
		font.draw(batch, "Gegenstände im Inventar", offsetRight + PADDING, height - PADDING);
		batch.end();

		int i = 0;
		for (String itemId : context.getPlayer().getEquipment()) {
			Item item = context.getItem(itemId);
			drawItem(item, PADDING, height - PADDING_TOP - (i * (ITEM_ICON_SIZE + PADDING)));
			i++;
		};

		// render inventory
		drawInventorySelectors(offsetRight + PADDING, height - PADDING_TOP);
		drawInventory(offsetRight + PADDING, height - PADDING_TOP -  SELECTOR_HEIGHT - PADDING);
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
		shapeRenderer.rect(x, y - ITEM_ICON_SIZE, 500, 64);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(x, y - ITEM_ICON_SIZE, 500, 64);
		shapeRenderer.line(x + ITEM_ICON_SIZE, y, x + ITEM_ICON_SIZE, y - ITEM_ICON_SIZE);
		shapeRenderer.end();

		batch.begin();
		batch.draw(TextureCache.getTexture(item.getIcon()), x - 1, y - ITEM_ICON_SIZE);

		int textX = x + ITEM_ICON_SIZE + PADDING;
		int textY = y - PADDING;

		font.draw(batch, item.getName(), textX, textY);

		font.draw(batch, "Leben: " + item.getStat(Stat.LIFE), textX, textY - LINE_HEIGHT);
		font.draw(batch, "Rüstung: " + item.getStat(Stat.ARMOR), textX, textY - LINE_HEIGHT*2);

		font.draw(batch, "Schaden: " + item.getStat(Stat.DAMAGE), textX + ITEM_TAB_SIZE, textY - LINE_HEIGHT);
		font.draw(batch, "Heilung: " + item.getStat(Stat.HEAL), textX + ITEM_TAB_SIZE, textY - LINE_HEIGHT*2);

		font.draw(batch, "Magie-Wiederstand: " + item.getStat(Stat.MAGIC_RESISTANCE), textX + ITEM_TAB_SIZE*2, textY - LINE_HEIGHT);

		batch.end();
	}

	private void drawInventorySelectors(int x, int y) {
		int i = 0;
		for (ItemType itemType : ItemType.values()) {
			// filling
			shapeRenderer.begin(ShapeType.Filled);
			if (itemType == currentType) {
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
			batch.begin();
			font.draw(batch, itemType.name(), x + i*(SELECTOR_WIDTH + PADDING) + PADDING, y - PADDING*2);
			batch.end();

			i++;
		}
	}

	private void drawInventory(int x, int y) {
		int i = 0;
		for (String itemId : context.getPlayer().getInventory()) {
			Item item = context.getItem(itemId);
			if (item.getType() == currentType) {
				drawItem(item, x, y - i * (ITEM_ICON_SIZE + PADDING), i == currentItem);
				i++;
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.Q) {
		//context.changeState(GameStateContext.MAP);
		context.changeState(null);
		}
		return true;
	}
}
