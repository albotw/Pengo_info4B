package com.generic.coreClasses;

import com.generic.gameplayClasses.Game;

public class IceBlock extends MapBlock {

    public IceBlock(int x, int y)
    {
        super(x, y);
    }

    public void destroy()
    {
        Game.iceBlockDestroyed(this);
    }
}
