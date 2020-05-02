package com.generic.UI;

import com.generic.gameplay.Game;
import com.generic.player.Player;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.generic.gameplay.CONFIG.*;

public class GameOverlay extends JPanel{

    private Game g = Game.instance;

    private JLabel points;
    private JLabel hiscore;
    private JLabel lives;
    private JLabel remainingEnemies;

    private Player localPlayer = g.getLocalPlayer();

    public GameOverlay()
    {
        super();
        setLayout(new GridLayout(2,1));

        JLabel OnePlayer = new JLabel("1P | "+ localPlayer.getPseudo());

        points = new JLabel("Score | 0");
        lives = new JLabel("Vies | " + localPlayer.getRemainigLives());
        remainingEnemies = new JLabel("Restants | " + g.getAIlives());
        hiscore = new JLabel("HI | 20000");

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(1, 3));
        top.add(OnePlayer);
        top.add(points);
        top.add(hiscore);

        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 2));
        bottom.add(lives);
        bottom.add(remainingEnemies);


        add(top);
        add(bottom);

        setBackground(Color.BLACK);
    }

    public void update()
    {
        points.setText("Score | " + localPlayer.getScore().getPoints());
        remainingEnemies.setText("Restants | " + g.getAIlives());
        lives.setText("Vies | " + localPlayer.getRemainigLives());
    }
}

/**
 * w.add(this);
 *
 */


