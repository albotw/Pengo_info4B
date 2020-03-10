package com.generic.coreClasses;

public abstract class MapEntity extends MapObject{
    public MapEntity(int x, int y)
    {
        super(x, y);
    }

    abstract void action();
}
