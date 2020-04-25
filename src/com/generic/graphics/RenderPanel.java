package com.generic.graphics;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {

    private SpriteManager sm = SpriteManager.instance;

    public RenderPanel()
    {
        super();
    }

    private void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.black);

        for (int i = 0; i < sm.getBSize(); i++){
            Sprite sp = sm.getSprite(i, "background");
            if (sp != null) g2d.drawImage(sp.getTexture(), sp.getX(), sp.getY(), this);
        }

        for (int i = 0; i < sm.getFSize(); i++)
        {
            Sprite sp = sm.getSprite(i, "foreground");
            if (sp != null) g2d.drawImage(sp.getTexture(), sp.getX(), sp.getY(), this);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

}