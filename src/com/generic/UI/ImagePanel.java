package com.generic.UI;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    protected Image img;
    public ImagePanel(){
        super();
        this.img = null;
    }

    public ImagePanel(Image im){
        super();
        this.img = im;
        //this.repaint();
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        this.repaint();
    }

    public void draw(Graphics g)
    {
        if (img != null){
            g.drawImage(img, 0, 0,null);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public String toString() {
        return "PanneauImage{" + "img=" + img + '}';
    }
}
