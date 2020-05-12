package com.generic.UI;

import com.generic.gameplay.AbstractPlayer;
import com.generic.gameplay.LocalGame;
import com.generic.launcher.Launcher;
import com.generic.gameplay.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

import static com.generic.gameplay.CONFIG_GAME.CLIENT;

public class GameOverlay extends JPanel {
    private String pseudo;
    private int score;
    private int vies;
    private int remainigEnemies;

    private Image background;

    private AbstractPlayer player;

    private Font police;

    public GameOverlay() {
        super();
        this.setPreferredSize(new Dimension(750, 50));
        ImageIcon ii = new ImageIcon("src/ressources/GameOverlayWindow.png");
        this.background = ii.getImage();

        pseudo = "Yann";
        score = 0;
        vies = 5;
        remainigEnemies = 8;

        setBackground(Color.BLACK);
    }

    public void update(String[] params) {
        System.out.println("application commande");
        if (params[0].equals("POINTS"))
        {
            this.score = Integer.parseInt(params[1]);
        }
        else if (params[0].equals("VIES"))
        {
            this.vies = Integer.parseInt(params[1]);
        }
    }

    public void draw(Graphics g)
    {
        if (background != null)
            g.drawImage(this.background, 0, 0, this);

        g.setColor(Color.WHITE);
        g.drawString(pseudo, 15, 15);
        g.drawString(""+score + " Points", 100, 15);
        g.drawString("" +vies + " Vies", 200, 15);
        g.drawString(""+remainigEnemies + " à tuer", 300, 15);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}

/**
 *
 */
