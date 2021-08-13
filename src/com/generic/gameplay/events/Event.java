package com.generic.gameplay.events;

public class Event {
    public ThreadID sender;
    public Object arg;

    public Event(ThreadID sender, Object arg)
    {
        this.sender = sender;
        this.arg = arg;
    }

    public Event(ThreadID sender)
    {
        this(sender, null);
    }

    public Event()
    {
        this(null, null);
    }
}
