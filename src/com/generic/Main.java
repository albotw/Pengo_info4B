package com.generic;
import com.generic.gameplay.CONFIG;
import com.generic.launcher.Launcher;
import com.generic.launcher.Leaderboard;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
        Launcher l = new Launcher();
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