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
}
