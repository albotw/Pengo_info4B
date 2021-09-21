package events;

import gameplay.MapObjectController;

public class AnimalKilledEvent extends Event{
    private MapObjectController owner;
    public AnimalKilledEvent ( MapObjectController owner)
    {
        super();
        this.owner = owner;
    }

    public MapObjectController getOwner()
    {
        return this.owner;
    }
}
