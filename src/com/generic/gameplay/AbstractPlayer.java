package com.generic.gameplay;

import com.generic.core.entities.MapEntity;
import com.generic.core.MapObject;

import static com.generic.gameplay.config.CONFIG_GAME.PLAYER_INIT_LIVES;

public abstract class AbstractPlayer implements Runnable {
    protected String pseudo;
    protected int currentLives;
    protected MapEntity controlledObject;

    protected int points;
    protected int nextLiveCounter;
    protected int nextLiveThresold = 4000;

    public AbstractPlayer(String pseudo) {
        this.pseudo = pseudo;
        this.currentLives = PLAYER_INIT_LIVES;
        this.points = 0;
    }

    public abstract void run();

    public abstract void removeLive();

    public void addKillPoints()
    {
        points += 400;
        nextLiveCounter += 400;
        addLive();
    }

    public void addLive()
    {
        if (nextLiveCounter >= nextLiveThresold)
        {
            currentLives++;
            nextLiveThresold *= 1.3;
        }
    }

    public void addEndPoints(int time)
    {
        if (time <= 20)
        {
            points += 5000;
            nextLiveCounter += 5000;
        }
        else if (time > 20 && time <= 29)
        {
            points += 2000;
            nextLiveCounter += 2000;
        }
        else if (time > 29 && time <= 39)
        {
            points += 1000;
            nextLiveCounter += 1000;
        }
        else if (time > 39 && time <= 60)
        {
            points += 500;
            nextLiveCounter += 500;
        }

        addLive();
    }

    //TODO: supprimer
    public void setPoints(String context, int time) {

    }

    public int getPoints() {
        return points;
    }

    public MapObject getControlledObject() {
        return this.controlledObject;
    }

    public void setControlledObject(MapEntity me) {
        this.controlledObject = me;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public int getRemainigLives() {
        return this.currentLives;
    }
}
