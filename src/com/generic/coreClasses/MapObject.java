package com.generic.coreClasses;

import com.generic.gameplayClasses.Game;
import com.generic.graphics.Sprite;
import static com.generic.utils.CONFIG.*;

public abstract class MapObject implements Movement{
    protected int x;
    protected int y;
    protected Map m;
    protected String type;

    public MapObject(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.m = Game.instance.getMap();
    }

    abstract void destroy();

    public void goLeft()
    {
        if (x > 0)
        {
            if (m.getAt(x - 1, y) == null)
            {
                m.release(x, y);
                x--;
                m.place(this, x, y);
            }
        }
        System.out.println("GAUCHE");
    }

    public void goRight()
    {
        if (x < GRID_WIDTH - 1)
        {
            if (m.getAt(x + 1, y) == null)
            {
                m.release(x, y);
                x++;
                m.place(this, x, y);
            }
        }
        System.out.println("DROITE");
    }

    public void goUp()
    {
        if (y > 0)
        {
            if (m.getAt(x, y - 1) == null)
            {
                m.release(x, y);
                y--;
                m.place(this, x, y);
            }
        }
        System.out.println("HAUT");
    }

    public void goDown()
    {
        if (y < GRID_HEIGHT - 1)
        {
            if (m.getAt(x, y + 1) == null)
            {
                m.release(x, y);
                y++;
                m.place(this, x, y);
            }
        }

        System.out.println("BAS");
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

    public void setMap()
    {
        this.m = Game.instance.getMap();
    }
}
