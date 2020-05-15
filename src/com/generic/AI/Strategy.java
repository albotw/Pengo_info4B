package com.generic.AI;

import com.generic.core.MapObject;

public interface Strategy {
    void process();

    void updateControlledObject(MapObject co);
}
