package com.generic.gameplayClasses;

import com.generic.coreClasses.Map;
import com.generic.graphics.RenderThread;
import com.generic.graphics.Sprite;

import static com.generic.utils.CONFIG.*;

public class MapToRender {
    public static void transfer(Map m, RenderThread rt)
    {
        rt.flushRenderPile();

        int xpos = 0;
        int ypos = 0;
        for (int i = 0; i < GRID_HEIGHT; i++)
        {
            xpos = 0;
            for (int j = 0; j < GRID_WIDTH; j++)
            {
                if (m.getAt(j, i) != null)
                {
                    if(m.getAt(j, i).getType().equals("IceBlock"))
                    {
                        Sprite spr = new Sprite(xpos, ypos);
                        spr.loadImage("src/resources/IceBlock.png");
                        rt.addToRenderPile(spr, "foreground");
                    }
                }

                xpos += SPRITE_SIZE;
            }

            ypos += SPRITE_SIZE;
        }
    }
}
