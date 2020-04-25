package com.generic.graphics;

import com.generic.coreClasses.Map;

import java.util.ArrayList;

import static com.generic.utils.CONFIG.*;
import static com.generic.utils.CONFIG.SPRITE_SIZE;

public class SpriteManager {
    public static SpriteManager instance;

    private volatile ArrayList<Sprite> foreground;
    private volatile ArrayList<Sprite> background;

    private SpriteManager()
    {
        foreground = new ArrayList<Sprite>();
        background = new ArrayList<Sprite>();
    }

    public static SpriteManager createSpriteManager()
    {
        if (instance == null)
        {
            instance = new SpriteManager();
        }

        return instance;
    }

    public synchronized void addSprite(Sprite spr, String position)
    {
        if (position == "foreground")
        {
            foreground.add(spr);
        }
        else if (position == "background")
        {
            background.add(spr);
        }
    }

    public synchronized void flushSprites()
    {
        foreground.clear();
        background.clear();
    }

    public synchronized Sprite getSprite(int index, String position)
    {
        if (position == "foreground" && !foreground.isEmpty())
        {
            return foreground.get(index);
        }
        else if (position == "background" && !background.isEmpty())
        {
            return background.get(index);
        }
        else
        {
            return null;
        }
    }

    public int getFSize()
    {
        return foreground.size();
    }

    public int getBSize()
    {
        return background.size();
    }

    //Optimisation probable
    public static void transfer(Map m, RenderThread rt)
    {
        if (m != null)
        {
            instance.flushSprites();

            int xpos = 0;
            int ypos = 0;
            for (int i = 0; i < GRID_HEIGHT; i++)
            {
                xpos = 0;
                for (int j = 0; j < GRID_WIDTH; j++)
                {
                    if (m.getAt(j, i) != null)
                    {
                        Sprite spr = new Sprite(xpos, ypos);
                        if(m.getAt(j, i).getType().equals("IceBlock"))
                        {
                            spr.loadImage("src/resources/IceBlock.png");
                        }
                        else if (m.getAt(j, i).getType().equals("Penguin"))
                        {
                            spr.loadImage("src/resources/Penguin.png");
                        }
                        else if (m.getAt(j, i).getType().equals("DiamondBlock"))
                        {
                            spr.loadImage("src/resources/DiamondBlock.png");
                        }
                        else if (m.getAt(j, i).getType().equals("Animal"))
                        {
                            spr.loadImage("src/resources/Animal.png");
                        }
                        instance.addSprite(spr, "foreground");
                    }

                    xpos += SPRITE_SIZE;
                }

                ypos += SPRITE_SIZE;
            }
        }

    }
}