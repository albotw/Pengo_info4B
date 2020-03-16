package com.generic.coreClasses;

import com.generic.gameplayClasses.Game;

public class Animal extends MapEntity {
    private boolean isStun;
    public Animal(int x, int y)
    {
        super(x, y);
        this.type = "Animal";
        this.isStun = false;
    }

    public void action(){}

    public void activateStun()
    {
        this.isStun = true;
    }

    public void deactivateStun()
    {
        this.isStun = false;
    }

    public boolean isStun()
    {
        return this.isStun;
    }

    public void destroy()
    {
        Game.animalKilled(this);
    }

    public void goLeft()
    {
        if (isStun == false) {
            super.goLeft();
            if (m.getAt(x - 1, y).type == "Penguin") {
                m.getAt(x - 1, y).destroy();
            }

            // A voir en fonction de l'appel a action ou pas
            if (m.getAt(x - 1, y).type == "Block") {
                m.getAt(x - 1, y).destroy();
            }
        }
    }

    public void goRight()
    {
        if (isStun == false) {
            super.goRight();
            if (m.getAt(x + 1, y).type == "Penguin") {
                m.getAt(x + 1, y).destroy();
            }

            // A voir en fonction de l'appel a action ou pas
            if (m.getAt(x + 1, y).type == "Block") {
                m.getAt(x + 1, y).destroy();
            }
        }
    }

    public void goUp()
    {
        if (isStun == false) {
            super.goLeft();
            if (m.getAt(x, y - 1).type == "Penguin") {
                m.getAt(x, y - 1).destroy();
            }

            // A voir en fonction de l'appel a action ou pas
            if (m.getAt(x, y - 1).type == "Block") {
                m.getAt(x, y - 1).destroy();
            }
        }
    }

    public void goDown()
    {
        if (isStun == false) {
            super.goLeft();
            if (m.getAt(x, y + 1).type == "Penguin") {
                m.getAt(x, y + 1).destroy();
            }

            // A voir en fonction de l'appel a action ou pas
            if (m.getAt(x, y + 1).type == "Block") {
                m.getAt(x, y + 1).destroy();
            }
        }
    }
}