package com.generic.core;

import com.generic.gameplay.GameMap;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;

public abstract class MapObject implements SubPixel, Orientation{
    protected char orientation = 'N';

    protected int x;
    protected int y;
    protected GameMap m;
    protected String type;

    protected boolean transition;
    protected int sp;

    public MapObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    abstract void destroy(MapObject killer);

    public void goLeft() {
        this.orientation = 'W';
        if (x > 0) {
            transition = true;
            m.release(x, y);
            x--;
            m.place(this, x, y);
        }
    }

    public void goRight() {
        this.orientation = 'E';
        if (x < GRID_WIDTH - 1) {
            transition = true;
            m.release(x, y);
            x++;
            m.place(this, x, y);
        }
    }

    public void goUp() {
        this.orientation = 'N';
        if (y > 0) {
            transition = true;
            m.release(x, y);
            y--;
            m.place(this, x, y);
        }
    }

    public void goDown() {
        this.orientation = 'S';
        if (y < GRID_HEIGHT - 1) {
            transition = true;
            m.release(x, y);
            y++;
            m.place(this, x, y);
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

    public void setMap(GameMap m) {
        this.m = m;
    }

    public void flush() {
        this.x = -1;
        this.y = -1;
        this.m = null;
        this.type = "";
    }

    public String getOrientation() {return "" + orientation;}

    public void setOrientation(char direction){this.orientation = direction;}

    public void setSubpixel(int val){
        this.sp = val;
    }

    public boolean getTransitionState()
    {
        return this.transition;
    }

    public void setTransitionState(boolean val)
    {
        this.transition = val;
        this.sp = 0;
    }

    public int getSubpixel()
    {
        return this.sp;
    }
}
