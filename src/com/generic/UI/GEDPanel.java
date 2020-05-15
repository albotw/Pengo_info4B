package com.generic.UI;

import com.generic.gameplay.CONFIG;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GEDPanel extends JPanel {

    private Image bg;
    private String message;
    private String timeMessage;
    private String scoreMessage;
    private String totalScoreMessage;
    private Font police;
    private int timeScore;

    public GEDPanel(boolean victoire, int temps, int score) {
        super();

        if (temps <= 20) {
            timeScore = 5000;
        } else if (temps > 20 && temps <= 29) {
            timeScore = 2000;
        } else if (temps > 29 && temps <= 39) {
            timeScore = 1000;
        } else if (temps > 39 && temps <= 60) {
            timeScore = 500;
        } else
            timeScore = 150;

        try {
            police = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/police.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon("ressources/GameEndBg.png");
        bg = icon.getImage();

        if (victoire) {
            message = "Victoire !";
            timeMessage = "Tu as fini en " + temps + " secondes";
            scoreMessage = "" + timeScore + " points supplémentaires !";

        } else {
            message = "Défaite...";
            timeMessage = "Tu as résisté " + temps + " secondes";
            scoreMessage = "";
        }

        totalScoreMessage = "Total: " + score + " points.";

        setBackground(CONFIG.BG_DEFAULT_COLOR);
        repaint();
    }

    public void draw(Graphics g) {
        System.out.println("repaint fenêtre");
        g.drawImage(bg, 0, 0, this);
        g.setFont(police.deriveFont(32f));
        g.setColor(Color.WHITE);
        g.drawString(message, 100, 80);
        g.setFont(police.deriveFont(14f));
        g.drawString(timeMessage, 70, 140);
        g.drawString(scoreMessage, 50, 180);
        g.drawString(totalScoreMessage, 120, 220);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}
