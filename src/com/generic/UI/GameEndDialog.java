package com.generic.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GameEndDialog extends JDialog {

    private ImagePanel img;
    public GameEndDialog(Frame parent, boolean modal, boolean victoire){
            super(parent, modal);

            if (victoire) {
                ImageIcon victory = new ImageIcon("src/ressources/victoire.png");
                img = new ImagePanel(victory.getImage());
                System.out.println(victory.toString());
            } else {
                ImageIcon defeat = new ImageIcon("src/ressources/defeat.png");
                img = new ImagePanel(defeat.getImage());
            }
            img.setBounds(0, 0, 600, 600);
            add(img);
            setLayout(new BorderLayout());
            setSize(537,523);
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
