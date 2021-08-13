package AI.strategy;

import core.MapObject;

public interface Strategy {
    void process();

    void updateControlledObject(MapObject co);

    void flush();
}
