package com.generic.gameplay;

import com.generic.AI.AI;
import com.generic.UI.GameEndDialog;
import com.generic.core.*;
import com.generic.graphics.RenderThread;
import com.generic.graphics.Window;
import com.generic.launcher.Launcher;
import com.generic.net.multiplayer.Connexion;
import com.generic.net.multiplayer.Serveur;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.CONFIG_GAME.*;
import static com.generic.gameplay.CONFIG_GAME.PLAYER_IS_ANIMAL;
import static com.generic.utils.Equations.RandomizedInt;

import static java.lang.Thread.sleep;

public class OnlineGame extends AbstractGame implements Runnable {
    // a modifier avec les IA.
    private HashMap<MapEntity, Connexion> equipe1;
    private HashMap<MapEntity, Connexion> equipe2;
    private HashMap<MapEntity, AI> AIs;
    private Window w;

    private Connexion host;

    private Serveur srv = Serveur.instance;

    private int AILives = AI_INIT_LIVES;

    public OnlineGame() {

        super();
        map.setLocal(false);
        equipe1 = new HashMap<MapEntity, Connexion>();
        equipe2 = new HashMap<MapEntity, Connexion>();
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

        Set<Connexion> set1 = Serveur.instance.getEquipe1().keySet();
        Set<Connexion> set2 = Serveur.instance.getEquipe2().keySet();

        Iterator it1 = set1.iterator();
        while (it1.hasNext()) {
            Connexion owner = (Connexion) (it1.next());

            boolean loop = true;
            do {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (map.getAt(initX, initY).getType().equals("void")) {
                    loop = false;

                    if (TEAM_2_IS_ANIMAL) {
                        Penguin p = new Penguin(initX, initY);
                        map.place(p, initX, initY);
                        owner.setControlledObject(p);

                        equipe1.put(p, owner);
                        System.out.println("### added player to team 1 ###");
                    } else if (TEAM_1_IS_ANIMAL) {
                        Animal a = new Animal(initX, initY);
                        map.place(a, initX, initY);
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
                Connexion owner = (Connexion) (it2.next());

                boolean loop = true;
                do {
                    int initX = RandomizedInt(0, GRID_WIDTH - 1);
                    int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                    if (map.getAt(initX, initY).getType().equals("void")) {
                        loop = false;

                        if (TEAM_1_IS_ANIMAL) {
                            Penguin p = new Penguin(initX, initY);
                            map.place(p, initX, initY);
                            owner.setControlledObject(p);

                            equipe2.put(p, owner);
                        } else if (TEAM_2_IS_ANIMAL) {
                            Animal a = new Animal(initX, initY);
                            map.place(a, initX, initY);
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
                            Animal a = new Animal(initX, initY);
                            map.place(a, initX, initY);
                            ai.setControlledObject(a);
                            AIs.put(a, ai);
                        } else {
                            Penguin p = new Penguin(initX, initY);
                            map.place(p, initX, initY);
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
        host.setPoints(100);
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
                Animal a = new Animal(initX, initY);
                if (PvE) {
                    if (TEAM_1_IS_ANIMAL) // connexion = animal
                    {
                        Connexion player = (Connexion) (owner);
                        map.place(a, initX, initY);
                        player.setControlledObject(a);

                        equipe1.put(a, player);
                    } else // IA = animal
                    {
                        AI bot = (AI) (owner);
                        map.place(a, initX, initY);
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
                    Penguin p = new Penguin(initX, initY);
                    if (PvE) {
                        if (TEAM_1_IS_ANIMAL) {
                            AI bot = (AI) owner;
                            map.place(p, initX, initY);
                            bot.setControlledObject(p);
                            AIs.put(p, bot);
                        } else if (!TEAM_1_IS_ANIMAL) {
                            Connexion player = (Connexion) owner;
                            map.place(p, initX, initY);
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
        host.setPoints(100);
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
                Connexion owner = equipe1.get(a);
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
                Connexion owner = equipe1.get(p);
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
