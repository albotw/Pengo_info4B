package com.generic.net.multiplayer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import com.generic.net.Command;

public class Serveur extends Thread {
    static final int port = 8080;
    static boolean flush;
    public static Serveur instance;

    private ServerSocket ss;

    private ArrayList<ObjectOutputStream> cmdOuts;
    private HashMap<OnlinePlayer, String> equipe1;
    private HashMap<OnlinePlayer, String> equipe2;

    private OnlinePlayer host;

    private OnlineGame game;

    public Serveur() {
        instance = this;

        cmdOuts = new ArrayList<ObjectOutputStream>();

        equipe1 = new HashMap<OnlinePlayer, String>();
        equipe2 = new HashMap<OnlinePlayer, String>();

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
                OnlinePlayer c = new OnlinePlayer(ss.accept());
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

    public void putOnTeam1(OnlinePlayer c, String s) {
        equipe1.put(c, s);
        sendCommandToAll("ADD TO TEAM 1", new String[] { s });
    }

    public void putOnTeam2(OnlinePlayer c, String s) {
        equipe2.put(c, s);
        sendCommandToAll("ADD TO TEAM 2", new String[] { s });
    }

    public void removeFromTeam1(OnlinePlayer c) {
        equipe1.remove(c);
        sendCommandToAll("REMOVE TEAM 1", new String[] { c.getPseudo() });
    }

    public void removeFromTeam2(OnlinePlayer c) {
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

    public OnlinePlayer getHost() {
        return host;
    }

    public void setHost(OnlinePlayer host) {
        this.host = host;
    }

    public void purge() {
        cmdOuts.clear();
        equipe1.clear();
        equipe2.clear();
    }

    public void startGame() {
        sendCommandToAll("GAME START", null);
        game = new OnlineGame();
    }

    public HashMap<OnlinePlayer, String> getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(HashMap<OnlinePlayer, String> equipe1) {
        this.equipe1 = equipe1;
    }

    public HashMap<OnlinePlayer, String> getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(HashMap<OnlinePlayer, String> equipe2) {
        this.equipe2 = equipe2;
    }
}
