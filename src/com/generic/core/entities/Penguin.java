package com.generic.core.entities;

import com.generic.core.MapObject;
import com.generic.core.blocks.MapBlock;
import com.generic.gameplay.AbstractGame;
import com.generic.gameplay.LocalGame;

import static com.generic.gameplay.config.CONFIG.*;

public class Penguin extends MapEntity{

    public Penguin(int x, int y) {
        super(x, y);
        sprite.toggleOrientation();
        sprite.loadImage("LONK");
    }

    public void goUp() {
        super.setOrientation('N');
        if (m.getAt(x, y - 1)  instanceof MapBlock) {
            if (m.getAt(x, y - 2) instanceof MapBlock) {
                m.getAt(x, y - 1).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x, y - 1))).onMoveTriggered('H', this);
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
        super.setOrientation('W');
        if (m.getAt(x - 1, y) instanceof MapBlock) {
            if (m.getAt(x - 2, y) instanceof MapBlock) {
                m.getAt(x - 1, y).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x - 1, y))).onMoveTriggered('G', this);
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
        super.setOrientation('E');
        if (m.getAt(x + 1, y) instanceof MapBlock) {
            if (m.getAt(x + 2, y) instanceof MapBlock) {
                m.getAt(x + 1, y).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x + 1, y))).onMoveTriggered('D', this);
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
        super.setOrientation('S');
        if (m.getAt(x, y + 1) instanceof MapBlock) {
            if (m.getAt(x, y + 2) instanceof MapBlock) {
                m.getAt(x, y + 1).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x, y + 1))).onMoveTriggered('B', this);
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
        if (x == 0) {
            LocalGame.instance.stunTriggered('G');
        } else if (x == GRID_WIDTH - 1) {
            LocalGame.instance.stunTriggered('D');
        } else if (y == 0) {
            LocalGame.instance.stunTriggered('H');
        } else if (y == GRID_HEIGHT - 1) {
            LocalGame.instance.stunTriggered('B');
        }
    }

    public void destroy(MapObject killer) {
        AbstractGame.instance.getMap().release(x, y);

        //TODO: envoyer signal
        AbstractGame.instance.penguinKilled(this, killer);
    }
}
