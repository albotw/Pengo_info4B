package com.generic.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;

public class GameEndDialog extends JDialog {

    public GameEndDialog(Frame parent, boolean modal, boolean victoire, int temps, int score){
        super(parent, modal);

        GEDPanel panel = new GEDPanel(victoire, temps, score);
        panel.setBounds(0, 0, 500, 250);
        add(panel);
        setBackground(Color.BLACK);
        setSize(515,290);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Fin de la partie");
        repaint();
    }

    public void Fermer () {
        this.setVisible(false);
        this.dispose();
    }
}
