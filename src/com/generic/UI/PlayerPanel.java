package com.generic.UI;

import com.generic.player.Player;

import javax.swing.*;
import java.awt.*;

class PlayerPanel extends JPanel
{
    private JLabel pseudoLabel;
    private JLabel score;
    private JLabel vies;

    private Player p;

    public PlayerPanel(Player p)
    {
        super();
        this.p = p;
        setLayout(new FlowLayout());
        pseudoLabel = new JLabel(p.getPseudo());
        add(pseudoLabel);
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2, 2));
        center.add(new JLabel("Score"));
        score = new JLabel("0");
        center.add(score);
        center.add(new JLabel("Vies"));
        vies = new JLabel("0");
        center.add(vies);
        add(center);
    }

    public void updateScore(){ this.score.setText(Integer.toString(p.getScore().getPoints()));}

    public void updateVies(){ this.vies.setText(Integer.toString(p.getRemainigLives()));}
}