package com.generic.player;

import com.generic.core.MapEntity;
import com.generic.core.MapObject;
import com.generic.gameplay.Game;
import com.generic.gameplay.Score;

import static com.generic.gameplay.CONFIG_GAME.PLAYER_INIT_LIVES;

/**
 * TODO: création du inputHandler différée ?
 * TODO: généralisation pour toute MapEntity
 */

public class Player extends Thread
{
    private MapObject controlledObject;
    private int currentLives;
    private InputHandler ih;
    private Score s;
    private String pseudo;
    private boolean canMove;

    public Player(String pseudo)
    {
        this.pseudo = pseudo;
        this.s = new Score();
        currentLives = PLAYER_INIT_LIVES;
    }


    public void run()
    {
        ih = new InputHandler();
        while(true)
        {
            if (controlledObject != null)
            {
                linkInput();
            }

            try
            {
                sleep(16);
            }catch(Exception e) {e.printStackTrace(); }
        }
    }
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
            Game.instance.respawnPenguin(this);
        }
    }

    public MapObject getControlledObject()
    {
        return this.controlledObject;
    }

    public void setControlledObject(MapObject mo){this.controlledObject = mo;}

    public String getPseudo()
    {
        return this.pseudo;
    }

    public Score getScore() {return this.s;}

    public int getRemainigLives(){ return this.currentLives;}
}
