package core.blocks;

import core.Direction;
import core.entities.Animal;
import core.entities.MapEntity;
import core.MapObject;

import static config.CONFIG.GRID_HEIGHT;
import static config.CONFIG.GRID_WIDTH;

public abstract class MapBlock extends MapObject {
    public MapBlock(int x, int y) {
        super(x, y);
        sprite.toggleOrientation(false);
    }

    private void tick_wait() {
        try {
            Thread.currentThread().sleep(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMoveTriggered(Direction direction, MapEntity source) {
        tick_wait();
        switch (direction) {
            case UP:
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
            case DOWN:
                if (y < GRID_HEIGHT - 1) {
                    if (m.getAt(x, y + 1) != null) {
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
            case LEFT:
                if (x != 0) {
                    if (m.getAt(x - 1, y) != null) {
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
            case RIGHT:
                if (x < GRID_WIDTH - 1) {
                    if (m.getAt(x + 1, y) != null) {
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
