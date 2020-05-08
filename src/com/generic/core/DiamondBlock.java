package com.generic.core;

import com.generic.gameplay.AbstractGame;

public class DiamondBlock extends MapBlock {
    public DiamondBlock(int x, int y) {
        super(x, y);
        this.type = "DiamondBlock";
    }

    public void destroy(MapObject killer) {
    }

    public void onMoveTriggered(char direction, MapEntity source) {
        super.onMoveTriggered(direction, source);
        AbstractGame.instance.checkDiamondBlocks();
    }
}
