package com.generic.coreClasses;

import com.generic.gameplayClasses.Game;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;

public class Penguin extends MapEntity{
    public Penguin (int x, int y)
    {
        super(x, y);
    }

    public void action()
    {
        if (x == 0 || x == GRID_WIDTH || y == 0 || y == GRID_HEIGHT)
        {
            Game.instance.stunTriggered();
        }
    }

    public void destroy()
    {
        Game.instance.penguinKilled(this);
    }

}
