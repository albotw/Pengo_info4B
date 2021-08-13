package graphics;

import core.MapObject;
import gameplay.GameMap;
import graphics.effects.Effect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static config.CONFIG.*;

public class SpriteManager extends JPanel{
    public static SpriteManager instance;
    private final ArrayList<Sprite> background;
    private ArrayList<Effect> effects;

    private HashMap<TextureID, Image> textureAtlas;

    private GameMap map;

    private SpriteManager(GameMap m){
        super();
        this.setBackground(BG_DEFAULT_COLOR);

        instance = this;
        textureAtlas = new HashMap<TextureID, Image>();
        background = new ArrayList<Sprite>();
        effects = new ArrayList<Effect>();
        map = m;

        loadTextures();
        generateBackground();
    }

    public static SpriteManager createSpriteManager(GameMap m) {
        if (instance == null) {
            instance = new SpriteManager(m);
        }

        return instance;
    }

    public void cleanSprites()
    {
        background.clear();
        textureAtlas.clear();
        effects.clear();
        map = null;
    }

    private void loadTextures()
    {
        //TODO: permettre le chargement de sets de textures ?
        for(TextureID tID : TextureID.values())
        {
            String dir = "ressources/GAME/" + tID.name() + ".png";
            ImageIcon ii = new ImageIcon(dir);
            textureAtlas.put(tID, ii.getImage());
            ii = null;
        }
        System.out.println("loaded " + textureAtlas.values().size() + " textures");
    }

    private void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.black);

        //dessin du fond
        for (Sprite spr : background)
        {
            g2d.drawImage(textureAtlas.get(spr.getTexture()), spr.x, spr.y, this);
        }

        //dessin de la carte
        if (map != null)
        {
            for (int i = 0; i < GRID_HEIGHT; i++)
            {
                for (int j = 0; j < GRID_WIDTH; j++)
                {
                    MapObject tmp = map.getAt(j, i);
                    if (tmp != null)
                    {
                        Sprite spr = tmp.getSprite();
                        if (spr != null)
                        {
                            g2d.drawImage(textureAtlas.get(spr.getTexture()), spr.x, spr.y, this);
                        }
                    }
                }
            }
        }

        //dessin des effets
        Iterator<Effect> iter = effects.iterator();
        while (iter.hasNext())
        {
            Effect e = iter.next();
            e.update(RENDER_TICK);
            if (e.isDone())
            {
                e.clean();
                iter.remove();
            }
            else
            {
                g2d.drawImage(textureAtlas.get(e.getFrame()), e.getX(), e.getY(), null);
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
                    spr = new Sprite(j * SPRITE_SIZE, 0, null);
                    if (j == 0)
                    {
                        spr.setTexture(TextureID.WALL_UPLEFT);
                    }
                    else if (j == GRID_WIDTH+1)
                    {
                        spr.setTexture(TextureID.WALL_UPRIGHT);
                    }
                    else
                    {
                        spr.setTexture(TextureID.WALL_UP);
                    }
                }
                else if (i == GRID_HEIGHT +1)
                {
                    spr = new Sprite(j * SPRITE_SIZE, i * SPRITE_SIZE, null);
                    if (j == 0)
                    {
                        spr.setTexture(TextureID.WALL_DOWNLEFT);
                    }
                    else if (j == GRID_WIDTH +1)
                    {
                        spr.setTexture(TextureID.WALL_DOWNRIGHT);
                    }
                    else
                    {
                        spr.setTexture(TextureID.WALL_DOWN);
                    }
                }
                else if (j == 0)
                {
                    spr = new Sprite(0, i * SPRITE_SIZE, TextureID.WALL_LEFT);
                }
                else if (j == GRID_WIDTH + 1)
                {
                    spr = new Sprite(j * SPRITE_SIZE, i * SPRITE_SIZE, TextureID.WALL_RIGHT);
                }

                if (spr != null)
                {
                    background.add(spr);
                }
            }
        }
    }

    public void addEffect(Effect e)
    {
        effects.add(e);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.getWidth(), this.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
        Toolkit.getDefaultToolkit().sync();
    }
}
