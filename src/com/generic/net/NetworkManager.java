package com.generic.net;

import com.generic.launcher.OnlineDialog;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkManager  implements Runnable{
    private Socket socket;
    private int port = 8080;
    private String IP = "127.0.0.1";
    private ObjectInputStream commandIn;
    private ObjectOutputStream commandOut;

    private boolean endConnexion;

    private OnlineDialog manager;

    public NetworkManager(OnlineDialog manager)
    {
        this.manager = manager;
    }

    public void run()
    {
        //récup commande et appel des fonctions liées
        try {
            while (!endConnexion) {
                Command cmd = (Command)(commandIn.readObject());
                System.out.println("CLIENT | " + cmd.toString());

                if (cmd.getVal().equals("ADD TO TEAM 1"))
                {
                    manager.addToTeam1(cmd.getParam());
                }
                else if (cmd.getVal().equals("ADD TO TEAM 2"))
                {
                    manager.addToTeam2(cmd.getParam());
                }
                else if (cmd.getVal().equals("GAME START"))
                {
                    manager.gameStart();
                }
                else if (cmd.getVal().equals("TEAM 1 FULL"))
                {
                    manager.team1Full();
                }
                else if (cmd.getVal().equals("TEAM 2 FULL"))
                {
                    manager.team2Full();
                }
                else if (cmd.getVal().equals("DISCONNECT"))
                {
                    endConnexion = true;
                }
                else if (cmd.getVal().equals("REMOVE TEAM 1"))
                {
                    manager.removeTeam1(cmd.getParam());
                }
                else if (cmd.getVal().equals("REMOVE TEAM 2"))
                {
                    manager.removeTeam2(cmd.getParam());
                }
            }

            commandOut.close();
            commandIn.close();
            socket.close();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(String IP, int port)
    {
        try {
            socket = new Socket(IP, port);
            System.out.println("CONNEXION [CLIENT] =>" + socket.toString());
            commandOut = new ObjectOutputStream(socket.getOutputStream());
            commandIn = new ObjectInputStream(socket.getInputStream());
            endConnexion = false;
        }catch(Exception e){e.printStackTrace();}
    }

    public void sendPseudo(String pseudo)
    {
        try {
            Command cmd = new Command("SET PSEUDO", pseudo, "");
            commandOut.writeObject(cmd);
        }catch(Exception e){e.printStackTrace();}
    }

    public void joinTeam1(String pseudo)
    {
        try{
            Command cmd = new Command("JOIN TEAM 1", pseudo, "");
            commandOut.writeObject(cmd);
        }catch(Exception e){e.printStackTrace();}
    }

    public void joinTeam2(String pseudo)
    {
        try{
            Command cmd = new Command("JOIN TEAM 2", pseudo, "");
            commandOut.writeObject(cmd);
        }catch(Exception e){e.printStackTrace();}
    }

    public void disconnect()
    {
        try{
            Command cmd = new Command("DISCONNECT", "", "");
            commandOut.writeObject(cmd);

            endConnexion = true;
        }catch(Exception e){e.printStackTrace();}
    }
}
