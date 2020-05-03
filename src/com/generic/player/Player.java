package com.generic.player;

import com.generic.core.MapEntity;
import com.generic.core.MapObject;
import com.generic.gameplay.Game;

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
    private String pseudo;
    private boolean canMove;
    private int points;

    public Player(String pseudo)
    {
        this.pseudo = pseudo;
        this.points = 0;
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
