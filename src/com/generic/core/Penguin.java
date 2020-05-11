package com.generic.core;

import com.generic.gameplay.LocalGame;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;

import com.generic.gameplay.AbstractGame;

public class Penguin extends MapEntity implements Orientation{
    private char orientation = 'N';
    public Penguin(int x, int y) {
        super(x, y);
        this.type = "Penguin";
    }

    public void goUp() {
        orientation = 'N';
        if (m.getAt(x, y - 1).getType().equals("IceBlock") || m.getAt(x, y - 1).getType().equals("DiamondBlock")) {
            if (m.getAt(x, y - 2).getType().equals("IceBlock") || m.getAt(x, y - 2).getType().equals("DiamondBlock")) {
                m.getAt(x, y - 1).destroy(this);
            } else{
                ((MapBlock) (m.getAt(x, y - 1))).onMoveTriggered('H', this);
            }
        }
        super.goUp();
    }

    public void goLeft() {
        orientation = 'W';
        if (m.getAt(x - 1, y).getType().equals("IceBlock") || m.getAt(x - 1, y).getType().equals("DiamondBlock")) {
            if (m.getAt(x - 2, y).getType().equals("IceBlock") || m.getAt(x - 2, y).getType().equals("DiamondBlock")) {
                m.getAt(x - 1, y).destroy(this);
            } else{
                ((MapBlock) (m.getAt(x - 1, y))).onMoveTriggered('G', this);
            }
        }
        super.goLeft();
    }

    public void goRight() {
        orientation = 'E';
        if (m.getAt(x + 1, y).getType().equals("IceBlock") || m.getAt(x + 1, y).getType().equals("DiamondBlock")) {
            if (m.getAt(x + 2, y).getType().equals("IceBlock") || m.getAt(x + 2, y).getType().equals("DiamondBlock")) {
                m.getAt(x + 1, y).destroy(this);
            } else{
                ((MapBlock) (m.getAt(x + 1, y))).onMoveTriggered('D', this);
            }
        }
        super.goRight();
    }

    public void goDown() {
        orientation = 'S';
        if (m.getAt(x, y + 1).getType().equals("IceBlock") || m.getAt(x, y + 1).getType().equals("DiamondBlock")) {
            if (m.getAt(x, y + 2).getType().equals("IceBlock")
                    || m.getAt(x, y + 2).getType().equals("DiamondBlock")) {
                m.getAt(x, y + 1).destroy(this);
            } else{
                ((MapBlock) (m.getAt(x, y + 1))).onMoveTriggered('B', this);
            }
        }
        super.goDown();
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

    public String  getOrientation() {
        return "" + orientation;
    }
}
