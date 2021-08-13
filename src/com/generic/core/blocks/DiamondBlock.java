package com.generic.core.blocks;

import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;

public class DiamondBlock extends MapBlock {
    public DiamondBlock(int x, int y) {
        super(x, y);
        sprite.loadImage("DiamondBlock");
    }

    public void destroy(MapObject killer) {
    }

    public void onGlideEnded() {
        AbstractGame.instance.checkDiamondBlocks(this);
    }
}
