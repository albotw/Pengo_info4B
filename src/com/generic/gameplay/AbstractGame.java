package com.generic.gameplay;

import com.generic.core.Animal;
import com.generic.core.DiamondBlock;
import com.generic.core.GameMap;
import com.generic.core.MapObject;
import com.generic.core.Penguin;
import com.generic.graphics.Window;

import static com.generic.gameplay.CONFIG.*;
import static com.generic.utils.Equations.*;

public abstract class AbstractGame {
    public static AbstractGame instance;

    protected MapGenerator mg;
    protected GameMap map;
    protected GameTimer time;

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
                        DiamondBlock d = new DiamondBlock(initX, initY);
                        map.place(d, initX, initY);
                    }
                }
            } while (loop && cpt != 3);
        }
    }

    public void checkDiamondBlocks() {

        /**
         * TODO: Optimisation
         */
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                MapObject tmp = map.getAt(i, j);

                if (map.getAt(i, j) != null) {
                    if (map.getAt(i, j).getType().equals("DiamondBlock")) {
                        if (map.getAt(i + 1, j) != null && map.getAt(i + 2, j) != null) {
                            if (((map.getAt(i + 1, j).getType().equals("DiamondBlock")
                                    && map.getAt(i + 2, j).getType().equals("DiamondBlock")))) {
                                victory();
                            }
                            if (map.getAt(i, j + 1) != null && map.getAt(i, j + 2) != null) {
                                if (((map.getAt(i, j + 1).getType().equals("DiamondBlock")
                                        && map.getAt(i, j + 2).getType().equals("DiamondBlock")))) {
                                    victory();
                                }

                            }

                        }
                    }
                }
            }
        }
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