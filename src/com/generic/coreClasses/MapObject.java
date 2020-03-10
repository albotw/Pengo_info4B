package com.generic.coreClasses;

import com.generic.graphics.Sprite;

public abstract class MapObject implements Movement{
    protected int x;
    protected int y;
    protected Sprite spr;

    public MapObject(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    abstract void destroy();

    public void goLeft()
    {

    }

    public void goRight()
    {

    }

    public void goUp()
    {

    }

    public void goDown()
    {
        
    }
}
