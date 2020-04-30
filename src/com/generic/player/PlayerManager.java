package com.generic.player;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerManager {
    public static PlayerManager instance;
    private CopyOnWriteArrayList<PlayerContainer> playerProfiles;
    private int mainProfile = -1;

    public PlayerManager()
    {
        instance = this;
        playerProfiles = new CopyOnWriteArrayList<PlayerContainer>();
    }

    public void addPlayer(String pseudo)
    {
        PlayerContainer pc = new PlayerContainer(pseudo);
        playerProfiles.add(pc);
    }

    public CopyOnWriteArrayList<PlayerContainer> getPlayersProfiles()
    {
        return this.playerProfiles;
    }

    public ArrayList<Player> getPlayers()
    {
        ArrayList<Player> output = new ArrayList<Player>();
        for (PlayerContainer pc : playerProfiles)
        {
            output.add(pc.getPlayer());
        }

        return output;
    }

    public void removePlayer(String pseudo)
    {
        for (PlayerContainer pc : playerProfiles)
        {
            if (pc.getPseudo().equals(pseudo))
                playerProfiles.remove(pc);
        }
    }

    public void setMainProfile(String pseudo)
    {
        int i = 0;
        for (PlayerContainer pc : playerProfiles)
        {
            if (pc.getPseudo().equals(pseudo))
                mainProfile = i;
            else
                i++;
        }

        System.out.println("Main profile set to " + mainProfile + " | " + playerProfiles.get(mainProfile).getPseudo());
    }

    public PlayerContainer getMainProfile()
    {
        if (mainProfile != -1)
        {
            return playerProfiles.get(mainProfile);
        }
        else
        {
            return null;
        }
    }

    public boolean isMainProfileChosen()
    {
        if (mainProfile != -1) {
            if (playerProfiles.get(mainProfile) != null) {
                return true;
            }
            else return false;
        }
        else return false;
    }
}
