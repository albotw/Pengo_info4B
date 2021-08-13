package graphics.effects;

import graphics.TextureID;

public class PopEffect extends Effect{

    public PopEffect(int x, int y) {
        super(x, y, false);
    }

    @Override
    void setFrames() {
        frames.add(new EffectFrame(TextureID.POP_EFFECT_FRAME_1, 100));
        frames.add(new EffectFrame(TextureID.POP_EFFECT_FRAME_2, 200));
        frames.add(new EffectFrame(TextureID.POP_EFFECT_FRAME_3, 300));
        frames.add(new EffectFrame(TextureID.POP_EFFECT_FRAME_4, 400));
    }
}
