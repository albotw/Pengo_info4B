package com.generic.coreClasses;

import com.generic.gameplayClasses.Game;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;

public class Penguin extends MapEntity{
    public Penguin (int x, int y)
    {
        super(x, y);
        this.type = "Penguin";
    }

    public void goUp()
    {
        if (m.getAt(x, y- 1) != null)
        {
            if (m.getAt(x, y-1).getType().equals("IceBlock") || m.getAt(x, y - 1).getType().equals("DiamondBlock"))
            {
                if (m.getAt(x, y - 2) != null)
                {
                    if (m.getAt(x, y - 2).getType().equals("IceBlock"))
                    {
                        m.getAt(x, y - 1).destroy();
                    }
                    else
                    {
                        ((MapBlock)(m.getAt(x, y-1))).onMoveTriggered('H');
                    }
                }
                else
                {
                    ((MapBlock)(m.getAt(x, y-1))).onMoveTriggered('H');
                }
            }
        }
        super.goUp();
    }

    public void goLeft()
    {
        if (m.getAt(x - 1, y) != null)
        {
            if (m.getAt(x - 1, y).getType().equals("IceBlock") || m.getAt(x - 1, y).getType().equals("DiamondBlock"))
            {
                if (m.getAt(x - 2, y) != null)
                {
                    if (m.getAt(x - 2, y).getType().equals("IceBlock"))
                    {
                        m.getAt(x - 1, y).destroy();
                    }
                    else {
                        ((MapBlock) (m.getAt(x - 1, y))).onMoveTriggered('G');
                    }
                }
                else {
                    ((MapBlock) (m.getAt(x - 1, y))).onMoveTriggered('G');
                }
            }

        }
        super.goLeft();
    }

    public void goRight()
    {
        if (m.getAt(x + 1, y) != null)
        {
            if (m.getAt(x + 1, y).getType().equals("IceBlock") || m.getAt(x + 1, y).getType().equals("DiamondBlock"))
            {
                if (m.getAt(x + 2, y) != null)
                {
                    if (m.getAt(x + 2, y).getType().equals("IceBlock"))
                    {
                        m.getAt(x + 1, y).destroy();
                    }
                    else
                    {
                        ((MapBlock)(m.getAt(x + 1, y))).onMoveTriggered('D');
                    }
                }
                else
                {
                    ((MapBlock)(m.getAt(x + 1, y))).onMoveTriggered('D');
                }
            }
        }
        super.goRight();
    }

    public void goDown()
    {
        if (m.getAt(x, y + 1) != null)
        {
            if (m.getAt(x, y + 1).getType().equals("IceBlock") || m.getAt(x, y + 1).getType().equals("DiamondBlock"))
            {
                if (m.getAt(x, y + 2) != null)
                {
                    if (m.getAt(x, y + 2).getType().equals("IceBlock"))
                    {
                        m.getAt(x, y + 1).destroy();
                    }
                    else
                    {
                        ((MapBlock)(m.getAt(x, y + 1))).onMoveTriggered('B');
                    }
                }
                else
                {
                    ((MapBlock)(m.getAt(x, y + 1))).onMoveTriggered('B');
                }
            }
        }
        super.goDown();
    }
    public void action()
    {
        if (x == 0)
        {
            Game.instance.stunTriggered('G');
        }
        else if(x == GRID_WIDTH - 1)
        {
            Game.instance.stunTriggered('D');
        }
        else if (y == 0)
        {
            Game.instance.stunTriggered('H');
        }
        else if (y == GRID_HEIGHT - 1)
        {
            Game.instance.stunTriggered('B');
        }
    }

    public void destroy()
    {
        Game.instance.penguinKilled(this);
    }

}
