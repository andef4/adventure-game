package ch.andefgassm.adventuregame.game.map;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {
    private Animation still, left, right, up, down;

    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2, animationTime = 0;
    private enum Direction {
        RIGHT, UP, DOWN, LEFT;
    }
    private List<Direction> directions = new LinkedList<Direction>();

    private MapLayers layers = null;
    private String enemyId = null;
    private TiledMapTileLayer mobLayer = null;
    private TiledMapTileLayer collisionLayer = null;
    private List<String> livingBosses = null;

    private static final int TILE_WIDTH = 16;
    private static final int TILE_HEIGHT = 16;

    public Player(Animation still, Animation left, Animation right, Animation up, Animation down, MapLayers layers) {
        super(still.getKeyFrame(0));
        this.still = still;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.layers = layers;

        collisionLayer = (TiledMapTileLayer) layers.get("collision");
        mobLayer = (TiledMapTileLayer) layers.get("mobs");
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    @Override
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
        if (velocity.x > 0 && currentDirection != Direction.RIGHT) {
            float snapX = newX % 16;
            if (snapX <= 3.0) {
                newX -= snapX;
                newVelocity();
            }
        } else if (velocity.x < 0 && currentDirection != Direction.LEFT) {
            float snapX = newX % 16;
            if (snapX >= 13.0) {
                newX += 16 - snapX;
                newVelocity();
            }
        } else if (velocity.y < 0 && currentDirection != Direction.DOWN) {
            float snapY = newY % 16;
            if (snapY >= 13.0) {
                newY += 16 - snapY;
                newVelocity();
            }
        } else if (velocity.y > 0 && currentDirection != Direction.UP) {
            float snapY = newY % 16;
            if (snapY <= 3.0) {
                newY -= snapY;
                newVelocity();
            }
        }

        // collision checks
        if (velocity.x != 0 || velocity.y != 0) {
            enemyId = null;
        }

        boolean collisionX = false;
        if(velocity.x > 0) { // right
            if (checkCollision((int) ((newX + getWidth()) / TILE_WIDTH), (int) ((newY + getHeight() / 2) / TILE_HEIGHT))) {
                collisionX = true;
            }
        } else if(velocity.x < 0) { // left
            if (checkCollision((int) ((newX) / TILE_WIDTH), (int) ((newY + getHeight() / 2) / TILE_HEIGHT))) {
                collisionX = true;
            }
        }
        boolean collisionY = false;
        if(velocity.y > 0) { // up
            if (checkCollision((int) ((newX + getWidth() / 2) / TILE_WIDTH), (int) ((newY + getHeight()) / TILE_HEIGHT))) {
                collisionY = true;
            }
        } else if(velocity.y < 0) { // down
            if (checkCollision((int) ((newX + getWidth() / 2) / TILE_WIDTH), (int) ((newY) / TILE_HEIGHT))) {
                collisionY = true;
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


    private boolean checkCollision(int cellX, int cellY) {
        // check with collision layer
        if (collisionLayer.getCell(cellX, cellY) != null) {
            return true;
        }

        // check with mobs layer
        Cell cell = getCell(cellX, cellY, mobLayer);
        if (cell != null) {
            enemyId = (String) cell.getTile().getProperties().get("enemy");
            return true;
        }

        // check with boss layers
        for (String enemyId : livingBosses) {
            TiledMapTileLayer bossLayer = (TiledMapTileLayer) layers.get("boss_" + enemyId);
            cell = getCell(cellX, cellY, bossLayer);
            if (cell != null) {
                this.enemyId = enemyId;
                return true;
            }
        }
        return false;
    }

    private Cell getCell(int cellX, int cellY, TiledMapTileLayer layer) {
        Cell cell = layer.getCell(cellX, cellY);
        if (cell != null) {
            return cell;
        }
        cell = layer.getCell(cellX - 1, cellY);
        if (cell != null) {
            return cell;
        }
        cell = layer.getCell(cellX, cellY - 1);
        if (cell != null) {
            return cell;
        }
        cell = layer.getCell(cellX - 1, cellY - 1);
        if (cell != null) {
            return cell;
        }
        return null;
    }

    public String getEnemyId() {
        return enemyId;
    }

    public void setLivingBosses(List<String> livingBosses) {
        this.livingBosses = livingBosses;
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
