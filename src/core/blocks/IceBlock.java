package core.blocks;

import core.MapObject;
import graphics.TextureID;
import gameplay.GameController;
import graphics.effects.PopEffect;
import graphics.effects.SparkleEffect;


public class IceBlock extends MapBlock {
    private SparkleEffect effect;

    public IceBlock(int x, int y) {
        super(x, y);
        sprite.setTexture(TextureID.ICEBLOCK);
        this.effect = new SparkleEffect(this.sprite.x, this.sprite.y);
    }

    public void destroy(MapObject killer) {
        PopEffect pe = new PopEffect(sprite.x, sprite.y);
        this.effect.stop();
        GameController.instance.getMap().release(x, y);


        /*
        TODO: pourquoi ?
        try {
            Thread.currentThread().sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void onGlideEnded() {}

    @Override
    public void update()
    {
        super.update();
        this.effect.setPosition(this.sprite.x, this.sprite.y);
    }

    @Override
    public void clean()
    {
        super.clean();
        this.effect.stop();
    }
}
