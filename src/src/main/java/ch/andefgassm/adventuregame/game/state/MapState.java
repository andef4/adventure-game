package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.map.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapState extends AbstractGameState implements Screen  {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private TextureAtlas playerAtlas;
	private Player player;

	private int[] background = new int[] {0};
	private int[] foreground = new int[] {1};

	private ShapeRenderer shapeRenderer;

	private InputMultiplexer inputMultiplexer = null;
	private GameStateContext context;
	private int width;

	public MapState() {
		map = new TmxMapLoader().load("maps/lowlands.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();

		playerAtlas = new TextureAtlas("img/player/player.pack");
		Animation still, left, right, up, down;
		still = new Animation(1 / 2f, playerAtlas.findRegions("still"));
		left = new Animation(1 / 6f, playerAtlas.findRegions("left"));
		right = new Animation(1 / 6f, playerAtlas.findRegions("right"));
		up = new Animation(1 / 6f, playerAtlas.findRegions("up"));
		down = new Animation(1 / 6f, playerAtlas.findRegions("down"));
		still.setPlayMode(Animation.LOOP);
		left.setPlayMode(Animation.LOOP);
		right.setPlayMode(Animation.LOOP);
		up.setPlayMode(Animation.LOOP);
		down.setPlayMode(Animation.LOOP);

		player = new Player(still, left, right, up, down, (TiledMapTileLayer) map.getLayers().get(0));
		player.setPosition(11 * player.getCollisionLayer().getTileWidth(), (player.getCollisionLayer().getHeight() - 14) * player.getCollisionLayer().getTileHeight());

		inputMultiplexer = new InputMultiplexer(this, player);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();

		renderer.setView(camera);
		renderer.render(background);

		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();

		renderer.render(foreground);

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(new Color(255f/255, 200f/255, 120f/255, 1f));
		shapeRenderer.rect(0, 0, width, 150);
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		camera.viewportWidth = width / 2.5f;
		camera.viewportHeight = height / 2.5f;
	}

	@Override
	public InputProcessor getInputProcessor() {
		return inputMultiplexer;
	}

	@Override
	public void init(GameStateContext context, String param) {
		this.context = context;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.E:
			//TODO: implement game combat start
			return true;
		case Keys.I:
			context.changeState(GameStateContext.INVENTORY_MENU);
			return true;
		}
		return false;
	}

}

