package com.generic.graphics;

import javax.swing.*;
import java.awt.*;

public class  Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image texture;

    public Sprite(int x, int y){
        this.x = x;
        this.y = y;
    }

    protected void getImageDimensions(){
        this.width = this.texture.getWidth(null);
        this.height = this.texture.getHeight(null);
    }

    public Image getTexture()
    {
        return this.texture;
    }

    public void loadImage(String dir)
    {
        ImageIcon ii = new ImageIcon(dir);
        this.texture = ii.getImage();
        getImageDimensions();
        System.out.println("Loaded img: " + dir + " [" + width + "px * " + height + "px]");
    }

    public int getX(){return x;}
    public void setX(int x){ this.x = x;}
    public int getY(){return y;}
    public void setY(int y){this.y = y;}
    public Rectangle getBounds(){return new Rectangle(x, y, width, height);}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
}