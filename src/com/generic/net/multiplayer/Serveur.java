package com.generic.net.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class Serveur extends Thread{
    static final int port = 8080;
    static boolean lobby;
    static boolean flush;
    public static Serveur instance;

    static ServerSocket ss;

    public static Lobby l;

    public Serveur()
    {
        instance = this;

        l = new Lobby();

        try {
            ss = new ServerSocket(port);
            System.out.println("SERVEUR CREE => " + ss.toString());
            flush = false;
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()
    {
        while(!flush)
        {
            try{
                Connexion c = new Connexion(ss.accept());
                l.addPlayer(c.getCommandOut());
                c.start();
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void stopServer()
    {
        flush = true;
    }
}






