package com.generic.launcher;

import com.generic.UI.LauncherUI;
import com.generic.gameplay.LocalGame;
import com.generic.utils.ScorePair;
import com.generic.player.PlayerManager;

import javax.swing.*;

public class Launcher extends JFrame
{
    public static Launcher instance;

    private LauncherUI UI;
    private PlayerManager pm;
    private Leaderboard l;

    public  Launcher()
    {
        super();
        instance = this;

        UI = new LauncherUI();
        add(UI);
        setTitle("Pengo Launcher");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        pm = new PlayerManager();
        l = new Leaderboard();
        l.pull();

        l.addToLeaderboard(new ScorePair("Yann", 100000, true));

        l.push();
        l.print();
    }

    public void SoloModeSelected()
    {
        System.out.println("Solo séléctionné");
        if (pm.isMainProfileChosen())
        {
            this.setVisible(false);
            LocalGame g = new LocalGame();
        }
    }

    public void SoloSettingsSelected()
    {
        System.out.println("Réglages solo séléctionnés");
        SettingsDialog SD = new SettingsDialog(this,true);
    }

    public void MultiModeSelected()
    {
        System.out.println("Mode Multi séléctionné");
        Online OD = new Online(this,true);
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

    public Leaderboard getLeaderboard()
    {
        return this.l;
    }

    public void leaderboardSelected()
    {
        System.out.println("Leaderboard séléctionné");
        LeaderboardDialog modal = new LeaderboardDialog(this, true);
    }

    public void onGameEnded()
    {
        this.setVisible(true);
    }
}
