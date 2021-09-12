package events;

import gameplay.MapObjectController;

public class AnimalKilledEvent extends Event{
    private MapObjectController owner;
    public AnimalKilledEvent (ThreadID sender, MapObjectController owner)
    {
        super(sender);
        this.owner = owner;
    }

    public MapObjectController getOwner()
    {
        return this.owner;
    }
}
