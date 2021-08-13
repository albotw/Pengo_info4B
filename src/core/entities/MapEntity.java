package core.entities;

import core.MapObject;

public abstract class MapEntity extends MapObject {
    public MapEntity(int x, int y) {
        super(x, y);
        sprite.toggleOrientation(true);
    }

    public abstract void action();
}
