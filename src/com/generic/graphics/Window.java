/**
 * Nom de la classe: RenderThread
 *
 * Description: thread responsable de l'affichage et de la gestion de sprites dans un JPanel
 *
 * Version: 1.0
 *
 * Date: 08/03/2020
 *
 * Auteur: Yann
 */

package com.generic.graphics;

import com.generic.UI.GameOverlay;

import javax.swing.*;
import javax.swing.border.Border;
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
