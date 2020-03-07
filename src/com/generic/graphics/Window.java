package com.generic.graphics;

import javax.swing.*;

public class Window extends JFrame
{
    private DrawPanel dp;
    private int width;
    private int height;

    public Window(int width, int height)
    {
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}
