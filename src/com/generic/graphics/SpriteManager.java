package com.generic.graphics;

import com.generic.core.MapObject;
import com.generic.gameplay.GameMap;

import java.util.ArrayList;

import static com.generic.gameplay.config.CONFIG.*;

//Singleton OK

public class SpriteManager {
    public static SpriteManager instance;

    private ArrayList<Sprite> foreground;
    private ArrayList<Sprite> background;

    private SpriteManager() {
        instance = this;
        foreground = new ArrayList<Sprite>();
        background = new ArrayList<Sprite>();
        generateBackground();
    }

    public static SpriteManager createSpriteManager() {
        if (instance == null) {
            instance = new SpriteManager();
        }

        return instance;
    }

    public static void flushSprites()
    {
        instance.foreground.clear();
        instance.background.clear();
    }

    public synchronized void addSprite(Sprite spr) {
        foreground.add(spr);
    }

    public synchronized Sprite getSprite(int index, String position) {
        if (position == "foreground" && !foreground.isEmpty()) {
            return foreground.get(index);
        } else if (position == "background" && !background.isEmpty()) {
            return background.get(index);
        } else {
            return null;
        }
    }

    public int getFSize() {
        return foreground.size();
    }

    public int getBSize() {
        return background.size();
    }

    /**
     * Optimisation très probable.
     * On peut imaginer qu'en stockant la composition précédente de la carte
     * on peut actualiser juste les mapobjects modifiés et non régénérer une tonne de sprites a chaque fois.
     */

    public static void transfer(GameMap m, RenderThread rt)
    {
        if (m != null)
        {
            instance.foreground.clear();
            for (int i = 0; i < GRID_HEIGHT; i++)
            {
                for (int j = 0; j < GRID_WIDTH; j++)
                {
                    MapObject tmp = m.getAt(j, i);
                    if (tmp != null)
                    {
                        instance.addSprite(tmp.getSprite());
                    }
                }
            }
        }
    }

    public void generateBackground()
    {
        for (int i = 0; i <= GRID_HEIGHT+1; i++)
        {
            for (int j = 0; j <= GRID_WIDTH+1; j++)
            {
                Sprite spr = null;
                if (i == 0)
                {
                    spr = new Sprite(j * SPRITE_SIZE, 0);
                    if (j == 0)
                    {
                        spr.loadImage("WA_HG");
                    }
                    else if (j == GRID_WIDTH+1)
                    {
                        spr.loadImage("WA_HD");
                    }
                    else
                    {
                        spr.loadImage("WallH");
                    }
                }
                else if (i == GRID_HEIGHT +1)
                {
                    spr = new Sprite(j * SPRITE_SIZE, i * SPRITE_SIZE);
                    if (j == 0)
                    {
                        spr.loadImage("WA_BG");
                    }
                    else if (j == GRID_WIDTH +1)
                    {
                        spr.loadImage("WA_BD");
                    }
                    else
                    {
                        spr.loadImage("WallB");
                    }
                }
                else if (j == 0)
                {
                    spr = new Sprite(0, i * SPRITE_SIZE);
                    spr.loadImage("WallG");
                }
                else if (j == GRID_WIDTH + 1)
                {
                    spr = new Sprite(j * SPRITE_SIZE, i * SPRITE_SIZE);
                    spr.loadImage("WallD");
                }

                if (spr != null)
                {
                    instance.background.add(spr);
                }
            }
        }
    }
    /*
    public static void transfer_old(GameMap m, RenderThread rt) {
        if (m != null) {
            instance.flushSprites();

            if (!LOW_RES_MODE) {
                Sprite bg = new Sprite(SPRITE_SIZE_HD, SPRITE_SIZE_HD);
                bg.loadImage("ressources/gameBg.png");
                instance.addSprite(bg, "background");
            }

            int xpos = 0;
            int ypos = 0;
            for (int i = -1; i <= GRID_HEIGHT; i++) {
                xpos = 0;
                for (int j = -1; j <= GRID_WIDTH; j++) {
                    if (i != -1 && i != GRID_HEIGHT && j != -1 && j != GRID_WIDTH) {
                        if (!m.getAt(j, i).getType().equals("void")) {
                            MapObject tmp = m.getAt(j, i);
                            Sprite spr = null;
                            if (tmp.getTransitionState())
                            {
                                switch (tmp.getOrientation())
                                {
                                    case "W":
                                    {
                                        int old_pos = xpos + SPRITE_SIZE_HD;
                                        int current_step_pos = old_pos - tmp.getSubpixel();
                                        spr = new Sprite(current_step_pos, ypos);
                                        tmp.setSubpixel(tmp.getSubpixel() + SubPixel.step);

                                        if (tmp.getSubpixel() >= SPRITE_SIZE_HD)
                                        {
                                            tmp.setTransitionState(false);
                                        }
                                        break;
                                    }

                                    case "E":
                                    {
                                        int old_pos = xpos - SPRITE_SIZE_HD;
                                        int current_step_pos = old_pos + tmp.getSubpixel();
                                        spr = new Sprite(current_step_pos, ypos);
                                        tmp.setSubpixel(tmp.getSubpixel() + SubPixel.step);

                                        if (tmp.getSubpixel() >= SPRITE_SIZE_HD)
                                        {
                                            tmp.setTransitionState(false);
                                        }
                                        break;
                                    }

                                    case "N":
                                    {
                                        int old_pos = ypos + SPRITE_SIZE_HD;
                                        int current_step_pos = old_pos - tmp.getSubpixel();
                                        spr = new Sprite(xpos, current_step_pos);
                                        tmp.setSubpixel(tmp.getSubpixel() + SubPixel.step);

                                        if (tmp.getSubpixel() >= SPRITE_SIZE_HD)
                                        {
                                            tmp.setTransitionState(false);
                                        }
                                        break;
                                    }

                                    case "S":
                                    {
                                        int old_pos = ypos - SPRITE_SIZE_HD;
                                        int current_step_pos = old_pos + tmp.getSubpixel();
                                        spr = new Sprite(xpos, current_step_pos);
                                        tmp.setSubpixel(tmp.getSubpixel() + SubPixel.step);

                                        if (tmp.getSubpixel() >= SPRITE_SIZE_HD)
                                        {
                                            tmp.setTransitionState(false);
                                        }
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                spr = new Sprite(xpos, ypos);
                            }

                            String dir = "";

                            if (LOW_RES_MODE) {
                                dir = "SD/";
                            } else {
                                dir = "HD/";
                            }

                            if (tmp.getType().equals("IceBlock")) {
                                spr.loadImage("ressources/" + dir + "IceBlock.png");
                            }
                            else if (tmp.getType().equals("Penguin"))
                            {
                                if (LOW_RES_MODE) {
                                    spr.loadImage("ressources/" + dir + "Penguin.png");
                                } else {
                                    spr.loadImage("ressources/" + dir + "Lonk_" + tmp.getOrientation() + ".png");
                                }
                            }
                            else if (m.getAt(j, i).getType().equals("DiamondBlock"))
                            {
                                spr.loadImage("ressources/" + dir + "DiamondBlock.png");
                            }
                            else if (m.getAt(j, i).getType().equals("Animal"))
                            {
                                if (LOW_RES_MODE) {
                                    spr.loadImage("ressources/" + dir + "Animal.png");
                                } else {
                                    Variante v = (Variante)m.getAt(j, i);
                                    spr.loadImage("ressources/" + dir + "/" + v.getVariante() + "/" + tmp.getOrientation() + ".png");
                                }
                            }
                            instance.addSprite(spr, "foreground");
                        }
                    } else {
                        Sprite spr = new Sprite(xpos, ypos);

                        String dir = "";

                        if (LOW_RES_MODE) {
                            dir = "SD/";
                        } else {
                            dir = "HD/";
                        }

                        if (i == -1) {
                            if (j == -1) spr.loadImage("ressources/" + dir + "WA_HG.png");
                            else if (j == GRID_WIDTH) spr.loadImage("ressources/" + dir + "WA_HD.png");
                            else spr.loadImage("ressources/" + dir + "WallH.png");
                        } else if (i == GRID_HEIGHT) {
                            if (j == -1) spr.loadImage("ressources/" + dir + "WA_BG.png");
                            else if (j == GRID_WIDTH) spr.loadImage("ressources/" + dir + "WA_BD.png");
                            else spr.loadImage("ressources/" + dir + "WallB.png");
                        } else if (j == -1) {
                            spr.loadImage("ressources/" + dir + "WallG.png");
                        } else if (j == GRID_WIDTH) {
                            spr.loadImage("ressources/" + dir + "WallD.png");
                        }
                        instance.addSprite(spr, "background");
                    }

                    if (LOW_RES_MODE) {
                        xpos += SPRITE_SIZE_SD;
                    } else {
                        xpos += SPRITE_SIZE_HD;
                    }

                }

                if (LOW_RES_MODE) {
                    ypos += SPRITE_SIZE_SD;
                } else {
                    ypos += SPRITE_SIZE_HD;
                }

            }
        }
    }*/
}
