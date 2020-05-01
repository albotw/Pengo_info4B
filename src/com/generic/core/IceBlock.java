package com.generic.core;

import com.generic.gameplay.Game;

public class IceBlock extends MapBlock {

    public IceBlock(int x, int y)
    {
        super(x, y);
        this.type = "IceBlock";
    }

    public void destroy(MapObject killer)
    {
        Game.instance.getMap().release(x, y);
    }
}
