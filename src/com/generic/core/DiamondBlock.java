package com.generic.core;

import com.generic.gameplay.AbstractGame;

public class DiamondBlock extends MapBlock {
    public DiamondBlock(int x, int y) {
        super(x, y);
        this.type = "DiamondBlock";
        sprite.loadImage("DiamondBlock");
    }

    public void destroy(MapObject killer) {
    }

    public void onGlideEnded() {
        AbstractGame.instance.checkDiamondBlocks(this);
    }
}
