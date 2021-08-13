package com.generic.core.blocks;

import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;


public class IceBlock extends MapBlock {

    public IceBlock(int x, int y) {
        super(x, y);
        sprite.loadImage("IceBlock");
    }

    public void destroy(MapObject killer) {
        AbstractGame.instance.getMap().release(x, y);

        /*
        TODO: pourquoi ?
        try {
            Thread.currentThread().sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void onGlideEnded() {}
}
