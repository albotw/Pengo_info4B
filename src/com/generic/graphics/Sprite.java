/**
 * Nom de la classe: Sprite
 * <p>
 * Description: classe conteneur pour une image devant être affichée.
 * <p>
 * Version: 2.0
 * <p>
 * Date: 08/03/2020
 * <p>
 * Auteur: Yann
 */

package com.generic.graphics;

import javax.swing.*;
import java.awt.*;

import static com.generic.gameplay.config.CONFIG.*;

public class Sprite {
    protected int x;
    protected int y;
    protected Image texture;
    protected Image[] orientations; // N, S, E, W
    protected boolean hasOrientation;

    public Sprite(){ }

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        hasOrientation = false;
    }

    public void setPosition(int x, int y)
    {
        // on rajoute sprite size pour avoir de la place pour la bordure.
        this.x = SPRITE_SIZE + x * SPRITE_SIZE;
        this.y = SPRITE_SIZE + y * SPRITE_SIZE;
    }

    public void setOrientation(char orientation)
    {
        switch(orientation)
        {
            case 'N': this.texture = orientations[0]; break;
            case 'S': this.texture = orientations[1]; break;
            case 'E': this.texture = orientations[2]; break;
            case 'W': this.texture = orientations[3]; break;
        }
    }

    public Image getTexture() {
        return this.texture;
    }

    public void loadImage(String ressourceType) {
        if (!hasOrientation)
        {
            String dir = "ressources/" + ressourceType + ".png";
            ImageIcon ii = new ImageIcon(dir);
            this.texture = ii.getImage();
        }
        else
        {
            String dir="ressources/"+ressourceType;
            ImageIcon N = new ImageIcon(dir+"/N.png");
            ImageIcon S = new ImageIcon(dir+"/S.png");
            ImageIcon E = new ImageIcon(dir+"/E.png");
            ImageIcon W = new ImageIcon(dir+"/W.png");

            orientations[0] = N.getImage();
            orientations[1] = S.getImage();
            orientations[2] = E.getImage();
            orientations[3] = W.getImage();

            this.texture = orientations[0];

            N = null;
            S = null;
            E = null;
            W = null;
        }

        //System.out.println("Loaded img: " + dir + " [" + width + "px * " + height + "px]");
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void toggleOrientation() {
        this.hasOrientation = true;
        this.orientations = new Image[4];
    }
}