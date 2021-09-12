package events;

import gameplay.MapObjectController;

public class PenguinKilledEvent extends Event{
    private MapObjectController owner;

    public PenguinKilledEvent(ThreadID sender, MapObjectController controller)
    {
        super(sender);
        this.owner = controller;
    }

    public MapObjectController getOwner()
    {
        return this.owner;
    }
}
