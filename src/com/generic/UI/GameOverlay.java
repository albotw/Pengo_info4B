package com.generic.UI;

import com.generic.launcher.Launcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameOverlay extends JPanel {
    private String pseudo = "";
    private int score = 0;
    private int vies = 0;
    private int remainigEnemies = 0;

    private boolean showRemainingEnemies = true;

    private Image background;

    private Font police;

    public GameOverlay() {
        super();
        this.setPreferredSize(new Dimension(750, 50));
        ImageIcon ii = new ImageIcon("ressources/GameOverlayWindow.png");
        this.background = ii.getImage();

        try {
            police = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/police.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBackground(Color.BLACK);
    }

    public void updateLocal() {
        pseudo = Launcher.instance.getMainProfile().getPseudo();
        score = Launcher.instance.getMainProfile().getPoints();
        vies = Launcher.instance.getMainProfile().getRemainigLives();
        if (Launcher.instance.getGame() != null) {
            remainigEnemies = Launcher.instance.getGame().getAIlives();
        }
    }

    public void draw(Graphics g) {
        if (background != null)
            g.drawImage(this.background, 0, 0, this);

        g.setFont(police.deriveFont(14f));
        g.setColor(Color.WHITE);
        g.drawString(pseudo, 20, 30);
        g.drawString("" + score + " Points", 200, 30);
        g.drawString("" + vies + " Vies", 400, 30);
        if (showRemainingEnemies) g.drawString("" + remainigEnemies + " à tuer", 600, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setVies(int vies) {
        this.vies = vies;
    }

    public void setRemainigEnemies(int remainigEnemies) {
        this.remainigEnemies = remainigEnemies;
    }

    public void setShowRemainingEnemies(boolean showRemainingEnemies) {
        this.showRemainingEnemies = showRemainingEnemies;
    }
}
