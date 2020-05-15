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

public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image texture;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void getImageDimensions() {
        this.width = this.texture.getWidth(null);
        this.height = this.texture.getHeight(null);
    }

    public Image getTexture() {
        return this.texture;
    }

    public void loadImage(String dir) {
        ImageIcon ii = new ImageIcon(dir);
        this.texture = ii.getImage();
        getImageDimensions();
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
}