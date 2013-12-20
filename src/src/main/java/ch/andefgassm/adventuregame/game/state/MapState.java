package ch.andefgassm.adventuregame.game.state;

import ch.andefgassm.adventuregame.game.map.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

	private int[] background = new int[] {0}, foreground = new int[] {1};

	private ShapeRenderer sr;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		//camera.position.set(0, 0, 0);
		//camera.zoom = 10f;
		camera.update();

		renderer.setView(camera);

		renderer.render(background);

		renderer.getSpriteBatch().begin();
		player.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();

		renderer.render(foreground);

		// render objects
		//sr.setProjectionMatrix(camera.combined);
//		for(MapObject object : map.getLayers().get("objects").getObjects())
//			if(object instanceof RectangleMapObject) {
//				Rectangle rect = ((RectangleMapObject) object).getRectangle();
//				sr.begin(ShapeType.Filled);
//				sr.rect(rect.x, rect.y, rect.width, rect.height);
//				sr.end();
//			} else if(object instanceof CircleMapObject) {
//				Circle circle = ((CircleMapObject) object).getCircle();
//				sr.begin(ShapeType.Filled);
//				sr.circle(circle.x, circle.y, circle.radius);
//				sr.end();
//			} else if(object instanceof EllipseMapObject) {
//				Ellipse ellipse = ((EllipseMapObject) object).getEllipse();
//				sr.begin(ShapeType.Filled);
//				sr.ellipse(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
//				sr.end();
//			} else if(object instanceof PolylineMapObject) {
//				Polyline line = ((PolylineMapObject) object).getPolyline();
//				sr.begin(ShapeType.Line);
//				sr.polyline(line.getTransformedVertices());
//				sr.end();
//			} else if(object instanceof PolygonMapObject) {
//				Polygon poly = ((PolygonMapObject) object).getPolygon();
//				sr.begin(ShapeType.Line);
//				sr.polygon(poly.getTransformedVertices());
//				sr.end();
//			}
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 2.5f;
		camera.viewportHeight = height / 2.5f;
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("maps/lowlands.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);
//		sr = new ShapeRenderer();
//		sr.setColor(Color.CYAN);
//		Gdx.gl.glLineWidth(3);

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

		Gdx.input.setInputProcessor(player);
		
		// ANIMATED TILES

		// frames
		//Array<StaticTiledMapTile> frameTiles = new Array<StaticTiledMapTile>(2);
		
		// get the frame tiles
//		Iterator<TiledMapTile> tiles = map.getTileSets().getTileSet("tiles").iterator();
//		while(tiles.hasNext()) {
//			TiledMapTile tile = tiles.next();
//			if(tile.getProperties().containsKey("animation") && tile.getProperties().get("animation", String.class).equals("flower"))
//				frameTiles.add((StaticTiledMapTile) tile);
//		}
		
//		// create the animated tile
//		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(1 / 3f, frameTiles);
//
//		// background layer
//		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("background");
//
//		// replace static with animated tile
//		for(int x = 0; x < layer.getWidth(); x++)
//			for(int y = 0; y < layer.getHeight(); y++) {
//				Cell cell = layer.getCell(x, y);
//				if(cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("flower"))
//					cell.setTile(animatedTile);
//			}
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		sr.dispose();
		playerAtlas.dispose();
	}

	@Override
	public void init(GameStateContext context, String param) {
		// TODO Auto-generated method stub
		
	}

}