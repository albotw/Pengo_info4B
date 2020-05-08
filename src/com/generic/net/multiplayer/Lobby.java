package com.generic.net.multiplayer;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.generic.gameplay.NetGame_Server;
import com.generic.net.Command;

public class Lobby {
    private ArrayList<ObjectOutputStream> cmdOuts;
    private HashMap<Connexion, String> equipe1;
    private HashMap<Connexion, String> equipe2;

    private Connexion host;

    private NetGame_Server game;

    public Lobby() {
        cmdOuts = new ArrayList<ObjectOutputStream>();

        equipe1 = new HashMap<Connexion, String>();
        equipe2 = new HashMap<Connexion, String>();
    }

    public void addPlayer(ObjectOutputStream oos) {
        cmdOuts.add(oos);
        System.out.println("joueur ajouté");
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
        game = new NetGame_Server();
    }
}