package com.generic.UI;

import com.generic.gameplay.Game;
import com.generic.player.Player;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.OVERLAY_WIDTH;

public class GameOverlay extends JPanel{

    private Game g = Game.instance;
    private ArrayList<PlayerPanel> panels;

    public GameOverlay(Window w)
    {
        super();
        setSize(OVERLAY_WIDTH, GRID_HEIGHT);
        w.add(this, BorderLayout.EAST);
        setLayout(new GridLayout(4, 0));
        panels = new ArrayList<PlayerPanel>();
        if (PlayerManager.instance != null)
        {
            for(Player p : PlayerManager.instance.getPlayers())
            {
                PlayerPanel pp = new PlayerPanel(p);
                add(pp);
                panels.add(pp);
            }
        }
    }

    public void updateValues()
    {
        for(PlayerPanel pp : panels)
        {
            pp.updateScore();
            pp.updateVies();
        }
    }
}


