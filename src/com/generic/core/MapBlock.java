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
        switch (direction)
        {
            case 'H':
                do
                {
                    if (m.getAt(x, y - 1) != null)
                    {
                        if (m.getAt(x , y -1).getType().equals("Animal"))
                        {
                            m.getAt(x, y-1).destroy();
                        }
                    }

                    goUp();
                    tick_wait();
                }while (y != 0 && m.getAt(x, y -1) == null);
                break;
            case 'B':
                do
                {
                    if (m.getAt(x, y + 1) != null) {
                        if (m.getAt(x, y + 1).getType().equals("Animal"))
                        {
                            m.getAt(x, y + 1).destroy();
                        }
                    }

                    goDown();
                    tick_wait();
                }while (y < GRID_HEIGHT - 1 && m.getAt(x, y + 1) == null);
                break;
            case 'G':
                do
                {
                    if (m.getAt(x - 1, y) != null)
                    {
                        if (m.getAt(x - 1, y).type == "Animal")
                        {
                            m.getAt(x - 1, y).destroy();
                        }
                    }

                    goLeft();
                    tick_wait();
                }while (x != 0 && m.getAt(x - 1, y) == null);
                break;
            case 'D':
                do
                {

                    if (m.getAt(x + 1, y) != null)
                    {
                        if (m.getAt(x + 1, y).getType().equals("Animal"))
                        {
                            m.getAt(x + 1, y).destroy();
                        }
                    }
                    goRight();
                    tick_wait();
                }while (x < GRID_WIDTH - 1 && m.getAt(x + 1, y) == null);
                break;
        }
    }
}
