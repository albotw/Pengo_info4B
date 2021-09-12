package events;

public class PlayerLostEvent extends Event{
    public PlayerLostEvent(ThreadID sender)
    {
        super(sender);
    }
}
