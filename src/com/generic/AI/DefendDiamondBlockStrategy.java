package com.generic.AI;

import com.generic.core.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;

import static com.generic.utils.Equations.VectorialDistance;

public class DefendDiamondBlockStrategy implements Strategy{
    public char direction;
    public AI bot;
    private MapObject controlledObject;
    private MapObject target;
    private GameMap m = AbstractGame.instance.getMap();

    public DefendDiamondBlockStrategy(AI bot)
    {
        this.bot = bot;
    }

    public void process()
    {
        System.out.println("process");

        if (target != null)
        {
            if (m.getAt(target.getX(), target.getY()) == target)
            {
                //si le bloc existe on se dirige vers lui
                setDirection();
                switch (direction)
                {
                    case 'H': controlledObject.goUp(); break;
                    case 'B': controlledObject.goDown(); break;
                    case 'G': controlledObject.goLeft(); break;
                    case 'D': controlledObject.goRight(); break;
                }
            }
            else
            {
                acquireTarget();
            }
        }
    }

    public void acquireTarget()
    {

    }

    private void setDirection()
    {
        int x = controlledObject.getX();
        int y = controlledObject.getY();

        double d_left = VectorialDistance(x - 1, target.getX(), y, target.getY());
        double d_right = VectorialDistance(x + 1, target.getX(), y, target.getY());
        double d_up = VectorialDistance(x, target.getX(), y - 1, target.getY());
        double d_down = VectorialDistance(x, target.getX(), y + 1, target.getY());

        if (d_up <= d_down && d_up <= d_left && d_up <= d_right)
        {
            direction = 'H';
        }
        else if (d_down < d_up && d_down <= d_left && d_down <= d_right)
        {
            direction = 'B';
        }
        else if (d_left < d_up && d_left <= d_right && d_left < d_down)
        {
            direction = 'G';
        }
        else if (d_right < d_up && d_right < d_down && d_right < d_left)
        {
            direction = 'D';
        }
    }
}
