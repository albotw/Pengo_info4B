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
    private boolean refreshClientList;
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
                    // refreshClientList = true;
                }
                else if (cmd.getVal().equals("JOIN TEAM 2"))
                {
                    if(equipe == 1)
                    {
                        Serveur.l.removeFromTeam1(this);
                    }
                    Serveur.l.putOnTeam2(this, pseudo);
                    this.equipe = 2;

                    refreshClientList = true;
                }
                /**
                if (refreshClientList)
                {
                    Iterator it1 = Serveur.instance.getEquipe1().entrySet().iterator();
                    Iterator it2 = Serveur.instance.getEquipe2().entrySet().iterator();

                    while (it1.hasNext())
                    {
                        Map.Entry pair1 = (Map.Entry)it1.next();

                        if (pair1.getValue() != null)
                        {
                            Command out = new Command("ADD TO TEAM 1", (String)(pair1.getValue()), "");
                            commandOut.writeObject(out);
                        }
                    }

                    while (it2.hasNext())
                    {
                        Map.Entry pair1 = (Map.Entry)it2.next();

                        if (pair1.getValue() != null)
                        {
                            Command out = new Command("ADD TO TEAM 2", (String)(pair1.getValue()), "");
                            commandOut.writeObject(out);
                        }
                    }
                }
                */
            }
            System.out.println("fermeture socket");
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
