package com.generic.net.multiplayer;

import com.generic.core.MapEntity;
import com.generic.net.Command;

import java.io.*;
import java.net.Socket;

public class Connexion extends Thread {
    private Socket socket;
    private ObjectOutputStream commandOut;
    private ObjectInputStream commandIn;

    private int equipe = 0;
    private String pseudo;
    private MapEntity controlledObject;

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
                    commandOut.writeObject(new Command("DISCONNECT", "", "", ""));
                    if (equipe == 1) Serveur.l.removeFromTeam1(this);
                    else if (equipe == 2) Serveur.l.removeFromTeam2(this);

                    Serveur.l.removePlayer(this.commandOut);
                    loop = false;
                }
                else if (cmd.getVal().equals("SET PSEUDO"))
                {
                    this.pseudo = cmd.getParam0();
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
                else if (cmd.getVal().equals("SET HOST"))
                {
                    Serveur.l.setHost(this);
                }
                else if (cmd.getVal().equals("START GAME"))
                {
                    Serveur.l.startGame();
                }
                else if (cmd.getVal().equals("MOVE UP"))
                {
                    controlledObject.goUp();
                }
                else if (cmd.getVal().equals("MOVE DOWN"))
                {
                    controlledObject.goDown();
                }
                else if (cmd.getVal().equals("MOVE LEFT"))
                {
                    controlledObject.goLeft();
                }
                else if (cmd.getVal().equals("MOVE RIGHT"))
                {
                    controlledObject.goRight();
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

    public MapEntity getControlledObject() {
        return controlledObject;
    }

    public void setControlledObject(MapEntity controlledObject) {
        this.controlledObject = controlledObject;
    }
}
