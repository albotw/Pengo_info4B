package com.generic.core.entities;

import com.generic.core.MapObject;
import com.generic.core.blocks.IceBlock;
import com.generic.gameplay.AbstractGame;
import com.generic.gameplay.events.AnimalKilledEvent;
import com.generic.gameplay.events.ThreadID;
import com.generic.gameplay.v2.GameController;

public class Animal extends MapEntity{
    private boolean isStun;
    private String variante = "DARKNUT";

    public Animal(int x, int y) {
        super(x, y);
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
        m.release(this.x, this.y);
        GameController.publish(new AnimalKilledEvent(null, source), ThreadID.Game);
        clean();
    }

    public void checkCollisions(int x, int y)
    {
        if (m.getAt(x, y) instanceof Penguin)
        {
            m.getAt(x, y).destroy(this);
        }
        else if (m.getAt(x, y) instanceof IceBlock)
        {
            m.getAt(x, y).destroy(this);
        }
    }

    public void goLeft() {
        super.setOrientation('W');
        if (!isStun) {
            checkCollisions(x - 1, y);
            super.goLeft();
        }
    }

    public void goRight() {
        super.setOrientation('E');
        if (!isStun) {
            checkCollisions(x + 1, y);
            super.goRight();
        }
    }

    public void goUp() {
        super.setOrientation('N');
        if (!isStun) {
            checkCollisions(x, y - 1);
            super.goUp();
        }
    }

    public void goDown() {
        super.setOrientation('S');
        if (!isStun) {
            checkCollisions(x, y + 1);
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
