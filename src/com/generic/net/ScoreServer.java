package com.generic.net;

import com.generic.gameplay.Leaderboard;
import com.generic.gameplay.ScorePair;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ScoreServer {
    static final int port = 9090;
    static Leaderboard l;

    public static void main(String[] args) throws Exception
    {
        ServerSocket s = new ServerSocket(port);

        System.out.println("SOCKET ECOUTE CREE => " + s);

        l = new Leaderboard();
        l.addToLeaderboard(new ScorePair("Wassim", 500000, false));
        l.addToLeaderboard(new ScorePair("Wassi", 300000, false));
        l.addToLeaderboard(new ScorePair("Wass", 200000, false));
        l.addToLeaderboard(new ScorePair("Was", 400000, false));
        l.addToLeaderboard(new ScorePair("Wa", 600000, false));
        l.addToLeaderboard(new ScorePair("W", 100000, false));

        while(true)
        {
            Connexion connexion = new Connexion(s.accept());
            connexion.start();
        }
    }
}

class Connexion extends Thread
{
    Socket connexion;
    private ObjectInputStream commandIn;
    private ObjectOutputStream commandOut;

    Connexion(Socket s)
    {
        connexion = s;

        try{
            commandIn = new ObjectInputStream(s.getInputStream());
            commandOut = new ObjectOutputStream(s.getOutputStream());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            boolean loop = true;
            while(loop)
            {
                Command cmd = (Command)(commandIn.readObject());

                System.out.println(cmd.toString());

                if (cmd.c.equals("GET SCORE"))
                {
                    for (int i = 0; i < ScoreServer.l.getLadder().size(); i++)
                    {
                        commandOut.writeObject(new Command("SET SCORE", ""+ScoreServer.l.getLadder().get(i).getScore(), ScoreServer.l.getLadder().get(i).getPseudo(), ""));
                    }
                    commandOut.writeObject(new Command("SET SCORE", "END", "", ""));
                    loop = false;
                }
                else if (cmd.c.equals("SET SCORE"))
                {
                    if (!cmd.param0.equals("END"))
                    {
                        ScoreServer.l.addToLeaderboard(new ScorePair(cmd.param1, Integer.parseInt(cmd.param0), false));
                    }
                    else
                    {
                        loop = false;
                    }
                }
            }

            commandIn.close();
            commandOut.close();

            connexion.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
