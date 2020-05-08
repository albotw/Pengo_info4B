package com.generic.net.score;

import com.generic.launcher.Leaderboard;
import com.generic.utils.ScorePair;

import java.net.ServerSocket;

public class ScoreServer {
    static final int port = 9090;
    static Leaderboard l;

    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(port);

        System.out.println("SOCKET ECOUTE CREE => " + s);

        l = new Leaderboard();
        l.addToLeaderboard(new ScorePair("Wassim", 500000, false));
        l.addToLeaderboard(new ScorePair("Wassi", 300000, false));
        l.addToLeaderboard(new ScorePair("Wass", 200000, false));
        l.addToLeaderboard(new ScorePair("Was", 400000, false));
        l.addToLeaderboard(new ScorePair("Wa", 600000, false));
        l.addToLeaderboard(new ScorePair("W", 100000, false));

        while (true) {
            Connexion connexion = new Connexion(s.accept());
            connexion.start();
        }
    }
}


