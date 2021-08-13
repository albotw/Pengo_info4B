package graphics.effects;

import graphics.TextureID;

import java.awt.*;

public class EffectFrame {
    public TextureID frame;
    public long duration;

    public EffectFrame(TextureID frame, long duration)
    {
        this.frame = frame;
        this.duration = duration;
    }
}
