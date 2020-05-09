package com.generic.net.score;

import com.generic.launcher.Leaderboard;
import com.generic.utils.ScorePair;

import java.io.FileInputStream;
import java.net.ServerSocket;

public class ScoreServer {
    static final int port = 9090;
    static Leaderboard l;

    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(port);

        System.out.println("SOCKET ECOUTE CREE => " + s);

        l = new Leaderboard();

        FileInputStream flux = new FileInputStream("src/saves/Ladder.sav");

        while (true) {
            Connexion connexion = new Connexion(s.accept());
            connexion.start();
        }
    }
}
