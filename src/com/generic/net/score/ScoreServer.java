package com.generic.net.score;

import com.generic.launcher.Leaderboard;
import com.generic.utils.ScorePair;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public class ScoreServer {
    private final int port = 9090;
    private Leaderboard l;
    public static ScoreServer instance;

    public static void main(String[] args) {
        try {
            ScoreServer srv = new ScoreServer();
        }catch(Exception e){e.printStackTrace();}
    }

    public ScoreServer() throws Exception
    {
        ServerSocket s = new ServerSocket(port);
        instance = this;

        System.out.println("SOCKET ECOUTE CREE => " + s);

        loadSavefile();

        while (true) {
            Connexion connexion = new Connexion(s.accept());
            connexion.start();
        }
    }

    public void loadSavefile()
    {
        try {
            FileInputStream fis = new FileInputStream("src/saves/ladder.sav");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object tmp = ois.readObject();

            l = (Leaderboard)(tmp);
            if (l != null) {
                System.out.println("--- Loaded savefile ---");
                l.print();
            }
            else
            {
                System.out.println("--- Error on load -> blank ladder ---");
                l = new Leaderboard();
            }
            ois.close();
            fis.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public void writeSavefile()
    {
        try{
            FileOutputStream fos = new FileOutputStream("src/saves/ladder.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(l);

            System.out.println("--- wrote savefile ---");
            oos.close();
            fos.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public Leaderboard getLeaderboard()
    {
        return this.l;
    }
}
