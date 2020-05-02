package com.generic.gameplay;

import com.generic.player.Player;
import com.generic.player.PlayerManager;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Leaderboard {
    //pseudo | highscore
    private HashMap<String, Integer> ladder;

    public Leaderboard()
    {
        ladder = new HashMap<String, Integer>();
    }

    public void classement(Player j, Score s) {


    }
    public void afficheClassement(Player joueurs, Time temps)
    {

    }

    public int getHighscore()
    {
        return 20000;
    }

    public void compareScore(Score s, PlayerManager P, Player p){
        int tab[]= new int[P.getNBPlayers()];
        for(int i = 0; i < P.getNBPlayers(); i++){
            if (p.getScore().getPlayer[i] < p.getScore().getPlayer[i + 1]){
                tab[i] == p.getScore().getPlayer[i+1];
                tab[i+1] == p.getScore().getPlayer[i]
            }
            else
            {
                tab[i]=p.getScore().getPlayer(i);
                tab[i+1]=p.getScore().getPlayer[i];
            }

        }

    }
}
