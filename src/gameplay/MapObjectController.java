package gameplay;

import core.entities.MapEntity;

public interface MapObjectController {
    public void setControlledObject(MapEntity me);
    public MapEntity getControlledObject();
}
