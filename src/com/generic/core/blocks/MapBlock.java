package com.generic.core.blocks;

import com.generic.core.entities.Animal;
import com.generic.core.entities.MapEntity;
import com.generic.core.MapObject;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;

public abstract class MapBlock extends MapObject {
    public MapBlock(int x, int y) {
        super(x, y);
    }

    private void tick_wait() {
        try {
            Thread.currentThread().sleep(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMoveTriggered(char direction, MapEntity source) {
        tick_wait();
        switch (direction) {
            case 'H':
                if (y != 0) {
                    if (m.getAt(x, y - 1) != null) {
                        if (m.getAt(x, y - 1) instanceof Animal) {
                            m.getAt(x, y - 1).destroy(source);
                            onMoveTriggered(direction, source);
                        } else {
                            onGlideEnded();
                        }
                    } else {
                        goUp();
                        onMoveTriggered(direction, source);
                    }
                }
                break;
            case 'B':
                if (y < GRID_HEIGHT - 1) {
                    if (m.getAt(x, y - 1) != null) {
                        if (m.getAt(x, y + 1) instanceof Animal) {
                            m.getAt(x, y + 1).destroy(source);
                            onMoveTriggered(direction, source);
                        } else {
                            onGlideEnded();
                        }
                    } else {
                        goDown();
                        onMoveTriggered(direction, source);
                    }
                }
                break;
            case 'G':
                if (x != 0) {
                    if (m.getAt(x, y - 1) != null) {
                        if (m.getAt(x - 1, y) instanceof Animal) {
                            m.getAt(x - 1, y).destroy(source);
                            onMoveTriggered(direction, source);
                        } else {
                            onGlideEnded();
                        }
                    } else {
                        goLeft();
                        onMoveTriggered(direction, source);
                    }
                }
                break;
            case 'D':
                if (x < GRID_WIDTH - 1) {
                    if (m.getAt(x, y - 1) != null) {
                        if (m.getAt(x + 1, y) instanceof Animal) {
                            m.getAt(x + 1, y).destroy(source);
                            onMoveTriggered(direction, source);
                        } else {
                            onGlideEnded();
                        }
                    } else {
                        goRight();
                        onMoveTriggered(direction, source);
                    }
                }
                break;
        }
    }

    public abstract void onGlideEnded();
}
