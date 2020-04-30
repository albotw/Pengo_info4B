package com.generic.UI;

import com.generic.gameplay.Game;
import com.generic.launcher.Launcher;
import com.generic.player.PlayerContainer;
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
        for(PlayerContainer pc : PlayerManager.instance.getPlayersProfiles())
        {
            System.out.println(pc.getPseudo().toString());
            PlayerPanel pp = new PlayerPanel(pc);
            add(pp);
            panels.add(pp);
        }
    }

    public void updateValues()
    {
        for(PlayerPanel pp : panels)
        {
            if (g.getScore() != null)
            {
                pp.updateScore(g.getScore().getPoints());
            }
        }
    }
}


