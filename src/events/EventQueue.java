package events;

import events.types.Event;
import gameplay.GameController;

import java.util.ArrayDeque;

public class EventQueue {
    private ArrayDeque<Event> queue;
    private ThreadID controller;

    public EventQueue(ThreadID controller)
    {
        this.controller = controller;
        this.queue = new ArrayDeque<Event>();
        EventDispatcher.instance.subscribe(this.controller, this);
    }

    public void send(Event e, ThreadID targetID)
    {
        e.setSender(this.controller);
        EventDispatcher.instance.publish(e, targetID);
    }

    public void broadcast(Event e)
    {
        e.setSender(this.controller);
        EventDispatcher.instance.publishToAll(e);
    }

    public void grab(Event e)
    {
        this.queue.add(e);
    }

    public boolean isEmpty()
    {
        return this.queue.isEmpty();
    }

    public Event get()
    {
        return this.queue.poll();
    }
}
