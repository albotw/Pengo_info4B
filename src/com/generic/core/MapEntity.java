package com.generic.core;

public abstract class MapEntity extends MapObject{
    private char orientation = 'N';

    public MapEntity(int x, int y) {
        super(x, y);
    }

    public abstract void action();
}
