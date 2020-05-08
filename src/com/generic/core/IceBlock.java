package com.generic.core;

import com.generic.gameplay.AbstractGame;

import static java.lang.Thread.sleep;

public class IceBlock extends MapBlock {

    public IceBlock(int x, int y) {
        super(x, y);
        this.type = "IceBlock";
    }

    public void destroy(MapObject killer) {
        AbstractGame.instance.getMap().release(x, y);

        try {
            sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
