package com.generic.net.multiplayer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import com.generic.gameplay.OnlineGame;
import com.generic.net.Command;

public class Serveur extends Thread {
    static final int port = 8080;
    static boolean flush;
    public static Serveur instance;

    private ServerSocket ss;

    private ArrayList<ObjectOutputStream> cmdOuts;
    private HashMap<Connexion, String> equipe1;
    private HashMap<Connexion, String> equipe2;

    private Connexion host;

    private OnlineGame game;

    public Serveur() {
        instance = this;

        cmdOuts = new ArrayList<ObjectOutputStream>();

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

    public void run() {
        while (!flush) {
            try {
                Connexion c = new Connexion(ss.accept());
                cmdOuts.add(c.getCommandOut());
                c.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        purge();
        System.out.println("Arret du serveur");
    }

    public void stopServer() {
        flush = true;
        try {
            ss.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removePlayer(ObjectOutputStream oos) {
        cmdOuts.remove(oos);
        System.out.println("joueur supprimé");
    }

    public void putOnTeam1(Connexion c, String s) {
        equipe1.put(c, s);
        sendCommandToAll("ADD TO TEAM 1", new String[] { s });
    }

    public void putOnTeam2(Connexion c, String s) {
        equipe2.put(c, s);
        sendCommandToAll("ADD TO TEAM 2", new String[] { s });
    }

    public void removeFromTeam1(Connexion c) {
        equipe1.remove(c);
        sendCommandToAll("REMOVE TEAM 1", new String[] { c.getPseudo() });
    }

    public void removeFromTeam2(Connexion c) {
        equipe2.remove(c);
        sendCommandToAll("REMOVE TEAM 2", new String[] { c.getPseudo() });
    }

    public void sendCommandToAll(String val, String[] params) {
        Command cmd = new Command(val, params);
        for (ObjectOutputStream oos : cmdOuts) {
            try {
                oos.writeObject(cmd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Connexion getHost() {
        return host;
    }

    public void setHost(Connexion host) {
        this.host = host;
    }

    public void purge() {
        cmdOuts.clear();
        equipe1.clear();
        equipe2.clear();
    }

    public void startGame() {
        game = new OnlineGame();
    }

}
