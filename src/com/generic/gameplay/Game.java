package com.generic.gameplay;

import com.generic.UI.GameEndDialog;
import com.generic.UI.ImagePanel;
import com.generic.core.*;
import com.generic.graphics.Window;
import com.generic.launcher.Launcher;
import com.generic.player.*;
import com.generic.graphics.*;
import com.generic.AI.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.CONFIG_GAME.AI_INIT_LIVES;
import static com.generic.utils.Equations.RandomizedInt;
import static java.lang.Thread.sleep;

/**
 * TODO: rendre animalKilled/penguinKilled indépendant de IA ou Player
 */

public class Game {
    public static Game instance;

    private HashMap<MapEntity, AI> AIs;

    private RenderThread renderer;
    private SpriteManager sm;
    private Window w;
    private MapGenerator mg;
    private Map m;
    private Player localPlayer;
    private PlayerManager pm = PlayerManager.instance;
    private GameTimer time;

    private int AIlives = 1;

    public Game() {
        instance = this;


        m = Map.createMap(GRID_WIDTH, GRID_HEIGHT);
        mg = new MapGenerator();

        AIs = new HashMap<MapEntity, AI>();


        localPlayer = pm.getMainProfile();
        //localPlayer = new Player("Yann");

        time = new GameTimer();

        w = new Window(CONFIG.WINDOW_WIDTH, CONFIG.WINDOW_HEIGHT);
        sm = SpriteManager.createSpriteManager();
        renderer = new RenderThread(w);
        renderer.start();

        mg.path_init();

        start();
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
                    Animal a = new Animal(initX, initY);
                    m.place(a, initX, initY);
                    AI ai = new AI();
                    ai.setControlledObject(a);
                    ai.setTarget(localPlayer.getControlledObject());
                    ai.start();

                    AIs.put(a, ai);
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
                Penguin p = new Penguin(initX, initY);
                m.place(p, initX, initY);
                localPlayer.setControlledObject(p);
                localPlayer.start();

            }
        } while (loop);
    }

    public void start() {
        time.start();
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    public void gameOver() {
        //a ajouter: déréférencement dans les objets
        time.stopTimer();
        AIs.clear();
        Map.deleteMap();
        System.out.println("Score");
        System.out.println("DEFAITE");
        GameEndDialog GED = new GameEndDialog(w,true,false);
        try {
            sleep(2000);
        }catch(Exception e){e.printStackTrace();}

        Launcher.instance.onGameEnded();
        renderer.stopRendering();
    }

    public void victory()
    {
        //a ajouter: déréférencement dans les objets.
        pm.getMainProfile().setPoints("GameEnded", time.getTime());
        time.stopTimer();
        AIs.clear();
        Map.deleteMap();
        System.out.println("Score");
        System.out.println("VICTOIRE");
        GameEndDialog ged = new GameEndDialog(w, true, true);
        Launcher.instance.onGameEnded();
        renderer.stopRendering();
    }

    public void animalKilled(Animal a, MapObject killer) {

        AI owner = AIs.get(a);
        owner.setControlledObject(null);

    localPlayer.setPoints("AnimalKilled", 0);
    System.out.println("Animal Tué");
    AIlives = AIlives - 1;
    if (AIlives == 0) {
        victory();
    }
    else{
        respawnAnimal(owner);
    }
    }

    public void checkDiamondBlocks()
    {

        /**
         * TODO: Optimisation
         */
        Map m = Game.instance.getMap();
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                MapObject tmp = m.getAt(i, j);

                if ( m.getAt(i,j) != null)
                {
                    if (m.getAt(i, j).getType().equals("DiamondBlock"))
                    {
                        if (m.getAt(i + 1, j) != null && m.getAt(i + 2, j) != null)
                        {
                            if (((m.getAt(i + 1, j).getType().equals("DiamondBlock") && m.getAt(i + 2, j).getType().equals("DiamondBlock"))))
                            {
                                victory();
                            }
                            if (m.getAt(i, j + 1) != null && m.getAt(i, j + 2) != null) {
                                if (((m.getAt(i, j + 1).getType().equals("DiamondBlock") && m.getAt(i, j + 2).getType().equals("DiamondBlock")))) {
                                    victory();
                                }

                            }

                        }
                    }
                }
            }
        }
    }

    public void stunTriggered(char dirMur)
    {
        /**
         * TODO: Optimisation
         */
        System.out.println("STUN!");

        switch(dirMur)
        {
            case 'G':
                for (int i = 0; i < GRID_HEIGHT; i++)
                {
                    MapObject mo = m.getAt(0, i);
                    if (mo != null)
                    {
                        if (mo.getType().equals("Animal"))
                        {
                            ((Animal)(mo)).activateStun();
                        }
                    }
                }
                break;

            case 'D':
                for (int i = 0; i < GRID_HEIGHT; i++)
                {
                    MapObject mo = m.getAt(GRID_WIDTH - 1, i);
                    if (mo != null)
                    {
                        if (mo.getType().equals("Animal"))
                        {
                            ((Animal)(mo)).activateStun();
                        }
                    }
                }
                break;

            case 'H':
                for (int i = 0; i < GRID_WIDTH; i++)
                {
                    MapObject mo = m.getAt(i, 0);
                    if (mo != null)
                    {
                        if (mo.getType().equals("Animal"))
                        {
                            ((Animal)(mo)).activateStun();
                        }
                    }
                }
                break;

            case 'B':
                for (int i = 0; i < GRID_WIDTH; i++)
                {
                    MapObject mo = m.getAt(i, GRID_HEIGHT - 1);
                    if (mo != null)
                    {
                        if (mo.getType().equals("Animal"))
                        {
                            ((Animal)(mo)).activateStun();
                        }
                    }
                }
                break;
        }

    }

    public void penguinKilled(Penguin p, MapObject Killer)
    {
        System.out.println("Pingouin tué");
        localPlayer.setControlledObject(null);
        localPlayer.removeLive();
    }

        public void respawnAnimal(AI owner) {
            boolean loop = true;
            do {

                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (m.getAt(initX, initY) != null) {
                    if (m.getAt(initX, initY).getType().equals("IceBlock")) {
                        loop = false;
                        Animal a = new Animal(initX, initY);
                        m.place(a, initX, initY);
                        owner.setControlledObject(a);

                        AIs.put(a, owner);
                    }
                }
            } while (loop);
        }

    public void respawnPenguin(Player owner)
    {
        try{
            sleep(500);
        }catch(Exception e){e.printStackTrace();}

        boolean loop = true;
        do {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);

            if (m.getAt(initX, initY) == null)
            {
                loop = false;
                Penguin p = new Penguin(initX, initY);
                m.place(p, initX, initY);
                owner.setControlledObject(p);
            }
        }while (loop);
    }


        public Map getMap() {
            return this.m;
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