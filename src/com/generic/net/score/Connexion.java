package com.generic.net.score;

import com.generic.utils.ScorePair;
import com.generic.launcher.Leaderboard;
import com.generic.net.Command;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connexion extends Thread {
    Socket connexion;
    private ObjectInputStream commandIn;
    private ObjectOutputStream commandOut;

    private Leaderboard l = ScoreServer.l;

    Connexion(Socket s) {
        connexion = s;

        try {
            commandIn = new ObjectInputStream(s.getInputStream());
            commandOut = new ObjectOutputStream(s.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            boolean loop = true;
            while (loop) {
                Command cmd = (Command) (commandIn.readObject());

                System.out.println(cmd.toString());

                if (cmd.getVal().equals("GET SCORE")) {
                    for (int i = 0; i < ScoreServer.l.getLadder().size(); i++) {
                        String score = "" + l.getLadder().get(i).getScore();
                        String pseudo = "" + l.getLadder().get(i).getPseudo();
                        Command out = new Command("SET SCORE", new String[] { score, pseudo });
                        commandOut.writeObject(out);
                    }
                    commandOut.writeObject(new Command("SET SCORE", new String[] { "END" }));
                } else if (cmd.getVal().equals("SET SCORE")) {
                    if (!cmd.getParam(0).equals("END")) {
                        String pseudo = cmd.getParam(1);
                        int score = Integer.parseInt(cmd.getParam(0));
                        l.addToLeaderboard(new ScorePair(pseudo, score, false));
                    }
                } else if (cmd.getVal().equals("DISCONNECT")) {
                    loop = false;
                }
            }

            commandIn.close();
            commandOut.close();

            connexion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
