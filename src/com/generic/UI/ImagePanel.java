package com.generic.UI;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    protected Image img;

    public ImagePanel(Image im) {
        super();
        this.img = im;
        //this.repaint();
    }

    public void draw(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, null);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public String toString() {
        return "PanneauImage{" + "img=" + img + '}';
    }
}
