package com.generic.core;

import com.generic.gameplay.AbstractGame;

public class Animal extends MapEntity {
    private boolean isStun;

    public Animal(int x, int y) {
        super(x, y);
        this.type = "Animal";
        this.isStun = false;
    }

    public void action() {
    }

    public void activateStun() {
        this.isStun = true;
    }

    public void deactivateStun() {
        this.isStun = false;
    }

    public boolean isStun() {
        return this.isStun;
    }

    public void destroy(MapObject source) {
        AbstractGame.instance.getMap().release(x, y);
        AbstractGame.instance.animalKilled(this, source);
    }

    /**
     * TODO: Déplacement opti ?
     */
    public void goLeft() {
        if (!isStun) {
            if (m.getAt(x - 1, y) != null) {
                if (m.getAt(x - 1, y).getType().equals("Penguin")) {
                    m.getAt(x - 1, y).destroy(this);
                } else if (m.getAt(x - 1, y).getType().equals("IceBlock")) {
                    m.getAt(x - 1, y).destroy(this);
                }
            }
            super.goLeft();
        }
    }

    public void goRight() {
        if (!isStun) {

            if (m.getAt(x + 1, y) != null) {
                if (m.getAt(x + 1, y).getType().equals("Penguin")) {
                    m.getAt(x + 1, y).destroy(this);
                } else if (m.getAt(x + 1, y).getType().equals("IceBlock")) {
                    m.getAt(x + 1, y).destroy(this);
                }
            }
            super.goRight();
        }
    }

    public void goUp() {
        if (!isStun) {

            if (m.getAt(x, y - 1) != null) {
                if (m.getAt(x, y - 1).getType().equals("Penguin")) {
                    m.getAt(x, y - 1).destroy(this);
                } else if (m.getAt(x, y - 1).getType().equals("IceBlock")) {
                    m.getAt(x, y - 1).destroy(this);
                }
            }
            super.goUp();
        }
    }

    public void goDown() {
        if (!isStun) {

            if (m.getAt(x, y + 1) != null) {
                if (m.getAt(x, y + 1).getType().equals("Penguin")) {
                    m.getAt(x, y + 1).destroy(this);
                } else if (m.getAt(x, y + 1).getType().equals("IceBlock")) {
                    m.getAt(x, y + 1).destroy(this);
                }
            }
            super.goDown();
        }
    }
}
