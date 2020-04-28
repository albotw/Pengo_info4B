package com.generic.core;

import static com.generic.utils.CONFIG.*;

public abstract class MapBlock extends MapObject{
    public MapBlock(int x, int y)
    {
        super(x, y);
    }

    private void tick_wait()
    {
        try{
            Thread.currentThread().sleep(30);
        }catch(Exception e ){e.printStackTrace(); }
    }

    public void onMoveTriggered(char direction)
    {
        tick_wait();
        switch (direction)
        {
            case 'H':
                if (y != 0)
                {
                    if (m.getAt(x, y -1) != null)
                    {
                        if (m.getAt(x, y - 1).getType().equals("Animal"))
                        {
                            m.getAt(x, y - 1).destroy();
                            onMoveTriggered(direction);
                        }
                    }
                    else
                    {
                        goUp();
                        onMoveTriggered(direction);
                    }
                }
                break;
            case 'B':
                if (y < GRID_HEIGHT - 1)
                {
                    if (m.getAt(x, y + 1) != null)
                    {
                        if (m.getAt(x, y + 1).getType().equals("Animal"))
                        {
                            m.getAt(x, y + 1).destroy();
                            onMoveTriggered(direction);
                        }
                    }
                    else
                    {
                        goDown();
                        onMoveTriggered(direction);
                    }
                }
                break;
            case 'G':

                if (x != 0)
                {
                    if(m.getAt(x - 1, y) != null)
                    {
                        if(m.getAt(x - 1, y).getType().equals("Animal"))
                        {
                            m.getAt(x - 1, y).destroy();
                            onMoveTriggered(direction);
                        }
                    }
                    else
                    {
                        goLeft();
                        onMoveTriggered(direction);
                    }
                }
                break;
            case 'D':


                if (x < GRID_WIDTH - 1)
                {
                    if(m.getAt(x + 1, y) != null) {
                        if (m.getAt(x + 1, y).getType().equals("Animal"))
                        {
                            m.getAt(x + 1, y).destroy();
                            onMoveTriggered(direction);
                        }
                    }
                    else
                    {
                        goRight();
                        onMoveTriggered(direction);
                    }
                }
                break;
        }
    }
}
