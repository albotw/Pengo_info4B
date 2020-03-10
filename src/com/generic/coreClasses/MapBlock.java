package com.generic.coreClasses;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;

public abstract class MapBlock extends MapObject{
    public MapBlock(int x, int y)
    {
        super(x, y);
    }

    public void onMoveTriggered(char direction)
    {
        switch (direction)
        {
            case 'H':
                while (y != 0 && m.getAt(x, y -1) == null)
                {
                    if (m.getAt(x , y -1).type == "Animal")
                    {
                        m.getAt(x, y-1).destroy();
                    }
                    goUp();

                }

            case 'B':
                while (y != GRID_HEIGHT && m.getAt(x, y + 1) == null)
                {
                    if (m.getAt(x , y + 1).type == "Animal")
                    {
                        m.getAt(x, y + 1).destroy();
                    }
                    goDown();

                }

            case 'G':
                while (x != 0 && m.getAt(x - 1, y) == null)
                {
                    if (m.getAt(x - 1, y).type == "Animal")
                    {
                        m.getAt(x - 1, y).destroy();
                    }
                    goLeft();
                }

            case 'D':
                while (x != GRID_WIDTH && m.getAt(x + 1, y) == null)
                {
                    if (m.getAt(x + 1, y).type == "Animal")
                    {
                        m.getAt(x + 1, y).destroy();
                    }
                    goRight();

                }
        }
    }
}
