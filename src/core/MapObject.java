package core;

import gameplay.GameMap;
import graphics.Sprite;

import static config.CONFIG.GRID_HEIGHT;
import static config.CONFIG.GRID_WIDTH;

public abstract class MapObject{
    protected Direction orientation = Direction.UP;

    protected int x;
    protected int y;
    protected GameMap m;

    protected Sprite sprite;

    public MapObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite();
        this.sprite.setPosition(x, y);
    }

    abstract public void destroy(MapObject killer);

    public void update()
    {
        m.place(this, x, y);
        sprite.setPosition(x, y);
    }

    public void goLeft() {
        this.orientation = Direction.RIGHT;
        if (x > 0) {
            m.release(x, y);
            x--;
            update();
        }
    }

    public void goRight() {
        this.orientation = Direction.LEFT;
        if (x < GRID_WIDTH - 1) {
            m.release(x, y);
            x++;
            update();
        }
    }

    public void goUp() {
        this.orientation = Direction.UP;
        if (y > 0) {
            m.release(x, y);
            y--;
            update();
        }
    }

    public void goDown() {
        this.orientation = Direction.DOWN;
        if (y < GRID_HEIGHT - 1) {
            m.release(x, y);
            y++;
            update();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setMap(GameMap m) {
        this.m = m;
    }

    public Sprite getSprite() { return this.sprite; }

    public void clean() {
        this.x = -1;
        this.y = -1;
        this.m = null;
        this.sprite = null;
    }

    public String getOrientation() {return "" + orientation;}

    public void setOrientation(Direction direction){
        this.orientation = direction;
        this.sprite.setOrientation(this.orientation);
    }
}
