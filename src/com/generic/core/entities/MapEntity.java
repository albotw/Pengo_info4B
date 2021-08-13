package com.generic.core.entities;

import com.generic.core.MapObject;

public abstract class MapEntity extends MapObject {
    public MapEntity(int x, int y) {
        super(x, y);
    }

    public abstract void action();
}
