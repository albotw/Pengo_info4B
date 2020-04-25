package com.generic.coreClasses;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;

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
                while (y != 0 && m.getAt(x, y -1) == null)
                {
                    /*if (m.getAt(x , y -1).type == "Animal")
                    {
                        m.getAt(x, y-1).destroy();
                    }

                     */
                    goUp();
                    tick_wait();
                }
                break;
            case 'B':
                while (y < GRID_HEIGHT - 1 && m.getAt(x, y + 1) == null)
                {
                    //ATTENTION VERIF A FAIRE AUTREMENT
                    /*if (m.getAt(x , y + 1).type == "Animal")
                    {
                        m.getAt(x, y + 1).destroy();
                    }

                     */
                    goDown();
                    tick_wait();
                }
                break;
            case 'G':
                while (x != 0 && m.getAt(x - 1, y) == null)
                {
                    /*if (m.getAt(x - 1, y).type == "Animal")
                    {
                        m.getAt(x - 1, y).destroy();
                    }

                     */
                    goLeft();
                    tick_wait();
                }
                break;
            case 'D':
                while (x < GRID_WIDTH - 1 && m.getAt(x + 1, y) == null)
                {
                    /*
                    if (m.getAt(x + 1, y).type == "Animal")
                    {
                        m.getAt(x + 1, y).destroy();
                    }

                     */
                    goRight();
                    tick_wait();
                }
                break;
        }
    }
}
