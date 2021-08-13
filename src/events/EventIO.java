package events;

import gameplay.GameController;

public interface EventIO {
    void grab(Event e);

    default void send(Event e, ThreadID targetID)
    {
        GameController.instance.publish(e, targetID);
    }
}
