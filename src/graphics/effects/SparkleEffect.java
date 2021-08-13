package graphics.effects;

import graphics.TextureID;

public class SparkleEffect extends Effect{

    public SparkleEffect(int x, int y) {
        super(x, y, true);
    }

    @Override
    void setFrames() {
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_1, 100));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_2, 200));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_3, 300));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_4, 400));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_5, 500));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_6, 600));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_7, 700));
        frames.add(new EffectFrame(TextureID.SPARKLE_EFFECT_FRAME_8, 3000));
    }
}
