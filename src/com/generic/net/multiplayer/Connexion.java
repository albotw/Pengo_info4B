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

    public Connexion(Socket s)
    {
        socket = s;
        System.out.println("CONNEXION => " + s.toString());
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
                System.out.println(cmd.toString());

                if (cmd.getVal().equals("DISCONNECT"))
                {
                    commandOut.writeObject(new Command("DISCONNECT", "", ""));
                    loop = false;
                }
                else if (cmd.getVal().equals("JOIN TEAM 1"))
                {
                    if (equipe == 0)
                    {
                        Serveur.instance.getEquipe1().put(this, cmd.getParam());
                    }
                    else if (equipe == 2)
                    {
                        Serveur.instance.getEquipe2().remove(this);
                        Serveur.instance.getEquipe1().put(this, cmd.getParam());
                    }

                    refreshClientList = true;
                }
                else if (cmd.getVal().equals("JOIN TEAM 2"))
                {
                    if (equipe == 0)
                    {
                        Serveur.instance.getEquipe2().put(this, cmd.getParam());
                    }
                    else if (equipe == 1)
                    {
                        Serveur.instance.getEquipe1().remove(this);
                        Serveur.instance.getEquipe2().put(this, cmd.getParam());
                    }

                    refreshClientList = true;
                }

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
            }

            commandOut.close();
            commandIn.close();
            socket.close();
        }catch(Exception e){ e.printStackTrace();}
    }
}
