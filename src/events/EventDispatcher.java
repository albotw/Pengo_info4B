package events;

import events.types.Event;

import java.util.HashMap;

public class EventDispatcher {
    private HashMap<ThreadID, EventQueue> channel;

    public static EventDispatcher instance;

    public static EventDispatcher createEventDispatcher()
    {
        if (instance == null)
            instance = new EventDispatcher();

        return instance;
    }

    private EventDispatcher()
    {
        this.channel = new HashMap<ThreadID, EventQueue>();
    }

    public void subscribe(ThreadID id, EventQueue eq)
    {
        this.channel.put(id, eq);
    }

    public void unsubscribe(ThreadID id)
    {
        this.channel.remove(id);
    }

    public void publish(Event e, ThreadID target)
    {
        try
        {
            this.channel.get(target).grab(e);
        }
        catch(Exception ex)
        {
            System.out.println("Erreur lors de la publication du message");
        }
    }

    public void publishToAll(Event e)
    {
        for (EventQueue eq : this.channel.values())
        {
            eq.grab(e);
        }
    }
}
