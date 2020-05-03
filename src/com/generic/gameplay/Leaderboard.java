package com.generic.gameplay;

import com.generic.player.Player;
import com.generic.player.PlayerManager;

import java.util.ArrayList;

public class Leaderboard {
    //contient des paires Pseudo[string] | score[Integer]
    private ArrayList<ScorePair> ladder;

    public Leaderboard()
    {
        ladder = new ArrayList<ScorePair>();
    }

    public void addToLeaderboard(Player p)
    {
        ScorePair toInsert = new ScorePair(p.getPseudo(), p.getPoints());

        boolean added = false;
        for (int i = 0; i < ladder.size(); i++)
        {
            if (ladder.get(i).getScore() <=toInsert.getScore())
            {
                ladder.add(i, toInsert);
                added = true;
            }
        }

        if (!added)
        {
            ladder.add(toInsert);
        }
    }



    /**
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

        }*/

    }



