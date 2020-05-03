package com.generic.player;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO: Chargement automatique des précédents profils via fichier (sérialisé ou StreamTokenizer
 * TODO: lien Player -> Score -> PlayerContainer
 * TODO: Pattern singleton ?
 */

public class PlayerManager {
    public static PlayerManager instance;
    private CopyOnWriteArrayList<Player> playerProfiles;
    private Player mainProfile;

    public PlayerManager()
    {
        instance = this;
        playerProfiles = new CopyOnWriteArrayList<Player>();
        playerProfiles.add(new Player("Yann"));
        setMainProfile("Yann");
    }

    public void addPlayer(String pseudo)
    {
        Player p = new Player(pseudo);
        playerProfiles.add(p);
    }

    public CopyOnWriteArrayList<Player> getPlayers()
    {
        return this.playerProfiles;
    }


    public void removePlayer(String pseudo)
    {
        playerProfiles.removeIf(p -> p.getPseudo().equals(pseudo));
    }

    public void setMainProfile(String pseudo)
    {
        for (Player p : playerProfiles)
        {
            if (p.getPseudo().equals(pseudo))
            {
                mainProfile = p;
            }
        }

        System.out.println("Main profile set to " + mainProfile + " | " + mainProfile.getPseudo());
    }

    public int getNBPlayers()
    {
        return this.playerProfiles.size();
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

    public Player getMainProfile()
    {
        return this.mainProfile;
    }

    public boolean isMainProfileChosen()
    {
        return mainProfile != null;
    }

    public int getNPlayers()
    {
        return this.playerProfiles.size();
    }

}
