package com.generic.AI;

import com.generic.core.MapObject;

public interface Strategy {

    void process();
    void acquireTarget();
    void updateControlledObject(MapObject co);
    void setTargetFromMap(MapObject o);

}
