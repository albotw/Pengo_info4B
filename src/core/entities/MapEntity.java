package core.entities;

import core.MapObject;
import gameplay.MapObjectController;

public abstract class MapEntity extends MapObject {
    protected MapObjectController controller;

    public MapEntity(int x, int y) {
        super(x, y);
        sprite.toggleOrientation(true);
    }

    public void setController(MapObjectController controller)
    {
        this.controller = controller;
    }

    public MapObjectController getController()
    {
        return this.controller;
    }

    public abstract void action();
}
