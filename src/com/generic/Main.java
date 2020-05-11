package com.generic;
import com.generic.launcher.Launcher;
import com.generic.launcher.Leaderboard;
import com.generic.net.score.ScoreServer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Main {

    public static void main(String[] args)
    {
        //generateDummySaveFiles();
        Launcher l = new Launcher();
        //Game g = new Game();
    }

    public static void generateDummySaveFiles()
    {
        try{
            FileOutputStream fos = new FileOutputStream("src/saves/ladder.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(new Leaderboard());
            System.out.println("--- création d'une sauvegarde de ladder vide ---");
        }catch(Exception e){e.printStackTrace();}
    }
}