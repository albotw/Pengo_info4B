/**
 * Nom de la classe: RenderPanel
 *
 * Description: JPanel effectuant le dessin de sprites contenus dans des array lists. Gérée par un RenderThread
 *
 * Version: 2.3
 *
 * Date: 08/03/2020
 *
 * Auteur: Yann
 */

package com.generic.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderPanel extends JPanel {
    private ArrayList<Sprite> foregroundPile;
    private ArrayList<Sprite> backgroundPile;

    public RenderPanel(){
        foregroundPile = new ArrayList<Sprite>();
        backgroundPile = new ArrayList<Sprite>();
    }


    private void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.black);

        for (Sprite sp : backgroundPile){
            g2d.drawImage(sp.getTexture(), sp.getX(), sp.getY(), this);
        }

        for (Sprite sp : foregroundPile)
        {
            g2d.drawImage(sp.getTexture(), sp.getX(), sp.getY(), this);
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public ArrayList<Sprite> getForegroundPile()
    {
        return foregroundPile;
    }

    public ArrayList<Sprite> getBackgroundPile()
    {
        return backgroundPile;
    }
}