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
        Game.instance.animalKilled(this);
    }

    public void goLeft()
    {
        if (isStun == false) {
            if (m.getAt(x - 1, y) != null)
            {
                if (m.getAt(x - 1, y).getType().equals("Penguin")) {
                    //m.getAt(x - 1, y).destroy();
                }

                // A voir en fonction de l'appel a action ou pas
                if (m.getAt(x - 1, y).type == "Block") {
                    m.getAt(x - 1, y).destroy();
                }
            }
            super.goLeft();
        }
    }

    public void goRight()
    {
        if (isStun == false) {

            if (m.getAt(x + 1, y) != null)
            {
                if (m.getAt(x + 1, y).getType().equals("Penguin")) {
                    //m.getAt(x + 1, y).destroy();
                }

                // A voir en fonction de l'appel a action ou pas
                if (m.getAt(x + 1, y).type == "Block") {
                    m.getAt(x + 1, y).destroy();
                }
            }
            super.goRight();
        }
    }

    public void goUp()
    {
        if (isStun == false) {

            if (m.getAt(x, y - 1) != null)
            {
                if (m.getAt(x, y - 1).getType().equals("Penguin")) {
                    //m.getAt(x, y - 1).destroy();
                }

                // A voir en fonction de l'appel a action ou pas
                if (m.getAt(x, y - 1).type == "Block") {
                    m.getAt(x, y - 1).destroy();
                }
            }
            super.goUp();
        }
    }

    public void goDown()
    {
        if (isStun == false) {

            if (m.getAt(x, y + 1) != null)
            {
                if (m.getAt(x, y + 1).getType().equals("Penguin")) {
                    //m.getAt(x, y + 1).destroy();
                }

                // A voir en fonction de l'appel a action ou pas
                if (m.getAt(x, y + 1).type == "Block") {
                    m.getAt(x, y + 1).destroy();
                }
            }
            super.goDown();
        }
    }
}
