package com.generic.gameplay;

import com.generic.core.*;
import com.generic.graphics.Window;
import com.generic.launcher.Launcher;

import static com.generic.gameplay.CONFIG.*;
import static com.generic.utils.Equations.*;

public abstract class AbstractGame {
    public static AbstractGame instance;

    protected MapGenerator mg;
    protected GameMap map;
    protected GameTimer time;
    protected Launcher launcher = Launcher.instance;

    public AbstractGame() {
        instance = this;

        map = new GameMap(GRID_WIDTH, GRID_HEIGHT);
        mg = new MapGenerator();

        time = new GameTimer();
    }

    public void initDiamondBlocks() {
        boolean loop = true;
        int cpt = 0;
        for (int k = 0; k < 3; k++) {
            loop = true;
            do {
                int initX = RandomizedInt(1, GRID_WIDTH - 2);
                int initY = RandomizedInt(1, GRID_HEIGHT - 2);

                if (map.getAt(initX, initY) != null) {
                    if (map.getAt(initX, initY).getType().equals("IceBlock")) {
                        loop = false;
                        MapObjectFactory.createDiamondBlock(initX, initY, this.map);
                    }
                }
            } while (loop && cpt != 3);
        }
    }

    public void checkDiamondBlocks(DiamondBlock db) {
        System.out.println("### check DiamondBlocks ###");
        int x = db.getX();
        int y = db.getY();

        if (map.getAt(x, y - 1).getType().equals("DiamondBlock")) {
            if (map.getAt(x, y - 2).getType().equals("DiamondBlock")) {
                victory(); // db en position bas, alignement vertical
            }
        }

        if (map.getAt(x, y + 1).getType().equals("DiamondBlock")) {
            if (map.getAt(x, y - 1).getType().equals("DiamondBlock")) {
                victory(); // db en position millieu, alignement vertical
            }
        }

        if (map.getAt(x, y + 1).getType().equals("DiamondBlock")) {
            if (map.getAt(x, y + 2).getType().equals("DiamondBlock")) {
                victory(); // db en position haut, alignement vertical
            }
        }

        if (map.getAt(x - 1, y).getType().equals("DiamondBlock"))
        {
            if (map.getAt(x - 2, y).getType().equals("DiamondBlock"))
            {
                victory(); //db en position droite, alignement horizontal
            }
        }

        if (map.getAt(x - 1, y).getType().equals("DiamondBlock"))
        {
            if (map.getAt(x + 1, y).getType().equals("DiamondBlock"))
            {
                victory(); //db en position milieu, alignement horizontal
            }
        }

        if (map.getAt(x + 1, y).getType().equals("DiamondBlock"))
        {
            if (map.getAt(x + 2, y).getType().equals("DiamondBlock"))
            {
                victory(); //db en position gauche, alignement horizontal
            }
        }



        /**
         * int i = 0; int j = 0; boolean found = false; while(i < GRID_HEIGHT && !found)
         * { while(j < GRID_WIDTH && !found) { MapObject tmp = map.getAt(i, j);
         *
         * if (map.getAt(i, j) != null) { if (map.getAt(i,
         * j).getType().equals("DiamondBlock")) { if (map.getAt(i + 1, j) != null &&
         * map.getAt(i + 2, j) != null) { if (((map.getAt(i + 1,
         * j).getType().equals("DiamondBlock") && map.getAt(i + 2,
         * j).getType().equals("DiamondBlock")))) { victory(); found = true; } if
         * (map.getAt(i, j + 1) != null && map.getAt(i, j + 2) != null) { if
         * (((map.getAt(i, j + 1).getType().equals("DiamondBlock") && map.getAt(i, j +
         * 2).getType().equals("DiamondBlock")))) { victory(); } found = true; }
         *
         * } } } j++; } i++;
         */
    }

    public abstract void initPlayers();

    public abstract void initIA();

    public abstract void start();

    public abstract void gameOver();

    public abstract void respawnAnimal(Object owner);

    public abstract void respawnPenguin(Object owner);

    public abstract void stop();

    public abstract void victory();

    public abstract void animalKilled(Animal a, MapObject killer);

    public abstract void penguinKilled(Penguin p, MapObject killer);

    public abstract void stunTriggered(char dirMur);

    public GameMap getMap() {
        return this.map;
    }

    public GameTimer getGameTimer() {
        return this.time;
    }
}