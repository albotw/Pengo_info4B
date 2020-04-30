package com.generic.UI;

import com.generic.player.PlayerContainer;

import javax.swing.*;
import java.awt.*;

class PlayerPanel extends JPanel
{
    JLabel pseudoLabel;
    JLabel score;
    JLabel vies;

    public PlayerPanel(PlayerContainer pc)
    {
        super();
        setLayout(new FlowLayout());
        pseudoLabel = new JLabel(pc.getPseudo());
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

    public void updateScore(int newScore)
    {
        score.setText(Integer.toString(newScore));
    }

    public void updateVies(int newVies)
    {
        vies.setText(Integer.toString(newVies));
    }
}