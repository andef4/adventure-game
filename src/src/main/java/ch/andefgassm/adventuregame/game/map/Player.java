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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
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

	private static final int TILE_WIDTH = 16;
	private static final int TILE_HEIGHT = 16;

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
		boolean noCollision = false;
		if (velocity.x > 0 && currentDirection != Direction.RIGHT) {
			float snapX = newX % 16;
			if (snapX <= 3.0) {
				newX -= snapX;
				newVelocity();
				noCollision = true;
			}
		} else if (velocity.x < 0 && currentDirection != Direction.LEFT) {
			float snapX = newX % 16;
			if (snapX >= 13.0) {
				newX += 16 - snapX;
				newVelocity();
				noCollision = true;
			}
		} else if (velocity.y < 0 && currentDirection != Direction.DOWN) {
			float snapY = newY % 16;
			if (snapY >= 13.0) {
				newY += 16 - snapY;
				newVelocity();
				noCollision = true;
			}
		} else if (velocity.y > 0 && currentDirection != Direction.UP) {
			float snapY = newY % 16;
			if (snapY <= 3.0) {
				newY -= snapY;
				newVelocity();
				noCollision = true;
			}
		}
		boolean collisionX = false;
		if (!noCollision) {
			if(velocity.x > 0) { // right
				Cell cell = collisionLayer.getCell((int) ((newX + getWidth()) / TILE_WIDTH), (int) ((newY + getHeight() / 2) / TILE_HEIGHT));
				if (cell != null) {
					collisionX = true;
				}
			} else if(velocity.x < 0) { // left
				Cell cell = collisionLayer.getCell((int) ((newX) / TILE_WIDTH), (int) ((newY + getHeight() / 2) / TILE_HEIGHT));
				if (cell != null) {
					collisionX = true;
				}
			}
		}
		boolean collisionY = false;
		if (!noCollision) {
			if(velocity.y > 0) { // up
				Cell cell = collisionLayer.getCell((int) ((newX + getWidth() / 2) / TILE_WIDTH), (int) ((newY + getHeight()) / TILE_HEIGHT));
				if (cell != null) {
					collisionY = true;
				}
			} else if(velocity.y < 0) { // down
				Cell cell = collisionLayer.getCell((int) ((newX + getWidth() / 2) / TILE_WIDTH), (int) ((newY) / TILE_HEIGHT));
				if (cell != null) {
					collisionY = true;
				}
			}
		}
		if (collisionX) {
			velocity.x = 0;
			newVelocity();
		} else {
			setX(newX);
		}
		if (collisionY) {
			velocity.y = 0;
			newVelocity();
		} else {
			setY(newY);
		}

		// update animation
		animationTime += delta;
		setRegion(velocity.x < 0 ? left.getKeyFrame(animationTime) :
			velocity.x > 0 ? right.getKeyFrame(animationTime) :
				velocity.y < 0 ? down.getKeyFrame(animationTime) :
					velocity.y > 0 ? up.getKeyFrame(animationTime) :
						still.getKeyFrame(animationTime));

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
