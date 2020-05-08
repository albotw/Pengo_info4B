package com.generic.net.multiplayer;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.generic.net.Command;

public class Lobby {
    private ArrayList<ObjectOutputStream> cmdOuts;
    private HashMap<Connexion, String> equipe1;
    private HashMap<Connexion, String> equipe2;
    
    public Lobby()
    {
        cmdOuts = new ArrayList<ObjectOutputStream>();

        equipe1 = new HashMap<Connexion, String>();
        equipe2 = new HashMap<Connexion, String>();
    }

    public void addPlayer(ObjectOutputStream oos)
    {
        cmdOuts.add(oos);
        System.out.println("joueur ajouté");
    }

    public void removePlayer(ObjectOutputStream oos)
    {
        cmdOuts.remove(oos);
        System.out.println("joueur supprimé");
    }

    public void putOnTeam1(Connexion c, String s)
    {
        equipe1.put(c, s);
        sendCommandToAll(new Command("ADD TO TEAM 1", s, ""));
    }

    public void putOnTeam2(Connexion c, String s)
    {
        equipe2.put(c, s);
        sendCommandToAll(new Command("ADD TO TEAM 2", s, ""));
    }

    public void removeFromTeam1(Connexion c)
    {
        equipe1.remove(c);
        sendCommandToAll(new Command("REMOVE TEAM 1", c.getPseudo(), ""));
    }

    public void removeFromTeam2(Connexion c)
    {
        equipe2.remove(c);
        sendCommandToAll(new Command("REMOVE TEAM 2", c.getPseudo(), ""));
    }

    public void sendCommandToAll(Command c)
    {
        for (ObjectOutputStream oos : cmdOuts)
        {
            try{
                oos.writeObject(c);
            }catch(Exception e){e.printStackTrace();}
        }
    }
    public void purge(){
        cmdOuts.clear();
        equipe1.clear();
        equipe2.clear();
    }
}