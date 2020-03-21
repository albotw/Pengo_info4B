package com.generic.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.generic.coreClasses.MapObject;
import com.generic.gameplayClasses.Game;

public class PlayerThread extends Thread implements KeyListener {
    private MapObject controlledObject;
    private int currentLives;

    public PlayerThread()
    {

    }

    public void keyTyped(KeyEvent key)
    {

    }

    public void keyPressed(KeyEvent key)
    {

    }

    public void keyReleased(KeyEvent key)
    {

    }

    public MapObject getControlledObject()
    {
        return this.controlledObject;
    }

    public void setControlledObject(MapObject mo){this.controlledObject = mo;}

    public void removeLive()
    {
        currentLives--;
        if (currentLives <= 0)
        {
            Game.gameOver();
        }
        else
        {
            Game.respawnAnimal();
        }
    }
}
