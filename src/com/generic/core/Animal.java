package com.generic.core;

import com.generic.gameplay.AbstractGame;

public class Animal extends MapEntity implements Variante{
    private boolean isStun;
    private String variante;

    public Animal(int x, int y) {
        super(x, y);
        this.type = "Animal";
        this.isStun = false;
        sprite.toggleOrientation();
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

    public void goLeft() {
        super.setOrientation('W');
        if (!isStun) {
            if (m.getAt(x - 1, y).getType().equals("Penguin")) {
                m.getAt(x - 1, y).destroy(this);
            } else if (m.getAt(x - 1, y).getType().equals("IceBlock")) {
                m.getAt(x - 1, y).destroy(this);
            }
            super.goLeft();
        }
    }

    public void goRight() {
        super.setOrientation('E');
        if (!isStun) {
            if (m.getAt(x + 1, y).getType().equals("Penguin")) {
                m.getAt(x + 1, y).destroy(this);
            } else if (m.getAt(x + 1, y).getType().equals("IceBlock")) {
                m.getAt(x + 1, y).destroy(this);
            }
            super.goRight();
        }
    }

    public void goUp() {
        super.setOrientation('N');
        if (!isStun) {
            if (m.getAt(x, y - 1).getType().equals("Penguin")) {
                m.getAt(x, y - 1).destroy(this);
            } else if (m.getAt(x, y - 1).getType().equals("IceBlock")) {
                m.getAt(x, y - 1).destroy(this);
            }
            super.goUp();
        }
    }

    public void goDown() {
        super.setOrientation('S');
        if (!isStun) {
            if (m.getAt(x, y + 1).getType().equals("Penguin")) {
                m.getAt(x, y + 1).destroy(this);
            } else if (m.getAt(x, y + 1).getType().equals("IceBlock")) {
                m.getAt(x, y + 1).destroy(this);
            }
            super.goDown();
        }
    }

    public void setVariante(String var) {
        this.variante = var;
        sprite.loadImage(variante);
    }

    public String getVariante() {
        return this.variante;
    }
}
