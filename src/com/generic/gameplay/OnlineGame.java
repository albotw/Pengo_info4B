package com.generic.gameplay;

import com.generic.AI.AI;
import com.generic.core.*;
import com.generic.net.multiplayer.Connexion;
import com.generic.net.multiplayer.Serveur;

import java.util.HashMap;

public class OnlineGame extends AbstractGame implements Runnable {
    // a modifier avec les IA.
    private HashMap<MapEntity, Connexion> equipe1;
    private HashMap<MapEntity, Connexion> equipe2;
    private HashMap<MapEntity, AI> AIs;

    private Connexion host;

    private Serveur srv = Serveur.instance;

    public OnlineGame() {
        super();

        host = srv.getHost();

        mg.path_init();
        start();
    }

    @Override
    public void initPlayers() {
        Penguin p = new Penguin(10, 10);
        map.place(p, 10, 10);
        host.setControlledObject(p);
    }

    @Override
    public void initIA() {

    }

    public void start() {
        time.start();
        initPlayers();
        initIA();
    }

    @Override
    public void gameOver() {

    }

    @Override
    public void respawnAnimal(Object owner) {

    }

    @Override
    public void respawnPenguin(Object owner) {

    }

    @Override
    public void stop() {

    }

    @Override
    public void victory() {

    }

    @Override
    public void animalKilled(Animal a, MapObject killer) {

    }

    @Override
    public void penguinKilled(Penguin p, MapObject killer) {

    }

    @Override
    public void stunTriggered(char dirMur) {

    }

    public void overrideMap(int x, int y, String type) {
        if (srv != null) {
            srv.sendCommandToAll("WRITE MAP", new String[] { "" + x, "" + y, type });
        }
    }

    @Override
    public void run() {

    }
}
