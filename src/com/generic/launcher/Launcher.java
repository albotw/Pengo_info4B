package com.generic.launcher;

import com.generic.player.PlayerContainer;

import javax.swing.*;
import java.util.ArrayList;

public class Launcher
{
    public ArrayList<PlayerContainer> players;
    public static Launcher instance;

    public Launcher()
    {
        instance = this;
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
}
