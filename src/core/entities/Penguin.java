package core.entities;

import core.Direction;
import core.MapObject;
import graphics.TextureID;
import core.blocks.MapBlock;
import gameplay.GameController;

public class Penguin extends MapEntity {

    public Penguin(int x, int y) {
        super(x, y);
        sprite.setTexture(TextureID.LONK);
        sprite.setOrientation(Direction.UP);
    }

    public void goUp() {
        super.setOrientation(Direction.UP);
        if (m.getAt(x, y - 1)  instanceof MapBlock) {
            if (m.getAt(x, y - 2) instanceof MapBlock) {
                m.getAt(x, y - 1).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x, y - 1))).onMoveTriggered(Direction.UP, this);
            }
        } else if (m.getAt(x, y - 1) instanceof Animal) {
            Animal tmp = (Animal) m.getAt(x, y - 1);
            if (tmp.isStun()) {
                tmp.destroy(this);
                super.goUp();
            }
        } else {
            super.goUp();
        }
    }

    public void goLeft() {
        super.setOrientation(Direction.LEFT);
        if (m.getAt(x - 1, y) instanceof MapBlock) {
            if (m.getAt(x - 2, y) instanceof MapBlock) {
                m.getAt(x - 1, y).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x - 1, y))).onMoveTriggered(Direction.LEFT, this);
            }
        } else if (m.getAt(x - 1, y) instanceof Animal) {
            Animal tmp = (Animal) m.getAt(x - 1, y);
            if (tmp.isStun()) {
                tmp.destroy(this);
                super.goLeft();
            }
        } else {
            super.goLeft();
        }

    }

    public void goRight() {
        super.setOrientation(Direction.RIGHT);
        if (m.getAt(x + 1, y) instanceof MapBlock) {
            if (m.getAt(x + 2, y) instanceof MapBlock) {
                m.getAt(x + 1, y).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x + 1, y))).onMoveTriggered(Direction.RIGHT, this);
            }
        } else if (m.getAt(x + 1, y) instanceof Animal) {
            Animal tmp = (Animal) m.getAt(x + 1, y);
            if (tmp.isStun()) {
                tmp.destroy(this);
                super.goRight();
            }
        } else {
            super.goRight();
        }
    }

    public void goDown() {
        super.setOrientation(Direction.DOWN);
        if (m.getAt(x, y + 1) instanceof MapBlock) {
            if (m.getAt(x, y + 2) instanceof MapBlock) {
                m.getAt(x, y + 1).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x, y + 1))).onMoveTriggered(Direction.DOWN, this);
            }
        } else if (m.getAt(x, y + 1) instanceof Animal) {
            Animal tmp = (Animal) m.getAt(x, y + 1);
            if (tmp.isStun()) {
                tmp.destroy(this);
                super.goDown();
            }
        } else {
            super.goDown();
        }
    }

    public void action() {
        //TODO: modifier le déclenchement
        /*
        if (x == 0) {
            gameplay.instance.stunTriggered('G');
        } else if (x == GRID_WIDTH - 1) {
            gameplay.instance.stunTriggered('D');
        } else if (y == 0) {
            gameplay.instance.stunTriggered('H');
        } else if (y == GRID_HEIGHT - 1) {
            gameplay.instance.stunTriggered('B');
        }*/
    }

    public void destroy(MapObject killer) {
        GameController.instance.getMap().release(x, y);

        //TODO: envoyer signal
        //GameController.instance.penguinKilled(this, killer);
    }
}
