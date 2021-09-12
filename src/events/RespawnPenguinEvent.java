package events;

import gameplay.MapObjectController;

public class RespawnPenguinEvent extends Event{
    private MapObjectController obj;

    public RespawnPenguinEvent (ThreadID sender, MapObjectController controller)
    {
        super(sender);
        this.obj = controller;
    }

    public MapObjectController getController()
    {
        return this.obj;
    }
}
