package com.generic.core;

import com.generic.gameplay.AbstractGame;
import com.generic.gameplay.LocalGame;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;

public class Penguin extends MapEntity{

    public Penguin(int x, int y) {
        super(x, y);
        this.type = "Penguin";
    }

    public void goUp() {
        super.setOrientation('N');
        if (m.getAt(x, y - 1).getType().equals("IceBlock") || m.getAt(x, y - 1).getType().equals("DiamondBlock")) {
            if (m.getAt(x, y - 2).getType().equals("IceBlock") || m.getAt(x, y - 2).getType().equals("DiamondBlock")) {
                m.getAt(x, y - 1).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x, y - 1))).onMoveTriggered('H', this);
            }
        } else if (m.getAt(x, y - 1).getType().equals("Animal")) {
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
        if (m.getAt(x - 1, y).getType().equals("IceBlock") || m.getAt(x - 1, y).getType().equals("DiamondBlock")) {
            if (m.getAt(x - 2, y).getType().equals("IceBlock") || m.getAt(x - 2, y).getType().equals("DiamondBlock")) {
                m.getAt(x - 1, y).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x - 1, y))).onMoveTriggered('G', this);
            }
        } else if (m.getAt(x - 1, y).getType().equals("Animal")) {
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
        if (m.getAt(x + 1, y).getType().equals("IceBlock") || m.getAt(x + 1, y).getType().equals("DiamondBlock")) {
            if (m.getAt(x + 2, y).getType().equals("IceBlock") || m.getAt(x + 2, y).getType().equals("DiamondBlock")) {
                m.getAt(x + 1, y).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x + 1, y))).onMoveTriggered('D', this);
            }
        } else if (m.getAt(x + 1, y).getType().equals("Animal")) {
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
        if (m.getAt(x, y + 1).getType().equals("IceBlock") || m.getAt(x, y + 1).getType().equals("DiamondBlock")) {
            if (m.getAt(x, y + 2).getType().equals("IceBlock")
                    || m.getAt(x, y + 2).getType().equals("DiamondBlock")) {
                m.getAt(x, y + 1).destroy(this);
            } else {
                ((MapBlock) (m.getAt(x, y + 1))).onMoveTriggered('B', this);
            }
        } else if (m.getAt(x, y + 1).getType().equals("Animal")) {
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
        AbstractGame.instance.penguinKilled(this, killer);
    }
}
