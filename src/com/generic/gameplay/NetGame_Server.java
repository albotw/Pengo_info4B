package com.generic.gameplay;

import com.generic.AI.AI;
import com.generic.core.*;
import com.generic.net.Command;
import com.generic.net.multiplayer.Connexion;
import com.generic.net.multiplayer.Lobby;
import com.generic.net.multiplayer.Serveur;

import java.util.HashMap;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.CONFIG_GAME.PLAYER_IS_ANIMAL;
import static com.generic.gameplay.CONFIG_GAME.PLAYER_IS_PENGUIN;
import static com.generic.utils.Equations.RandomizedInt;

public class NetGame_Server extends Thread{
    public static NetGame_Server instance;

    //a modifier avec les IA.
    private HashMap<MapEntity, Connexion> equipe1;
    private HashMap<MapEntity, Connexion> equipe2;
    private HashMap<MapEntity, AI> AIs;

    private MapGenerator mg;

    private GameMap m;

    private GameTimer time;

    private Lobby l;

    private Connexion host;

    public NetGame_Server()
    {
        instance = this;

        host = Serveur.l.getHost();

        m = GameMap.createMap(GRID_WIDTH, GRID_HEIGHT);
        mg = new MapGenerator();
        mg.path_init();

        start();
    }

    public void start() {
        time.start();
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    public void initDiamondBlocks() {
        boolean loop = true;
        int cpt = 0;
        for (int k = 0; k < 3; k++) {
            loop = true;
            do {
                int initX = RandomizedInt(1, GRID_WIDTH - 2);
                int initY = RandomizedInt(1, GRID_HEIGHT - 2);

                if (m.getAt(initX, initY) != null) {
                    if (m.getAt(initX, initY).getType().equals("IceBlock")) {
                        loop = false;
                        DiamondBlock d = new DiamondBlock(initX, initY);
                        m.place(d, initX, initY);
                    }
                }
            } while (loop && cpt != 3);
        }
    }

    public void initIA() {
        // A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        for (int i = 0; i < 1; i++) {
            loop = true;
            do {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (m.getAt(initX, initY) == null) {
                    loop = false;
                    AI ai = new AI();

                    if (PLAYER_IS_PENGUIN)
                    {
                        Animal a = new Animal(initX, initY);
                        m.place(a, initX, initY);
                        ai.setControlledObject(a);
                        AIs.put(a, ai);
                    }
                    else if (PLAYER_IS_ANIMAL)
                    {
                        Penguin p = new Penguin(initX, initY);
                        m.place(p, initX, initY);
                        ai.setControlledObject(p);
                        AIs.put(p, ai);
                    }

                    //recherche de cible a intégrer dans l'IA
                    //ai.setTarget(localPlayer.getControlledObject());
                    ai.start();
                }
            } while (loop);
        }
    }

    public void initPlayers() {
        //A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        do {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);


            if (m.getAt(initX, initY) == null) {
                loop = false;

                if (PLAYER_IS_PENGUIN)
                {
                    Penguin p = new Penguin(initX, initY);
                    m.place(p, initX, initY);
                    host.setControlledObject(p);
                }
                else if (PLAYER_IS_ANIMAL)
                {
                    Animal a = new Animal(initX, initY);
                    m.place(a, initX, initY);
                    host.setControlledObject(a);
                }
            }
        } while (loop);
    }

    public void overrideMap(int x, int y, String type)
    {
        Command cmd = new Command("WRITE MAP", ""+x, ""+y, type);
        l.sendCommandToAll(cmd);
    }

    public GameMap getMap()
    {
        return this.m;
    }
}
