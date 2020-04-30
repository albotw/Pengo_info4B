package com.generic.launcher;

import com.generic.UI.LauncherUI;
import com.generic.gameplay.Game;
import com.generic.player.PlayerContainer;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.util.ArrayList;

public class Launcher extends JFrame
{
    public static Launcher instance;

    private LauncherUI UI;
    private PlayerManager pm;

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

        pm = new PlayerManager();
    }

    public void SoloModeSelected()
    {
        System.out.println("Solo séléctionné");
        if (pm.isMainProfileChosen())
        {
            this.setVisible(false);
            Game g = new Game();
        }
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
        ProfileDialog modal = new ProfileDialog(this, true);

        if (pm.getMainProfile() != null)
        {
            UI.updateProfileMode(pm.getMainProfile().getPseudo());
        }
    }

    public void onGameEnded()
    {
        this.setVisible(true);
    }
}
