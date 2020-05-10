package com.generic.UI;

import com.generic.gameplay.LocalGame;
import com.generic.player.Player;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;

import static com.generic.gameplay.CONFIG_GAME.CLIENT;

public class GameOverlay extends JPanel {

    private LocalGame g;

    private String pseudo;
    private int score;
    private int vies;
    private int remainigEnemies;

    private Image background;

    private Player localPlayer;

    public GameOverlay() {
        super();
        this.setPreferredSize(new Dimension(750, 50));
        //ImageIcon ii = new ImageIcon("src/ressources/PlayerBar.png");
        //this.background = ii.getImage();

        pseudo = "YANN";
        score = 2000;
        vies = 5;
        remainigEnemies = 8;

        if (!CLIENT)
        {
            localPlayer = PlayerManager.instance.getMainProfile();
            g = (LocalGame) LocalGame.instance;
        }

        setBackground(Color.BLACK);
    }

    public void update() {
        Player p = PlayerManager.instance.getMainProfile();
        if (p != null)
        {
            pseudo = p.getPseudo();
            score = p.getPoints();
            vies= p.getRemainigLives();
            if (CLIENT == false)
            {
                remainigEnemies = ((LocalGame)(LocalGame.instance)).getAIlives();
            }
        }
    }

    public void draw(Graphics g)
    {
        if (background != null)
            g.drawImage(this.background, 0, 0, this);
        //g.setfont();
        g.setColor(Color.GREEN);
        g.drawString(pseudo, 15, 15);
        g.drawString(""+score + " POINTS", 100, 15);
        g.drawString("" +vies + " VIES", 200, 15);
        g.drawString(""+remainigEnemies + " A TUER", 300, 15);
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
