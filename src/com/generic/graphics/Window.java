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

import javax.swing.*;

public class Window extends JFrame
{
    public Window(int width, int height)
    {
        this.setSize(width, height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

}
