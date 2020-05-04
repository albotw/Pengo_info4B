package com.generic.graphics;

import com.generic.UI.GameOverlay;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
    public Window(int width, int height)
    {

        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

}
