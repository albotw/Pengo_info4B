package com.generic.player;

import com.generic.core.MapEntity;
import com.generic.core.MapObject;
import com.generic.gameplay.Game;

import static com.generic.gameplay.CONFIG_GAME.*;
import static java.lang.Thread.sleep;

/**
 * TODO: création du inputHandler différée ?
 * TODO: généralisation pour toute MapEntity
 */

public class Player implements Runnable
{
    private MapObject controlledObject;
    private int currentLives;
    private InputHandler ih;
    private String pseudo;
    private int points;
    private boolean flush;

    public Player(String pseudo)
    {
        this.pseudo = pseudo;
        this.points = 0;
        currentLives = PLAYER_INIT_LIVES;
    }


    public void run()
    {
        System.out.println("Démarrage player");
        ih = new InputHandler();
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
        this.ih = null;
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
            Game.instance.gameOver();
        }
        else
        {
            if(PLAYER_IS_PENGUIN)
            {
                Game.instance.respawnPenguin(this);
            }
            else if (PLAYER_IS_ANIMAL)
            {
                Game.instance.respawnAnimal(this);
            }
        }
    }

    public void setPoints(String context, int time){
        switch (context)
        {
            case "AnimalKilled":
                points = points + 400;
                break;

            case "GameEnd":
                if(time <= 20){
                    points = points + 5000;
                }
                if(time > 20 && time <= 29){
                    points = points + 2000;
                }
                if(time > 29 && time <= 39){
                    points = points + 1000;
                }
                if(time > 39 && time <= 60){
                    points = points + 500;
                }
                else
                    points = points + 150;

                break;

        }
        System.out.println("Points = " + points);
    }

    public int getPoints() {
        return points;
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

    public int getRemainigLives(){ return this.currentLives;}
}
