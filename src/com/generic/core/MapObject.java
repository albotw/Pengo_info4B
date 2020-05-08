package com.generic.core;

import static com.generic.gameplay.CONFIG.*;

import com.generic.gameplay.AbstractGame;

public abstract class MapObject {
    protected int x;
    protected int y;
    protected GameMap m;
    protected String type;

    public MapObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.m = AbstractGame.instance.getMap();
    }

    abstract void destroy(MapObject killer);

    public void goLeft() {
        if (x > 0) {
            if (m.getAt(x - 1, y) == null) {
                m.release(x, y);
                x--;
                m.place(this, x, y);
            }
        }
    }

    public void goRight() {
        if (x < GRID_WIDTH - 1) {
            if (m.getAt(x + 1, y) == null) {
                m.release(x, y);
                x++;
                m.place(this, x, y);
            }
        }
    }

    public void goUp() {
        if (y > 0) {
            if (m.getAt(x, y - 1) == null) {
                m.release(x, y);
                y--;
                m.place(this, x, y);
            }
        }
    }

    public void goDown() {
        if (y < GRID_HEIGHT - 1) {
            if (m.getAt(x, y + 1) == null) {
                m.release(x, y);
                y++;
                m.place(this, x, y);
            }
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

    public String getType() {
        return type;
    }

    public void setMap() {
        this.m = AbstractGame.instance.getMap();
    }
}
