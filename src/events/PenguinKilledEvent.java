package events;

import gameplay.MapObjectController;

public class PenguinKilledEvent extends Event{
    private MapObjectController owner;

    public PenguinKilledEvent(MapObjectController controller)
    {
        super();
        this.owner = controller;
    }

    public MapObjectController getOwner()
    {
        return this.owner;
    }
}
