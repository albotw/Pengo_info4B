package core.blocks;


import core.MapObject;
import graphics.TextureID;

public class DiamondBlock extends MapBlock {
    public DiamondBlock(int x, int y) {
        super(x, y);
        sprite.setTexture(TextureID.DIAMONDBLOCK);
    }

    public void destroy(MapObject killer) {
    }

    public void onGlideEnded() {
        //TODO: modifier
        //AbstractGame.instance.checkDiamondBlocks(this);
    }
}
