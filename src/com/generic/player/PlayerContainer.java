package com.generic.player;

import com.generic.player.Player;

public class PlayerContainer {
    private Player p;
    private String pseudo;
    private int score;

    public PlayerContainer(String pseudo)
    {
        this.pseudo = pseudo;
    }

    public String getPseudo()
    {
        return this.pseudo;
    }

    public int getScore()
    {
        return this.score;
    }

    public void setScore(int newScore){this.score = newScore;}

    public Player getPlayer(){return this.p;}

    public void setPlayer(Player p){this.p = p;}
}
