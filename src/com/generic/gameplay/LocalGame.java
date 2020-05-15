package com.generic.gameplay;

import com.generic.AI.AI;
import com.generic.UI.GameEndDialog;
import com.generic.core.*;
import com.generic.graphics.RenderThread;
import com.generic.graphics.SpriteManager;
import com.generic.graphics.Window;
import com.generic.launcher.Launcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.CONFIG_GAME.*;
import static com.generic.utils.Equations.RandomizedInt;
import static java.lang.Thread.sleep;

public class LocalGame extends AbstractGame {

    private HashMap<MapEntity, AI> AIs;

    private RenderThread renderer;
    private SpriteManager sm;
    private Window w;

    private LocalPlayer localPlayer;
    private Thread LPThread;

    private int AIlives = AI_INIT_LIVES;

    private boolean AIwin;

    public LocalGame() {
        super();
        map.setLocal(true);

        AIs = new HashMap<MapEntity, AI>();
        localPlayer = launcher.getMainProfile();
        sm = SpriteManager.createSpriteManager();

        renderer = new RenderThread();
        this.w = renderer.getWindow();

        renderer.setClient(false);
        renderer.start();

        mg.pre_init();
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
                        Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                        ai.setControlledObject(a);
                        AIs.put(a, ai);
                    } else if (PLAYER_IS_ANIMAL) {
                        Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
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
                    Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
                    localPlayer.setControlledObject(p);
                } else if (PLAYER_IS_ANIMAL) {
                    Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                    localPlayer.setControlledObject(a);
                }

                LPThread = new Thread(localPlayer);
                LPThread.start();
            }
        } while (loop);
    }

    public void start() {
        time.start();
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    @Override
    public void gameEnd() {
        time.stopTimer();
        if (!AIwin) {
            localPlayer.setPoints("GameEnd", time.getTime());
        }
        stop();
        GameEndDialog GED = new GameEndDialog(w, false, !AIwin, time.getTime(), localPlayer.getPoints());
        try {
            sleep(5000);
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
            try {
                System.out.println("En attente de l'arrêt d'un Thread IA");
                tmp.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            it.remove();
        }


        try {
            System.out.println("En attente de l'arrêt d'un Thread Player");
            localPlayer.flush();
            //LPThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.deleteMap();

    }

    public void animalKilled(Animal a, MapObject killer) {
        if (PLAYER_IS_PENGUIN) {
            AI owner = AIs.get(a);
            owner.setControlledObject(null);

            localPlayer.setPoints("AnimalKilled", 0);

            AIlives = AIlives - 1;
            if (AIlives == 0) {
                AIwin = false;
                gameEnd();
            } else {
                respawnAnimal(owner);
            }
        } else if (PLAYER_IS_ANIMAL) {
            localPlayer.setControlledObject(null);
            localPlayer.removeLive();
        }
    }

    public void stunTriggered(char dirMur) {
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
            localPlayer.setControlledObject(null);
            localPlayer.removeLive();
        } else if (PLAYER_IS_ANIMAL) {
            AI owner = AIs.get(p);
            owner.setControlledObject(null);

            localPlayer.setPoints("PenguinKilled", 0);

            AIlives = AIlives - 1;
            if (AIlives == 0) {
                AIwin = false;
                gameEnd();
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
                Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                if (PLAYER_IS_PENGUIN) {
                    AI bot = (AI) (owner);
                    bot.setControlledObject(a);
                    AIs.put(a, bot);
                } else if (PLAYER_IS_ANIMAL) {
                    LocalPlayer pl = (LocalPlayer) (owner);
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
                Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);

                if (PLAYER_IS_PENGUIN) {
                    LocalPlayer pl = (LocalPlayer) owner;
                    pl.setControlledObject(p);
                } else if (PLAYER_IS_ANIMAL) {
                    AI bot = (AI) (owner);
                    bot.setControlledObject(p);

                    AIs.put(p, bot);
                }
            }
        } while (loop);
    }

    public Window getWindow() {
        return this.w;
    }

    public int getAIlives() {
        return this.AIlives;
    }

    public void setAIwin(boolean val) {
        this.AIwin = val;
    }
}