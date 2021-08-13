package com.generic.gameplay;

import com.generic.core.*;
import com.generic.core.blocks.DiamondBlock;
import com.generic.core.blocks.IceBlock;
import com.generic.core.entities.Animal;
import com.generic.core.entities.Penguin;
import com.generic.launcher.Launcher;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;
import static com.generic.utils.Equations.RandomizedInt;

public abstract class AbstractGame extends Thread{
    public static AbstractGame instance;

    protected MapGenerator mg;
    protected GameMap map;
    protected GameTimer time;
    protected Launcher launcher = Launcher.instance;

    public AbstractGame() {
        instance = this;

        map = new GameMap(GRID_WIDTH, GRID_HEIGHT);
        mg = new MapGenerator(null);

        time = new GameTimer();
    }

    public void initDiamondBlocks() {
        for (int k = 0; k < 3; k++) {
            boolean loop = true;
            do {
                int initX = RandomizedInt(1, GRID_WIDTH - 2);
                int initY = RandomizedInt(1, GRID_HEIGHT - 2);

                if (map.getAt(initX, initY) != null) {
                    if (map.getAt(initX, initY) instanceof IceBlock) {
                        loop = false;
                        MapObjectFactory.createDiamondBlock(initX, initY, this.map);
                    }
                }
            } while (loop);
        }
    }

    public void checkDiamondBlocks(DiamondBlock db) {



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

    public abstract void postGame();

    public abstract void respawnAnimal(Object owner);

    public abstract void respawnPenguin(Object owner);

    public abstract void endGame();

    public abstract void animalKilled(Animal a, MapObject killer);

    public abstract void penguinKilled(Penguin p, MapObject killer);

    public abstract void stunTriggered(char dirMur);

    public GameMap getMap() {
        return this.map;
    }
}