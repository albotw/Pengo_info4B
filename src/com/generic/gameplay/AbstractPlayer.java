package com.generic.gameplay;

import com.generic.core.MapEntity;
import com.generic.core.MapObject;

import static com.generic.gameplay.CONFIG_GAME.PLAYER_INIT_LIVES;

public abstract class AbstractPlayer implements Runnable{
    protected String pseudo;
    protected int currentLives;
    protected int points;
    protected MapEntity controlledObject;
    private int addLiveDelta;

    public AbstractPlayer(String pseudo)
    {
        this.pseudo = pseudo;
        this.currentLives = PLAYER_INIT_LIVES;
        this.points = 0;
    }

    public abstract void run();

    public abstract void removeLive();

    public void setPoints(String context, int time){
        switch (context)
        {
            case "AnimalKilled":
                points = points + 400;
                addLiveDelta = addLiveDelta + 400;
                break;

            case "GameEnd":
                if(time <= 20){
                    points = points + 5000;
                    addLiveDelta = addLiveDelta  +5000;
                }
                if(time > 20 && time <= 29){
                    points = points + 2000;
                    addLiveDelta = addLiveDelta  +2000;
                }
                if(time > 29 && time <= 39){
                    points = points + 1000;
                    addLiveDelta = addLiveDelta  +1000;
                }
                if(time > 39 && time <= 60){
                    points = points + 500;
                    addLiveDelta = addLiveDelta  +5000;
                }
                else
                    points = points + 150;

                break;
        }
        if (addLiveDelta >= 4000)
        {
            currentLives++;
        }
        System.out.println(pseudo + " | Points = " + points);
    }

    public int getPoints() {
        return points;
    }

    public MapObject getControlledObject()
    {
        return this.controlledObject;
    }

    public void setControlledObject(MapEntity me){this.controlledObject = me;}

    public String getPseudo()
    {
        return this.pseudo;
    }

    public int getRemainigLives(){ return this.currentLives;}
}
