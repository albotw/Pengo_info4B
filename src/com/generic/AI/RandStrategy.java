package com.generic.AI;

import com.generic.core.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;
import com.generic.gameplay.AbstractPlayer;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.utils.Equations.RandomizedInt;

public class RandStrategy implements Strategy{

    public char direction;
    public AI bot;
    private MapObject controlledObject;

    public RandStrategy(AI bot)
    {
        this.bot = bot;

        int rand  = RandomizedInt(0, 3);
        switch (rand)
        {
            case 1: direction = 'H'; break;
            case 2: direction = 'B'; break;
            case 3: direction = 'G'; break;
            case 4: direction = 'D'; break;
        }
    }

    public void process()
    {
        System.out.println("process");
        int rand = RandomizedInt(0, 10);
        if (testDirections() && rand <= 8)
        {
            System.out.println("direction valide");
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
            System.out.println("direction invalide");
            refreshDirection();
        }
    }

    private boolean testDirections()
    {
        //si direction ou on s'en va est bloquée: retourne false
        //sinon retourne true.
        GameMap m = AbstractGame.instance.getMap();
        int x = controlledObject.getX();
        int y = controlledObject.getY();

        boolean output = true;
        switch(direction)
        {
            case 'H':
            {
                if (m.getAt(x, y -1).getType().equals("DiamondBlock") || y == 0)
                {
                    output = false;
                }
                break;
            }

            case 'B':
            {
                if (m.getAt(x, y + 1).getType().equals("DiamondBlock") || y == m.getHeight() - 1)
                {
                    output = false;
                }
                break;
            }

            case 'G':
            {
                if (m.getAt(x - 1, y).getType().equals("DiamondBlock") || x == 0)
                {
                    output = false;
                }
                break;
            }

            case 'D':
            {
                if (m.getAt(x+1, y).getType().equals("DiamondBlock") || x == m.getWidth() - 1)
                {
                    output = false;
                }
                break;
            }
        }

        return output;
    }

    private void refreshDirection()
    {
        int rand = RandomizedInt(0, 3);
        if (rand == 0 && direction != 'H')
        {
            direction = 'H';
        }
        else if (rand == 1 && direction != 'B')
        {
            direction = 'B';
        }
        else if (rand == 2 && direction != 'G')
        {
            direction = 'G';
        }
        else if (rand == 3 && direction != 'D')
        {
            direction = 'D';
        }
    }

    public void updateControlledObject(MapObject co)
    {
        this.controlledObject = co;
    }

    public void setTargetFromMap(MapObject o)
    {

    }

    public void acquireTarget()
    {

    }
}
