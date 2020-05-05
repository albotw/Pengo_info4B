package com.generic.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameEndDialog extends JDialog {

    private ImagePanel img;
    public GameEndDialog(Frame parent, boolean modal, boolean victoire){
            super(parent, modal);

            if (victoire) {
                ImageIcon victory = new ImageIcon("src/resources/victoire.png");
                img = new ImagePanel(victory.getImage());
                System.out.println(victory.toString());
            } else {
                ImageIcon defeat = new ImageIcon("src/ressource/defeat.png");
                img = new ImagePanel(defeat.getImage());
            }
            img.setBounds(0, 0, 580, 170);
            setLayout(null);
            add(img);
            setSize(580, 170);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            repaint();

        }
        public void Fermer () {
            this.setVisible(false);
            this.dispose();
        }


}
