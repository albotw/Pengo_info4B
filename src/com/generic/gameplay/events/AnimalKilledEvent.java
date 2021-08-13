package com.generic.gameplay.events;

public class AnimalKilledEvent extends Event{
    public AnimalKilledEvent()
    {
        super();
    }

    public AnimalKilledEvent(ThreadID source, Object arg)
    {
        super(source, arg);
    }
}
