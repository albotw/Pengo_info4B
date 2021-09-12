package gameplay;

import core.entities.MapEntity;

public interface MapObjectController {
    void setControlledObject(MapEntity me);
    void clearControlledObject();
    MapEntity getControlledObject();
}
