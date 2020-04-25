package com.generic.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.generic.coreClasses.MapEntity;
import com.generic.coreClasses.MapObject;
import com.generic.gameplayClasses.Game;

public class Player
{
    private MapObject controlledObject;
    private int currentLives;
    private InputHandler ih;

    public Player()
    {
        ih = new InputHandler();
    }

    public MapObject getControlledObject()
    {
        return this.controlledObject;
    }

    public void setControlledObject(MapObject mo){this.controlledObject = mo;}

    public void linkInput()
    {
        if (ih.UP == true) {controlledObject.goUp(); }
        else if (ih.DOWN == true) {controlledObject.goDown(); }
        else if (ih.LEFT == true) {controlledObject.goLeft(); }
        else if (ih.RIGHT == true) {controlledObject.goRight(); }
        else if (ih.ACTION == true) {((MapEntity)(controlledObject)).action(); }
        ih.flush();
    }

    public void removeLive()
    {
        //methode appellée quand un pingouin meurt.
        currentLives--;
        if (currentLives <= 0)
        {
            Game.instance.gameOver();
        }
        else
        {
            Game.instance.respawnPenguin();
        }
    }
}
