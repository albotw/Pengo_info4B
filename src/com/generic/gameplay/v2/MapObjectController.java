package com.generic.gameplay.v2;

import com.generic.core.entities.MapEntity;

public interface MapObjectController {
    public void setControlledObject(MapEntity me);
    public MapEntity getControlledObject();
}
