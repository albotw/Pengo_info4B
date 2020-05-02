package com.generic.gameplay;

import java.util.HashMap;

public class Leaderboard {
    //pseudo | highscore
    private HashMap<String, Integer> ladder;

    public Leaderboard()
    {
        ladder = new HashMap<String, Integer>();
    }

    public int getHighscore()
    {
        return 20000;
    }
}