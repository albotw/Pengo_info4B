package com.generic.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    private BufferedImage frame;
    public DrawPanel()
    {
        super();
        frame = null;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(frame, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
    }

    public void pushFrame(BufferedImage bi)
    {
        frame = bi;
    }
}
