package com.generic.coreClasses;

import com.generic.graphics.Sprite;
import static com.generic.utils.CONFIG.*;

public abstract class MapObject implements Movement{
    protected int x;
    protected int y;
    protected Sprite spr;
    protected Map m;
    protected String type;

    public MapObject(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    abstract void destroy();

    public void goLeft()
    {
        if (x != 0)
        {
            if (m.getAt(x - 1, y) == null)
            {
                m.release(x, y);
                x--;
                m.place(this, x, y);
                spr.setX(spr.getX() - SPRITE_SIZE);
            }
        }
    }

    public void goRight()
    {
        if (x != GRID_WIDTH)
        {
            if (m.getAt(x + 1, y) == null)
            {
                m.release(x, y);
                x++;
                m.place(this, x, y);
                spr.setX(spr.getX() + SPRITE_SIZE);
            }
        }
    }

    public void goUp()
    {
        if (y != 0)
        {
            if (m.getAt(x, y - 1) == null)
            {
                m.release(x, y);
                y--;
                m.place(this, x, y);
                spr.setY(spr.getY() - SPRITE_SIZE);
            }
        }
    }

    public void goDown()
    {
        if (y != GRID_HEIGHT)
        {
            if (m.getAt(x, y + 1) == null)
            {
                m.release(x, y);
                y++;
                m.place(this, x, y);
                spr.setY(spr.getY() + SPRITE_SIZE);
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

    public Sprite getSpr() {
        return spr;
    }

    public void setSpr(Sprite spr) {
        this.spr = spr;
    }

    public Map getM() {
        return m;
    }

    public void setM(Map m) {
        this.m = m;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
