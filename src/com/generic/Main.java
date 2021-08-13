package com.generic;

import com.generic.gameplay.v2.GameController;
import com.generic.launcher.Launcher;
import com.generic.launcher.Leaderboard;
import com.generic.net.score.ScoreServer;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Main {

    public static ScoreServer sc_srv;
    public static Launcher l;

    public static void main(String[] args) {
        if (args.length != 0) {
            if (args[0].equals("-createSave")) {
                generateDummySaveFiles();
            }
            else if (args[0].equals("-debugMainGame"))
            {
                GameController gc = GameController.createGameController();
                gc.start();
            }
            else
            {
                try {
                    sc_srv = new ScoreServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                l = new Launcher();
            }
        }


    }

    public static void generateDummySaveFiles() {
        try {
            FileOutputStream fos = new FileOutputStream("saves/ladder.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(new Leaderboard());
            System.out.println("--- création d'une sauvegarde de ladder vide ---");
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream("saves/PlayerProfiles.sav");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            ArrayList<String> pseudos = new ArrayList<String>();

            pseudos.add("Joueur 1");

            oos.writeObject(pseudos);

            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}