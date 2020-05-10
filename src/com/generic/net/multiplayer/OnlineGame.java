package com.generic.net.multiplayer;

import com.generic.AI.AI;
import com.generic.core.*;
import com.generic.gameplay.AbstractGame;
import com.generic.graphics.Window;
import com.generic.net.multiplayer.OnlinePlayer;
import com.generic.net.multiplayer.Serveur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.CONFIG_GAME.*;
import static com.generic.utils.Equations.RandomizedInt;

import static java.lang.Thread.sleep;

public class OnlineGame extends AbstractGame implements Runnable {
    // a modifier avec les IA.
    private HashMap<MapEntity, OnlinePlayer> equipe1;
    private HashMap<MapEntity, OnlinePlayer> equipe2;
    private HashMap<MapEntity, AI> AIs;
    private Window w;

    private OnlinePlayer host;

    private Serveur srv = Serveur.instance;

    private int AILives = AI_INIT_LIVES;

    public OnlineGame() {
        super();
        map.setLocal(false);
        equipe1 = new HashMap<MapEntity, OnlinePlayer>();
        equipe2 = new HashMap<MapEntity, OnlinePlayer>();
        AIs = new HashMap<MapEntity, AI>();

        host = srv.getHost();
        mg.pre_init();
        mg.path_init();
        start();
    }

    @Override
    public void initPlayers() {
        // Penguin p_host = new Penguin(10, 10);
        // map.place(p_host, 10, 10);
        // host.setControlledObject(p_host);

        Set<OnlinePlayer> set1 = Serveur.instance.getEquipe1().keySet();
        Set<OnlinePlayer> set2 = Serveur.instance.getEquipe2().keySet();

        Iterator it1 = set1.iterator();
        while (it1.hasNext()) {
            OnlinePlayer owner = (OnlinePlayer) (it1.next());

            boolean loop = true;
            do {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (map.getAt(initX, initY).getType().equals("void")) {
                    loop = false;

                    if (TEAM_2_IS_ANIMAL) {
                        Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
                        owner.setControlledObject(p);

                        equipe1.put(p, owner);
                        System.out.println("### added player to team 1 ###");
                    } else if (TEAM_1_IS_ANIMAL) {
                        Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                        owner.setControlledObject(a);

                        equipe1.put(a, owner);
                        System.out.println("### added player to team 1 ###");
                    }
                }
            } while (loop);
        }

        if (PvP) {
            Iterator it2 = set2.iterator();
            while (it2.hasNext()) {
                OnlinePlayer owner = (OnlinePlayer) (it2.next());

                boolean loop = true;
                do {
                    int initX = RandomizedInt(0, GRID_WIDTH - 1);
                    int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                    if (map.getAt(initX, initY).getType().equals("void")) {
                        loop = false;

                        if (TEAM_1_IS_ANIMAL) {
                            Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
                            owner.setControlledObject(p);

                            equipe2.put(p, owner);
                        } else if (TEAM_2_IS_ANIMAL) {
                            Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                            owner.setControlledObject(a);

                            equipe2.put(a, owner);
                        }
                    }
                } while (loop);
            }
        }
    }

    @Override
    public void initIA() {
        if (PvE) {
            boolean loop = true;
            for (int i = 0; i < N_AI; i++) {
                loop = true;
                do {
                    int initX = RandomizedInt(0, GRID_WIDTH - 1);
                    int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                    if (map.getAt(initX, initY).getType().equals("IceBlock")) {
                        loop = false;
                        AI ai = new AI();

                        if (!TEAM_1_IS_ANIMAL) {
                            Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                            ai.setControlledObject(a);
                            AIs.put(a, ai);
                        } else {
                            Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
                            ai.setControlledObject(p);
                            AIs.put(p, ai);
                        }

                        ai.setTarget(host.getControlledObject());
                        ai.start();
                    }
                } while (loop);
            }
        }
    }

    public void start() {
        time.start();
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    @Override
    public void gameOver() {
        System.out.println("### APPEL GAME OVER ##");
        time.stopTimer();
        stop();
        srv.sendCommandToAll("GAME END", new String[] { "DEFEAT", "" + host.getPoints() });
        srv.stopServer();
    }

    public void stop() {
        Iterator it = AIs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            AI tmp = (AI) pair.getValue();
            tmp.flush();
            try {
                System.out.println("En attente de l'arrêt d'un Thread IA");
                tmp.join();
                tmp.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            it.remove();
        }

        equipe1.clear();
        equipe2.clear();
        map.deleteMap();
    }

    @Override
    public void respawnAnimal(Object owner) {
        boolean loop = true;
        do {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);

            if (map.getAt(initX, initY).getType().equals("IceBlock")) {
                loop = false;
                Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                if (PvE) {
                    if (TEAM_1_IS_ANIMAL) // connexion = animal
                    {
                        OnlinePlayer player = (OnlinePlayer) (owner);
                        player.setControlledObject(a);
                        equipe1.put(a, player);
                    } else // IA = animal
                    {
                        AI bot = (AI) (owner);
                        bot.setControlledObject(a);
                        AIs.put(a, bot);
                    }
                }
            }
        } while (loop);
    }

    @Override
    public void respawnPenguin(Object owner) {
        try {
            sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
            boolean loop = true;
            do {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (map.getAt(initX, initY).getType().equals("void")) {
                    loop = false;
                    Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
                    if (PvE) {
                        if (TEAM_1_IS_ANIMAL) {
                            AI bot = (AI) owner;
                            bot.setControlledObject(p);
                            AIs.put(p, bot);
                        } else if (!TEAM_1_IS_ANIMAL) {
                            OnlinePlayer player = (OnlinePlayer) owner;
                            player.setControlledObject(p);
                            equipe1.put(p, player);
                        }
                    }
                }
            } while (loop);

        }
    }

    @Override
    public void victory() {
        System.out.println("### APPEL VICTOIRE ##");
        time.stopTimer();
        stop();
        srv.sendCommandToAll("GAME END", new String[] { "VICTORY", "" + host.getPoints() });
        srv.stopServer();
    }

    @Override
    public void animalKilled(Animal a, MapObject killer) {
        if (PvE) {
            if (!TEAM_1_IS_ANIMAL) // animal == IA
            {
                AI owner = AIs.get(a);

                owner.setControlledObject(null);
                // setPoints;

                AILives = AILives - 1;
                if (AILives == 0) {
                    victory();
                } else {
                    respawnAnimal(owner);
                }
            } else // animal == Connexion
            {
                OnlinePlayer owner = equipe1.get(a);
                equipe1.remove(a);
                owner.setControlledObject(null);
                owner.removeLive();
            }
        } else if (PvP) {

        }
    }

    @Override
    public void penguinKilled(Penguin p, MapObject killer) {
        if (PvE) {
            if (!TEAM_1_IS_ANIMAL) // Penquin = connexion
            {
                OnlinePlayer owner = equipe1.get(p);
                owner.setControlledObject(null);

                // setPoints;

                owner.removeLive();
            } else //penguin = IA
            {
                AI owner = AIs.get(p);

                owner.setControlledObject(null);
                // setPoints;

                AILives = AILives - 1;
                if (AILives == 0) {
                    victory();
                } else {
                    respawnPenguin(owner);
                }
            }
        }
    }

    @Override
    public void stunTriggered(char dirMur) {
        /**
         * TODO: Optimisation
         */
        System.out.println("STUN!");

        switch (dirMur) {
            case 'G':
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    MapObject mo = map.getAt(0, i);
                    if (mo.getType().equals("Animal")) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'D':
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    MapObject mo = map.getAt(GRID_WIDTH - 1, i);
                    if (mo.getType().equals("Animal")) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'H':
                for (int i = 0; i < GRID_WIDTH; i++) {
                    MapObject mo = map.getAt(i, 0);
                    if (mo.getType().equals("Animal")) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'B':
                for (int i = 0; i < GRID_WIDTH; i++) {
                    MapObject mo = map.getAt(i, GRID_HEIGHT - 1);
                    if (mo.getType().equals("Animal")) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;
        }

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
