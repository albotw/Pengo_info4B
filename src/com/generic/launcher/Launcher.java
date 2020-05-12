package com.generic.launcher;

import com.generic.UI.LauncherUI;
import com.generic.gameplay.LocalGame;
import com.generic.gameplay.Player;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.generic.gameplay.CONFIG_GAME.N_NIVEAUX;

public class Launcher extends JFrame
{
    public static Launcher instance;

    private LauncherUI UI;
    private Leaderboard localLeaderboard;

    private CopyOnWriteArrayList<Player> playerProfiles;
    private int mainProfile = - 1;

    private int currentLevel;

    public  Launcher()
    {
        super();
        instance = this;

        localLeaderboard = new Leaderboard();
        localLeaderboard.pull();
        localLeaderboard.push();

        playerProfiles = new CopyOnWriteArrayList<Player>();
        loadProfiles();

        UI = new LauncherUI();
        add(UI);
        setTitle("Pengo Launcher");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    // TRAITEMENT INTERFACE ----------
    public void SoloModeSelected()
    {
        System.out.println("Solo séléctionné");
        currentLevel = 0;
        if (isMainProfileChosen())
        {
            this.setVisible(false);
            currentLevel++;
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
        currentLevel = 0;
        System.out.println("Mode Multi séléctionné");
        Online OD = new Online(this,true);
    }

    public void ProfileModeSelected()
    {
        System.out.println("Profil séléctionné");
        ProfileDialog modal = new ProfileDialog(this, true);

        if (isMainProfileChosen())
        {
            UI.updateProfileMode(getMainProfile().getPseudo());
        }
    }

    public void leaderboardSelected()
    {
        System.out.println("Leaderboard séléctionné");
        LeaderboardDialog modal = new LeaderboardDialog(this, true);
    }

    //TRAITEMENT AUTRE ----------
    public void onGameEnded()
    {
        System.out.println("Game " + currentLevel + " / " + N_NIVEAUX);
        localLeaderboard.addToLeaderboard(playerProfiles.get(mainProfile));
        localLeaderboard.print();
        if (currentLevel >= N_NIVEAUX)
        {
            this.setVisible(true);
        }
        else
        {
            currentLevel++;
            LocalGame g = new LocalGame();
        }
    }

    public void loadProfiles()
    {
        try{
            FileInputStream fis = new FileInputStream("src/saves/PlayerProfiles.sav");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object tmp = ois.readObject();

            ArrayList<String> pseudos = (ArrayList<String>)(tmp);
            if (pseudos != null)
            {
                System.out.println("--- loaded profiles save ---");
                for (int i = 0; i < pseudos.size(); i++)
                {
                    addPlayer(pseudos.get(i));
                }

                setMainProfile(pseudos.get(0));
            }
            else
            {
                System.out.println("--- Error on load -> blank player ---");
                addPlayer("Default");
                setMainProfile("Default");
            }
            ois.close();
            fis.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public void saveProfiles()
    {
        try{
            FileOutputStream fos = new FileOutputStream("src/saves/PlayerProfiles.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            ArrayList<String> pseudos = new ArrayList<String>();
            for (int i = 0; i < playerProfiles.size(); i++)
            {
                pseudos.add(getPlayer(i).getPseudo());
            }

            oos.writeObject(pseudos);
            System.out.println("--- wrote profile save file ---");

            oos.close();
            fos.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public void incrementCurrentLevel(){currentLevel++;}

    public Leaderboard getLeaderboard()
    {
        return this.localLeaderboard;
    }

    public void addPlayer(String pseudo)
    {
        Player p = new Player(pseudo);
        playerProfiles.add(p);
        saveProfiles();
    }

    public CopyOnWriteArrayList<Player> getPlayers()
    {
        return this.playerProfiles;
    }

    public void removePlayer(String pseudo)
    {
        playerProfiles.removeIf(p -> p.getPseudo().equals(pseudo));
        saveProfiles();
    }

    public void setMainProfile(String pseudo)
    {
        for (Player p : playerProfiles)
        {
            if (p.getPseudo().equals(pseudo))
            {
                mainProfile = playerProfiles.indexOf(p);
            }
        }

        System.out.println("Main profile set to " + mainProfile + " | " + playerProfiles.get(mainProfile).getPseudo());
    }

    public Player getMainProfile()
    {
        return playerProfiles.get(mainProfile);
    }

    public boolean isMainProfileChosen()
    {
        return mainProfile != -1;
    }

    public Player getPlayer(int i)
    {
        if (i >= 0 && i < playerProfiles.size())
        {
            return playerProfiles.get(i);
        }
        else
        {
            return null;
        }
    }
}
