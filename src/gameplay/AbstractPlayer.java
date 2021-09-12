package gameplay;

import core.entities.MapEntity;

import static config.CONFIG_GAME.PLAYER_INIT_LIVES;

public abstract class AbstractPlayer extends Thread implements MapObjectController{
    protected String pseudo;
    protected int currentLives;
    protected MapEntity controlledObject;

    protected int points;
    protected int nextLiveCounter;
    protected int nextLiveThreshold = 4000;

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
        if (nextLiveCounter >= nextLiveThreshold)
        {
            currentLives++;
            nextLiveThreshold *= 1.3;
        }
    }

    public void addEndPoints(int time)
    {
        if (time <= 20)
        {
            points += 5000;
            nextLiveCounter += 5000;
        }
        else if (time <= 30)
        {
            points += 2000;
            nextLiveCounter += 2000;
        }
        else if (time <= 40)
        {
            points += 1000;
            nextLiveCounter += 1000;
        }
        else if (time <= 60)
        {
            points += 500;
            nextLiveCounter += 500;
        }

        addLive();
    }

    public int getPoints() {
        return points;
    }

    @Override
    public MapEntity getControlledObject() {
        return this.controlledObject;
    }

    @Override
    public void setControlledObject(MapEntity me) {
        this.controlledObject = me;
        me.setController(this);
    }

    @Override
    public void clearControlledObject()
    {
        this.controlledObject = null;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public int getRemainigLives() {
        return this.currentLives;
    }
}
