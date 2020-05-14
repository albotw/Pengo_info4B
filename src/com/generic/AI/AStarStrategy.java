package com.generic.AI;

import com.generic.core.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;

import java.util.ArrayList;

import static com.generic.utils.Equations.RandomizedInt;
import static com.generic.utils.Equations.VectorialDistance;

public class AStarStrategy implements Strategy{
    public char direction;
    private MapObject controlledObject;
    private MapObject target;

    private ArrayList<MapObject> targetList;

    public AStarStrategy()
    {
        targetList = new ArrayList<MapObject>();
    }

    public void process()
    {
        System.out.println("process");

        if (target != null)
        {
            System.out.println("déplacement vers la cible");
            testDirection();

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
            System.out.println("recherche de cible");
            acquireTarget();
        }
    }

    public void acquireTarget()
    {
        targetList.clear();
        GameMap m = AbstractGame.instance.getMap();

        for (int i = 0; i < m.getHeight(); i++)
        {
            for (int j = 0; j < m.getWidth(); j++)
            {
                MapObject tmp = m.getAt(j, i);
                if (controlledObject.getType().equals("Penguin"))
                {
                    if (tmp.getType().equals("Animal"))
                    {
                        targetList.add(tmp);
                    }
                }
                else if (controlledObject.getType().equals("Animal"))
                {
                    if (tmp.getType().equals("Penguin"))
                    {
                        targetList.add(tmp);
                    }
                }
            }
        }

        System.out.println("fin de parcours de la carte");

        int rand = RandomizedInt(0, targetList.size() - 1);
        target = targetList.get(rand);
    }

    public void testDirection()
    {
        GameMap m = AbstractGame.instance.getMap();

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

    public void updateControlledObject(MapObject co)
    {
        this.controlledObject = co;
    }

}
