package com.generic.gameplay;

import com.generic.UI.GameEndDialog;
import com.generic.core.*;
import com.generic.graphics.Window;
import com.generic.launcher.Launcher;
import com.generic.player.*;
import com.generic.graphics.*;
import com.generic.AI.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.generic.gameplay.CONFIG.*;
import static com.generic.gameplay.CONFIG_GAME.*;
import static com.generic.utils.Equations.RandomizedInt;
import static java.lang.Thread.sleep;

public class LocalGame extends AbstractGame {

    private HashMap<MapEntity, AI> AIs;

    private RenderThread renderer;
    private SpriteManager sm;
    private Window w;

    private PlayerManager pm = PlayerManager.instance;
    private Player localPlayer;
    private Thread LPThread;

    private int AIlives = AI_INIT_LIVES;

    public LocalGame() {
        super();

        AIs = new HashMap<MapEntity, AI>();
        localPlayer = pm.getMainProfile();
        sm = SpriteManager.createSpriteManager();
        renderer = new RenderThread();
        this.w = renderer.getWindow();
        renderer.start();

        mg.path_init();

        start();
    }

    public void initIA() {
        // A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        for (int i = 0; i < N_AI; i++) {
            loop = true;
            do {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (map.getAt(initX, initY).getType().equals("void")) {
                    loop = false;
                    AI ai = new AI();

                    if (PLAYER_IS_PENGUIN) {
                        Animal a = new Animal(initX, initY);
                        map.place(a, initX, initY);
                        ai.setControlledObject(a);
                        AIs.put(a, ai);
                    } else if (PLAYER_IS_ANIMAL) {
                        Penguin p = new Penguin(initX, initY);
                        map.place(p, initX, initY);
                        ai.setControlledObject(p);
                        AIs.put(p, ai);
                    }

                    ai.setTarget(localPlayer.getControlledObject());
                    ai.start();
                }
            } while (loop);
        }
    }

    public void initPlayers() {
        // A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        do {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);

            if (map.getAt(initX, initY).getType().equals("void")) {
                loop = false;

                if (PLAYER_IS_PENGUIN) {
                    Penguin p = new Penguin(initX, initY);
                    map.place(p, initX, initY);
                    localPlayer.setControlledObject(p);
                } else if (PLAYER_IS_ANIMAL) {
                    Animal a = new Animal(initX, initY);
                    map.place(a, initX, initY);
                    localPlayer.setControlledObject(a);
                }

                LPThread = new Thread(localPlayer);
                LPThread.start();
            }
        } while (loop);
    }

    public void start() {
        time.start();
        super.initDiamondBlocks();
        initPlayers();
        initIA();
    }

    public void gameOver() {
        // a ajouter: déréférencement dans les objets
        time.stopTimer();
        stop();
        GameEndDialog GED = new GameEndDialog(w, false, false);
        try {
            sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GED.Fermer();
        renderer.stopRendering();
        Launcher.instance.onGameEnded();
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

        localPlayer.flush();
        try {
            System.out.println("En attente de l'arrêt d'un Thread Player");
            LPThread.interrupt();
            LPThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.deleteMap();

    }

    public void victory() {
        // a ajouter: déréférencement dans les objets.
        pm.getMainProfile().setPoints("GameEnded", time.getTime());
        time.stopTimer();
        stop();
        GameEndDialog ged = new GameEndDialog(w, false, true);
        try {
            sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ged.Fermer();
        renderer.stopRendering();
        Launcher.instance.onGameEnded();
    }

    public void animalKilled(Animal a, MapObject killer) {
        if (PLAYER_IS_PENGUIN) {
            AI owner = AIs.get(a);
            owner.setControlledObject(null);

            localPlayer.setPoints("AnimalKilled", 0);

            AIlives = AIlives - 1;
            if (AIlives == 0) {
                victory();
            } else {
                respawnAnimal(owner);
            }
        } else if (PLAYER_IS_ANIMAL) {
            System.out.println("Animal tué");
            localPlayer.setControlledObject(null);
            localPlayer.removeLive();
        }
    }

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

    public void penguinKilled(Penguin p, MapObject Killer) {
        if (PLAYER_IS_PENGUIN) {
            System.out.println("Pingouin tué");
            localPlayer.setControlledObject(null);
            localPlayer.removeLive();
        } else if (PLAYER_IS_ANIMAL) {
            AI owner = AIs.get(p);
            owner.setControlledObject(null);

            localPlayer.setPoints("PenguinKilled", 0);

            AIlives = AIlives - 1;
            if (AIlives == 0) {
                victory();
            } else {
                respawnPenguin(owner);
            }
        }
    }

    public void respawnAnimal(Object owner) {
        boolean loop = true;
        do {

            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);

            if (map.getAt(initX, initY).getType().equals("IceBlock")) {
                loop = false;
                Animal a = new Animal(initX, initY);
                if (PLAYER_IS_PENGUIN) {
                    AI bot = (AI) (owner);
                    map.place(a, initX, initY);
                    bot.setControlledObject(a);

                    AIs.put(a, bot);
                } else if (PLAYER_IS_ANIMAL) {
                    Player pl = (Player) (owner);
                    map.place(a, initX, initY);
                    pl.setControlledObject(a);
                }
            }
        } while (loop);
    }

    public void respawnPenguin(Object owner) {
        try {
            sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean loop = true;
        do {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);

            if (map.getAt(initX, initY).getType().equals("void")) {
                loop = false;
                Penguin p = new Penguin(initX, initY);

                if (PLAYER_IS_PENGUIN) {
                    Player pl = (Player) owner;
                    map.place(p, initX, initY);
                    pl.setControlledObject(p);
                } else if (PLAYER_IS_ANIMAL) {
                    AI bot = (AI) (owner);
                    map.place(p, initX, initY);
                    bot.setControlledObject(p);

                    AIs.put(p, bot);
                }
            }
        } while (loop);
    }

    public RenderThread getRenderer() {
        return this.renderer;
    }

    public SpriteManager getSpriteManager() {
        return this.sm;
    }

    public Window getWindow() {
        return this.w;
    }

    public Player getLocalPlayer() {
        return this.localPlayer;
    }

    public int getAIlives() {
        return this.AIlives;
    }
}