package ch.andefgassm.adventuregame.game.map;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

	/** the movement velocity */
	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2, animationTime = 0;
	private enum Direction {
		RIGHT, UP, DOWN, LEFT;
	}
	private List<Direction> directions = new LinkedList<Direction>();


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

	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.UP:
			directions.add(Direction.UP);
			if (velocity.x == 0) {
				velocity.y = speed;
			}
			animationTime = 0;
			break;
		case Keys.LEFT:
			directions.add(Direction.LEFT);
			if (velocity.y == 0) {
				velocity.x = -speed;
			}
			animationTime = 0;
			break;
		case Keys.RIGHT:
			directions.add(Direction.RIGHT);
			if (velocity.y == 0) {
				velocity.x = speed;
			}
			animationTime = 0;
			break;
		case Keys.DOWN:
			directions.add(Direction.DOWN);
			if (velocity.x == 0) {
				velocity.y = -speed;
			}
			animationTime = 0;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.UP:
			directions.remove(Direction.UP);
			break;
		case Keys.LEFT:
			directions.remove(Direction.LEFT);
			break;
		case Keys.RIGHT:
			directions.remove(Direction.RIGHT);
			break;
		case Keys.DOWN:
			directions.remove(Direction.DOWN);
			break;
		}
		return true;
	}

	/**
	 * calculate new velocity after keyUp and position adjustment
	 */
	private void newVelocity() {
		animationTime = 0;
		if (directions.size() > 0) {
			Direction currentDirection = directions.get(directions.size() - 1);
			switch (currentDirection) {
			case UP:
				velocity.x = 0;
				velocity.y = speed;
				break;
			case LEFT:
				velocity.x = -speed;
				velocity.y = 0;
				break;
			case RIGHT:
				velocity.x = speed;
				velocity.y = 0;
				break;
			case DOWN:
				velocity.x = 0;
				velocity.y = -speed;
				break;
			}
		} else {
			velocity.x = 0;
			velocity.y = 0;
		}
	}

	public void update(float delta) {
		float newX = getX() + velocity.x * delta;
		float newY = getY() + velocity.y * delta;

		// adjust position of player so that he always stop on a tile and not bewteen two.
		Direction currentDirection = null;
		if (directions.size() != 0) {
			currentDirection = directions.get(directions.size() - 1);
		}
		if (velocity.x > 0 && currentDirection != Direction.RIGHT || velocity.x < 0 && currentDirection != Direction.LEFT) {
			float snapX = newX % 16;
			if (snapX <= 3.0) {
				newX -= snapX;
				newVelocity();
			} else if (snapX >= 13.0) {
				newX += 16 - snapX;
				newVelocity();
			}
		} else if (velocity.y < 0 && currentDirection != Direction.DOWN || velocity.y > 0 && currentDirection != Direction.UP) {
			float snapY = newY % 16;
			if (snapY <= 3.0) {
				newY -= snapY;
				newVelocity();
			} else if (snapY >= 13.0) {
				newY += 16 - snapY;
				newVelocity();
			}
		}

		// set new position
		setX(newX);
		setY(newY);

		// update animation
		animationTime += delta;
		setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime) :
			velocity.x > 0 ? right.getKeyFrame(animationTime) :
				velocity.y < 0 ? down.getKeyFrame(animationTime) :
					velocity.y > 0 ? up.getKeyFrame(animationTime) :
						still.getKeyFrame(animationTime));

		/*
		// save old position
		float oldX = getX(), oldY = getY(), tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		boolean collisionX = false, collisionY = false;

		if (velocity.x != 0|| velocity.y != 0) {
			enemyId = null;
		}


		// move on x


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
		}*/

	}
	/*
	private boolean isCellBlocked(Cell cell) {
		if (cell.getTile() != null) {
			String enemyId = cell.getTile().getProperties().get(ENEMY_KEY, String.class);
			if (enemyId != null) {
				this.enemyId = enemyId;
			}
			if (cell.getTile().getProperties().containsKey(BLOCKED_KEY)) {
				return true;
			}
		}

		return cell != null;
	}
	*/
	public String getEnemyId() {
		return enemyId;
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
