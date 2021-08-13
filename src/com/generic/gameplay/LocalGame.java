package com.generic.gameplay;

import com.generic.AI.AI;
import com.generic.UI.GameEndDialog;
import com.generic.core.*;
import com.generic.core.blocks.IceBlock;
import com.generic.core.entities.Animal;
import com.generic.core.entities.MapEntity;
import com.generic.core.entities.Penguin;
import com.generic.gameplay.events.ThreadID;
import com.generic.graphics.RenderThread;
import com.generic.graphics.SpriteManager;
import com.generic.graphics.Window;
import com.generic.launcher.Launcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.config.CONFIG_GAME.*;
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

    private boolean stopGame = false;

    public LocalGame() {
        super();
        map.setLocal(true);

        AIs = new HashMap<MapEntity, AI>();
        localPlayer = launcher.getMainProfile();
        this.sm = SpriteManager.createSpriteManager();

        renderer = new RenderThread();
        this.w = renderer.getWindow();
    }

    public void start()
    {
        //Démarrage du rendu
        renderer.setClient(false);
        renderer.start();

        //Création de la carte
        mg.pre_init();
        mg.path_init();

        //Démarrage de la partie
        time.start();
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    public void run()
    {

    }

    public void initIA() {
        // A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        for (int i = 0; i < N_AI; i++) {
            loop = true;
            do {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (map.getAt(initX, initY) == null) {
                    loop = false;
                    AI ai = new AI(ThreadID.AI_1);

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

            if (map.getAt(initX, initY) == null) {
                loop = false;

                if (PLAYER_IS_PENGUIN) {
                    Penguin p = MapObjectFactory.createPenguin(initX, initY, this.map);
                    localPlayer.setControlledObject(p);
                } else if (PLAYER_IS_ANIMAL) {
                    Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                    localPlayer.setControlledObject(a);
                    a.setVariante("DARKNUT");
                }

                LPThread = new Thread(localPlayer);
                LPThread.start();
            }
        } while (loop);
    }

    @Override
    public void postGame() {
        time.stopTimer();

        if (!AIwin) {
            localPlayer.setPoints("GameEnd", time.getTime());
        }
        endGame();

        GameEndDialog GED = new GameEndDialog(w, false, !AIwin, time.getTime(), localPlayer.getPoints());
        try {
            sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        GED.Fermer();
        renderer.stopRendering();
        renderer = null;
        sm = null;
        w = null;
        AIs.clear();
        LPThread = null;
        localPlayer = null;

        Launcher.instance.onGameEnded();
    }

    public void endGame() {
        Iterator it = AIs.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            AI tmp = (AI) pair.getValue();
            try {
                System.out.println("En attente de l'arrêt d'un Thread IA");
                tmp.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            it.remove();
        }


        try {
            System.out.println("En attente de l'arrêt d'un Thread Player");
            localPlayer.flush();
            LPThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.deleteMap();

    }

    public void animalKilled(Animal a, MapObject killer) {
        if (PLAYER_IS_PENGUIN) {
            AI owner = AIs.get(a);
            AIs.remove(a);
            owner.setControlledObject(null);

            localPlayer.setPoints("AnimalKilled", 0);

            AIlives = AIlives - 1;
            if (AIlives == 0) {
                AIwin = false;
                stopGame = true;
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
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'D':
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    MapObject mo = map.getAt(GRID_WIDTH - 1, i);
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'H':
                for (int i = 0; i < GRID_WIDTH; i++) {
                    MapObject mo = map.getAt(i, 0);
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'B':
                for (int i = 0; i < GRID_WIDTH; i++) {
                    MapObject mo = map.getAt(i, GRID_HEIGHT - 1);
                    if (mo instanceof Animal) {
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
                postGame();
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

            if (map.getAt(initX, initY) instanceof IceBlock) {
                loop = false;
                Animal a = MapObjectFactory.createAnimal(initX, initY, this.map);
                if (PLAYER_IS_PENGUIN) {
                    AI bot = (AI) (owner);
                    bot.setControlledObject(a);
                    AIs.put(a, bot);
                } else if (PLAYER_IS_ANIMAL) {
                    LocalPlayer pl = (LocalPlayer) (owner);
                    pl.setControlledObject(a);
                    a.setVariante("DARKNUT");
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

            if (map.getAt(initX, initY) == null) {
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