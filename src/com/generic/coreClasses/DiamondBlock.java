package com.generic.coreClasses;

import com.generic.gameplayClasses.Game;

public class DiamondBlock extends MapBlock {
    public DiamondBlock(int x, int y)
    {
        super(x, y);
        this.type = "DiamondBlock";
    }

    public void destroy(){}

    public void onMoveTriggered(char direction)
    {
        super.onMoveTriggered(direction);
        Game.instance.checkDiamondBlocks();
    }
}
