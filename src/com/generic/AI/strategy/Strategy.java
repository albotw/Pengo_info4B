package com.generic.AI.strategy;

import com.generic.core.MapObject;

public interface Strategy {
    void process();

    void updateControlledObject(MapObject co);

    void flush();
}
