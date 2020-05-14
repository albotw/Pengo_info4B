package com.generic;
import com.generic.gameplay.CONFIG;
import com.generic.launcher.Launcher;
import com.generic.launcher.Leaderboard;
import com.generic.net.score.ScoreServer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
        if (args.length != 0)
        {
            if (args[0].equals("-createSave"))
            {
                generateDummySaveFiles();
            }
            else if (args[0].equals("-lowResMode"))
            {
                CONFIG.setLowResMode(true);
            }
        }

        try{
            ScoreServer srv = new ScoreServer();
        }catch(Exception e){e.printStackTrace();}
        Launcher l = new Launcher();
    }

    public static void generateDummySaveFiles()
    {
        try{
            FileOutputStream fos = new FileOutputStream("src/saves/ladder.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(new Leaderboard());
            System.out.println("--- création d'une sauvegarde de ladder vide ---");
            oos.close();
            fos.close();
        }catch(Exception e){e.printStackTrace();}

        try{
            FileOutputStream fos = new FileOutputStream("src/saves/PlayerProfiles.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            ArrayList<String> pseudos = new ArrayList<String>();

            pseudos.add("Default");

            oos.writeObject(pseudos);

            oos.close();
            fos.close();
        }catch(Exception e){e.printStackTrace();}
    }
}