package com.generic.gameplay;

import com.generic.net.Command;
import com.generic.player.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Leaderboard {
    //contient des paires Pseudo[string] | score[Integer]
    private CopyOnWriteArrayList<ScorePair> ladder;

    public Leaderboard()
    {
        ladder = new CopyOnWriteArrayList<ScorePair>();
    }

    //Cas de leaderboard local
    public void addToLeaderboard(Player p)
    {
        ScorePair toInsert = new ScorePair(p.getPseudo(), p.getPoints(), true);
        addToLeaderboard(toInsert);
    }

    //cas de ScoreServer
    public synchronized void addToLeaderboard(ScorePair sp)
    {
        boolean added = false;
        int i = 0;
        while (i < ladder.size() && !added){
            if (ladder.get(i).getScore() <= sp.getScore())
            {
                ladder.add(i, sp);
                added = true;
            }
            else i++;
        }

        //si c'est le pire score, on l'ajoute a la fin
        if (!added)
        {
            ladder.add(sp);
        }

        //System.out.println();
        //print();
    }

    public void print()
    {
        for (int i = 0; i < ladder.size(); i++)
        {
            System.out.println(i + " | " + ladder.get(i).getPseudo() + " | " + ladder.get(i).getScore());
        }
    }

    public CopyOnWriteArrayList<ScorePair> getLadder()
    {
        return this.ladder;
    }

    private void flush()
    {
        for (int i = 0; i < ladder.size(); i++)
        {
            if (!ladder.get(i).isLocal())
            {
                ladder.remove(i);
            }
        }
    }

    //appelée uniquement au démarrage du launcher OU actualisation via dialog
    public void pull()
    {
        try
        {
            flush();
            Socket socket = new Socket("127.0.0.1", 9090);
            System.out.println("SOCKET CREE =>" + socket.toString());

            ObjectOutputStream commandOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream commandIn = new ObjectInputStream(socket.getInputStream());

            commandOut.writeObject(new Command("GET SCORE", "", ""));

            boolean loop = true;
            while(loop)
            {
                Command cmd = (Command)(commandIn.readObject());

                System.out.println(cmd.toString());

                if (cmd.getVal().equals("SET SCORE"))
                {
                    if (!cmd.getParam().equals("END"))
                    {
                        addToLeaderboard(new ScorePair(cmd.getSource(), Integer.parseInt(cmd.getParam()), false));
                    }
                    else
                    {
                        loop = false;
                    }
                }
            }

            commandIn.close();
            commandOut.close();
            socket.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    //appelée en fin de partie
    public void push()
    {
        try
        {
            Socket socket = new Socket("127.0.0.1", 9090);
            ObjectOutputStream commandOut = new ObjectOutputStream(socket.getOutputStream());

            for (int i = 0; i < ladder.size(); i++)
            {
                ScorePair tmp = ladder.get(i);
                if (tmp.isLocal())
                {
                    commandOut.writeObject(new Command("SET SCORE", Integer.toString(tmp.getScore()), tmp.getPseudo()));
                    tmp.setLocal(false);
                }
            }

            commandOut.writeObject(new Command("SET SCORE", "END", ""));

            commandOut.close();
            socket.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        } 
    }
} 



