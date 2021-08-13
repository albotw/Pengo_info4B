package com.generic.gameplay.events;

import com.generic.gameplay.v2.GameController;

public interface EventIO {
    void grab(Event e);

    default void send(Event e, ThreadID targetID)
    {
        GameController.instance.publish(e, targetID);
    }
}
