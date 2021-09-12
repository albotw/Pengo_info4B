package graphics;

import core.Direction;

public enum TextureID {
    DARKNUT (true),    //Texture virtuelle du a la composition des sprites avec orientation
    DARKNUT_UP ,
    DARKNUT_DOWN ,
    DARKNUT_LEFT ,
    DARKNUT_RIGHT ,

    STALFOS (true),    //texture virtuelle
    STALFOS_UP ,
    STALFOS_DOWN ,
    STALFOS_LEFT ,
    STALFOS_RIGHT ,

    MOLBLIN (true),    //texture virtuelle
    MOLBLIN_UP ,
    MOLBLIN_DOWN ,
    MOLBLIN_LEFT,
    MOLBLIN_RIGHT,

    LEECHER,

    LONK (true),       //texture virtuelle
    LONK_UP,
    LONK_DOWN,
    LONK_LEFT,
    LONK_RIGHT,

    DIAMONDBLOCK,
    ICEBLOCK,

    WALL_UP,
    WALL_DOWN,
    WALL_LEFT,
    WALL_RIGHT,
    WALL_UPLEFT,
    WALL_UPRIGHT,
    WALL_DOWNLEFT,
    WALL_DOWNRIGHT,

    SPARKLE_EFFECT_FRAME_1,
    SPARKLE_EFFECT_FRAME_2,
    SPARKLE_EFFECT_FRAME_3,
    SPARKLE_EFFECT_FRAME_4,
    SPARKLE_EFFECT_FRAME_5,
    SPARKLE_EFFECT_FRAME_6,
    SPARKLE_EFFECT_FRAME_7,
    SPARKLE_EFFECT_FRAME_8,

    POP_EFFECT_FRAME_1,
    POP_EFFECT_FRAME_2,
    POP_EFFECT_FRAME_3,
    POP_EFFECT_FRAME_4,

    APPEAR_EFFECT_FRAME_1,
    APPEAR_EFFECT_FRAME_2,
    APPEAR_EFFECT_FRAME_3,
    APPEAR_EFFECT_FRAME_4,

    DISAPPEAR_EFFECT_FRAME_1,
    DISAPPEAR_EFFECT_FRAME_2,
    DISAPPEAR_EFFECT_FRAME_3,
    DISAPPEAR_EFFECT_FRAME_4;

    private boolean virtual;

    TextureID(boolean virtual)
    {
        this.virtual = virtual;
    }

    TextureID()
    {
        this.virtual = false;
    }

    public boolean isVirtual()
    {
        return this.virtual;
    }
}
