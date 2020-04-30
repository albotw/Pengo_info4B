package com.generic.launcher;

import com.generic.UI.LauncherUI;
import com.generic.player.PlayerContainer;

import javax.swing.*;
import java.util.ArrayList;

public class Launcher extends JFrame
{
    public ArrayList<PlayerContainer> players;

    public static Launcher instance;

    private LauncherUI UI;

    public Launcher()
    {
        super();
        instance = this;
        UI = new LauncherUI();
        add(UI);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Pengo Launcher");
        setSize(600, 400);

        players = new ArrayList<PlayerContainer>();
        players.add(new PlayerContainer("IAN"));
        players.add(new PlayerContainer("GEO"));
        players.add(new PlayerContainer("MIC"));
        players.add(new PlayerContainer("PED"));
    }

    public ArrayList<PlayerContainer> getPlayers()
    {
        return this.players;
    }


    public void SoloModeSelected()
    {
        System.out.println("Solo séléctionné");
    }

    public void SoloSettingsSelected()
    {
        System.out.println("Réglages solo séléctionnés");
    }

    public void MultiModeSelected()
    {
        System.out.println("Mode Multi séléctionné");
    }

    public void ProfileModeSelected()
    {
        System.out.println("Profil séléctionné");
    }
}
