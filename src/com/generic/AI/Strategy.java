package com.generic.AI;

import com.generic.core.MapObject;

public interface Strategy {
    void process();
    MapObject acquireTarget();
    void updateCo(MapObject co);
}
