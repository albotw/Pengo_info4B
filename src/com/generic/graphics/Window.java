/**
 * TODO: rendre l'utilisation indépendante de l'overlay pour permettre une utilisation avec Launcher
 */

package com.generic.graphics;

import com.generic.UI.GameOverlay;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame
{
    private GameOverlay go;
    public Window(int width, int height)
    {

        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        go = new GameOverlay(this);
    }

    public GameOverlay getGameOverlay()
    {
        return this.go;
    }

}
