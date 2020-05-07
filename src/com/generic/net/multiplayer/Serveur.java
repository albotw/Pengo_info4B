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

    private HashMap<Connexion, String> equipe1;
    private HashMap<Connexion, String> equipe2;

    public Serveur()
    {
        instance = this;

        equipe1 = new HashMap<Connexion, String>();
        equipe2 = new HashMap<Connexion, String>();

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
                c.start();
            }catch(Exception e){e.printStackTrace();}
        }
    }

    public HashMap<Connexion, String> getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(HashMap<Connexion, String> equipe1) {
        this.equipe1 = equipe1;
    }

    public HashMap<Connexion, String> getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(HashMap<Connexion, String> equipe2) {
        this.equipe2 = equipe2;
    }
}






