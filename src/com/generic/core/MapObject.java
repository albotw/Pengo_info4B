package com.generic.core;

import com.generic.gameplay.GameMap;
import com.generic.graphics.Sprite;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;

public abstract class MapObject{
    protected char orientation = 'N';

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
        this.orientation = 'W';
        if (x > 0) {
            m.release(x, y);
            x--;
            update();
        }
    }

    public void goRight() {
        this.orientation = 'E';
        if (x < GRID_WIDTH - 1) {
            m.release(x, y);
            x++;
            update();
        }
    }

    public void goUp() {
        this.orientation = 'N';
        if (y > 0) {
            m.release(x, y);
            y--;
            update();
        }
    }

    public void goDown() {
        this.orientation = 'S';
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

    public void setOrientation(char direction){
        this.orientation = direction;
        this.sprite.setOrientation(this.orientation);
    }
}
