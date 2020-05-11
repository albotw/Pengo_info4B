package com.generic.graphics;

import com.generic.core.GameMap;
import com.generic.core.Orientation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.generic.gameplay.CONFIG.*;

//Singleton OK

public class SpriteManager {
    public static SpriteManager instance;

    private volatile ArrayList<Sprite> foreground;
    private volatile ArrayList<Sprite> background;

    private SpriteManager()
    {
        instance = this;
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
    public static void transfer(GameMap m, RenderThread rt)
    {
        if (m != null)
        {
            instance.flushSprites();

            int xpos = 0;
            int ypos = 0;
            for (int i = -1; i <= GRID_HEIGHT; i++)
            {
                xpos = 0;
                for (int j = -1; j <= GRID_WIDTH; j++)
                {
                    if (i != -1 && i != GRID_HEIGHT && j != -1 && j != GRID_WIDTH)
                    {
                        if (!m.getAt(j, i).getType().equals("void"))
                        {
                            Sprite spr = new Sprite(xpos, ypos);
                            String dir = "";

                            if (LOW_RES_MODE)
                            {
                                dir = "SD/";
                            }
                            else
                            {
                                dir = "HD/";
                            }

                            if(m.getAt(j, i).getType().equals("IceBlock"))
                            {
                                spr.loadImage("src/ressources/"+ dir + "IceBlock.png");
                            }
                            else if (m.getAt(j, i).getType().equals("Penguin"))
                            {
                                Orientation o = (Orientation)m.getAt(j, i);
                                spr.loadImage("src/ressources/" + dir+ "Lonk_"+o.getOrientation()+".png");
                            }
                            else if (m.getAt(j, i).getType().equals("DiamondBlock"))
                            {
                                spr.loadImage("src/ressources/" + dir + "DiamondBlock.png");
                            }
                            else if (m.getAt(j, i).getType().equals("Animal"))
                            {
                                spr.loadImage("src/ressources/" + dir + "Animal.png");
                            }
                            instance.addSprite(spr, "foreground");
                        }
                    }
                    else
                    {
                        Sprite spr = new Sprite(xpos, ypos);

                        String dir = "";

                        if (LOW_RES_MODE)
                        {
                            dir = "SD/";
                        }
                        else
                        {
                            dir = "HD/";
                        }

                        if (i == -1)
                        {
                            if (j == -1) spr.loadImage("src/ressources/" +dir + "WA_HG.png");
                            else if (j == GRID_WIDTH) spr.loadImage("src/ressources/"+ dir +"WA_HD.png");
                            else spr.loadImage("src/ressources/"+dir+"WallH.png");
                        }
                        else if (i == GRID_HEIGHT)
                        {
                            if(j == -1) spr.loadImage("src/ressources/"+dir+"WA_BG.png");
                            else if (j == GRID_WIDTH) spr.loadImage("src/ressources/"+dir+"WA_BD.png");
                            else spr.loadImage("src/ressources/"+dir+"WallB.png");
                        }
                        else if (j == - 1)
                        {
                            spr.loadImage("src/ressources/"+dir+"WallG.png");
                        }
                        else if (j == GRID_WIDTH)
                        {
                            spr.loadImage("src/ressources/"+dir+"WallD.png");
                        }
                        instance.addSprite(spr, "background");
                    }

                    if (LOW_RES_MODE)
                    {
                        xpos += SPRITE_SIZE_SD;
                    }
                    else
                    {
                        xpos += SPRITE_SIZE_HD;
                    }

                }

                if (LOW_RES_MODE)
                {
                    ypos += SPRITE_SIZE_SD;
                }
                else
                {
                    ypos += SPRITE_SIZE_HD;
                }

            }
        }
    }
}
