package events;

import gameplay.MapObjectController;

public class RespawnPenguinEvent extends Event{
    private MapObjectController obj;

    public RespawnPenguinEvent (MapObjectController controller) { this.obj = controller; }

    public MapObjectController getController()
    {
        return this.obj;
    }
}
