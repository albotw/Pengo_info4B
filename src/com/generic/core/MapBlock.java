package com.generic.core;

import static com.generic.gameplay.CONFIG.*;

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

    public void onMoveTriggered(char direction, MapEntity source)
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
                            m.getAt(x, y - 1).destroy(source);
                            onMoveTriggered(direction, source);
                        }
                    }
                    else
                    {
                        goUp();
                        onMoveTriggered(direction, source);
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
                            m.getAt(x, y + 1).destroy(source);
                            onMoveTriggered(direction, source);
                        }
                    }
                    else
                    {
                        goDown();
                        onMoveTriggered(direction, source);
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
                            m.getAt(x - 1, y).destroy(source);
                            onMoveTriggered(direction, source);
                        }
                    }
                    else
                    {
                        goLeft();
                        onMoveTriggered(direction, source);
                    }
                }
                break;
            case 'D':


                if (x < GRID_WIDTH - 1)
                {
                    if(m.getAt(x + 1, y) != null) {
                        if (m.getAt(x + 1, y).getType().equals("Animal"))
                        {
                            m.getAt(x + 1, y).destroy(source);
                            onMoveTriggered(direction, source);
                        }
                    }
                    else
                    {
                        goRight();
                        onMoveTriggered(direction, source);
                    }
                }
                break;
        }
    }
}
