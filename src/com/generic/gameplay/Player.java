package com.generic.gameplay;

import com.generic.core.MapEntity;
import com.generic.utils.InputHandler;

import static com.generic.gameplay.CONFIG_GAME.*;
import static java.lang.Thread.sleep;

/**
 * TODO: création du inputHandler différée ?
 * TODO: généralisation pour toute MapEntity
 */

public class Player extends AbstractPlayer
{
    private InputHandler ih;

    private boolean flush;

    public Player(String pseudo)
    {
        super(pseudo);
    }


    public void run()
    {
        System.out.println("Démarrage player");
        ih = new InputHandler(((LocalGame)(LocalGame.instance)).getWindow());
        while(!flush)
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

        this.controlledObject = null;
        ih.flush();
        this.ih    = null;
        this.flush = false;
    }

    public void linkInput()
    {
        if (ih.UP == true) {controlledObject.goUp(); }
        else if (ih.DOWN == true) {controlledObject.goDown(); }
        else if (ih.LEFT == true) {controlledObject.goLeft(); }
        else if (ih.RIGHT == true) {controlledObject.goRight(); }
        else if (ih.ACTION == true)
        {
            if (controlledObject.getType().equals("Penguin")) {
                ((MapEntity) (controlledObject)).action();
            }
        }
        ih.flush();
    }

    public void flush()
    {
        flush = true;
        //this.interrupt();
        //éventuellement clear le score apres upload
        //et les vies ou appeller reset
    }

    public void removeLive()
    {
        //methode appellée quand un pingouin meurt.
        currentLives--;
        if (currentLives <= 0)
        {
            LocalGame.instance.gameOver();
        }
        else
        {
            if(PLAYER_IS_PENGUIN)
            {
                LocalGame.instance.respawnPenguin(this);
            }
            else if (PLAYER_IS_ANIMAL)
            {
                LocalGame.instance.respawnAnimal(this);
            }
        }
    }
}
