package com.generic.net.multiplayer;

import com.generic.net.Command;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

public class Connexion extends Thread {
    private Socket socket;
    private ObjectOutputStream commandOut;
    private ObjectInputStream commandIn;

    private int equipe = 0;
    private String pseudo;

    public Connexion(Socket s)
    {
        socket = s;
        System.out.println("CONNEXION [SERVER] => " + s.toString());
        try{
            commandOut = new ObjectOutputStream(s.getOutputStream());
            commandIn = new ObjectInputStream(s.getInputStream());
        }catch(Exception e){e.printStackTrace();}
    }

    public void run()
    {
        try{
            boolean loop = true;
            while(loop)
            {
                Command cmd = (Command)(commandIn.readObject());
                System.out.println("SERVER | " +cmd.toString());

                if (cmd.getVal().equals("DISCONNECT"))
                {
                    commandOut.writeObject(new Command("DISCONNECT", "", ""));
                    if (equipe == 1) Serveur.l.removeFromTeam1(this);
                    else if (equipe == 2) Serveur.l.removeFromTeam2(this);

                    Serveur.l.removePlayer(this.commandOut);
                    loop = false;
                }
                else if (cmd.getVal().equals("SET PSEUDO"))
                {
                    this.pseudo = cmd.getParam();
                }
                else if (cmd.getVal().equals("JOIN TEAM 1"))
                {
                    if (equipe == 2)
                    {
                        Serveur.l.removeFromTeam2(this);
                    }
                    Serveur.l.putOnTeam1(this, pseudo);
                    this.equipe = 1;
                }
                else if (cmd.getVal().equals("JOIN TEAM 2"))
                {
                    if(equipe == 1)
                    {
                        Serveur.l.removeFromTeam1(this);
                    }
                    Serveur.l.putOnTeam2(this, pseudo);
                    this.equipe = 2;
                }
            }
            commandOut.close();
            commandIn.close();
            socket.close();
        }catch(Exception e){ e.printStackTrace();}
    }

    public ObjectOutputStream getCommandOut()
    {
        return this.commandOut;
    }

    public String getPseudo()
    {
        return this.pseudo;
    }
}
