package ch.andefgassm.adventuregame.game.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

	/** the movement velocity */
	private Vector2 velocity = new Vector2();

	private float speed = 60 * 2, animationTime = 0;

	private Animation still, left, right, up, down;
	private TiledMapTileLayer collisionLayer;

//	private static final String ENEMY_KEY = "enemy";

	private String enemyId = null;

	public Player(Animation still, Animation left, Animation right, Animation up, Animation down, TiledMapTileLayer collisionLayer) {
		super(still.getKeyFrame(0));
		this.still = still;
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
		this.collisionLayer = collisionLayer;
	}

	@Override
	public void draw(Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}

	public void update(float delta) {

		// save old position
		float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

		if (velocity.x != 0|| velocity.y != 0) {
			enemyId = null;
		}


		// move on x
		setX(getX() + velocity.x * delta);

		if(velocity.x < 0) { // going left
			// top left
			collisionX = isCellBlocked(collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight)));

			// middle left
			if(!collisionX)
				collisionX = isCellBlocked(collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)));

			// bottom left
			if(!collisionX)
				collisionX = isCellBlocked(collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)));
		} else if(velocity.x > 0) { // going right
			// top right
			collisionX = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)));

			// middle right
			if(!collisionX)
				collisionX = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)));

			// bottom right
			if(!collisionX)
				collisionX = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)));
		}

		// react to x collision
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
			velocity.y = 0;
		}

		// move on y
		setY(getY() + velocity.y * delta);

		if(velocity.y < 0) { // going down
			// bottom left
			collisionY = isCellBlocked(collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeight)));

			// bottom middle
			if(!collisionY)
				collisionY = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) (getY() / tileHeight)));

			// bottom right
			if(!collisionY)
				collisionY = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeight)));

			//canJump = collisionY;
		} else if(velocity.y > 0) { // going up
			// top left
			collisionY = isCellBlocked(collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight)));

			// top middle
			if(!collisionY)
				collisionY = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)));

			// top right
			if(!collisionY)
				collisionY = isCellBlocked(collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeight)));
		}

		// react to y collision
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}

		// update animation
		animationTime += delta;
		setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime) :
			velocity.x > 0 ? right.getKeyFrame(animationTime) :
				velocity.y < 0 ? down.getKeyFrame(animationTime) :
					velocity.y > 0 ? up.getKeyFrame(animationTime) :
						still.getKeyFrame(animationTime));
	}

	private boolean isCellBlocked(Cell cell) {
		/*if (cell.getTile() != null) {
			String enemyId = cell.getTile().getProperties().get(ENEMY_KEY, String.class);
			if (enemyId != null) {
				this.enemyId = enemyId;
			}
			if (cell.getTile().getProperties().containsKey(BLOCKED_KEY)) {
				return true;
			}
		}*/

		return cell != null;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
		case Keys.UP:
			velocity.y = speed;
			velocity.x = 0;
			animationTime = 0;
			break;
		case Keys.A:
		case Keys.LEFT:
			velocity.x = -speed;
			velocity.y = 0;
			animationTime = 0;
			break;
		case Keys.D:
		case Keys.RIGHT:
			velocity.x = speed;
			velocity.y = 0;
			animationTime = 0;
			break;
		case Keys.S:
		case Keys.DOWN:
			velocity.x = 0;
			velocity.y = -speed;
			animationTime = 0;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.UP:
			if (velocity.y == speed) {
				velocity.y = 0;
				animationTime = 0;
			}
			break;
		case Keys.LEFT:
			if (velocity.x == -speed) {
				velocity.x = 0;
				animationTime = 0;
			}
			break;
		case Keys.RIGHT:
			if (velocity.x == speed) {
				velocity.x = 0;
				animationTime = 0;
			}
			break;
		case Keys.DOWN:
			if (velocity.y == -speed) {
				velocity.y = 0;
				animationTime = 0;
			}
			break;
		}
		return true;
	}


	public String getEnemyId() {
		return enemyId ;
	}


	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
