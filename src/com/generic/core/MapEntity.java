package com.generic.core;

public abstract class MapEntity extends MapObject implements Orientation{
    private char orientation = 'N';
    public MapEntity(int x, int y)
    {
        super(x, y);
    }

    public abstract void action();

    public void goUp()
    {
        orientation = 'N';
        super.goUp();
    }

    public void goLeft()
    {
        orientation = 'W';
        super.goLeft();
    }

    public void goRight()
    {
        orientation = 'E';
        super.goRight();
    }

    public void goDown()
    {
        orientation = 'S';
        super.goDown();
    }


    public String  getOrientation() {
        return "" + orientation;
    }
}
