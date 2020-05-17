package com.generic.gameplay;

import com.generic.core.MapObject;
import com.generic.core.MapObjectFactory;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.utils.Equations.RandomizedInt;

public class MapGenerator {
    int x = 0;
    int y = GRID_HEIGHT - 1;
    GameMap m;

    public MapGenerator() {
        m = AbstractGame.instance.getMap();
    }

    public void pre_init() {
        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++) {
                MapObjectFactory.createIceBlock(j, i, this.m);
            }
        }
        m.release(x, y);
    }

    public void path_init() {
        y = GRID_HEIGHT - 1;
        for (; y >= 0; y = y - 2) {
            x = 0;
            for (; x < GRID_WIDTH; x = x + 2) {
                // System.out.println("position courante x = " + x + " | y = " + y);
                if (m.getAt(x, y).getType().equals("void")) {

                    if (!isEmpty(x, y - 2) && (y - 2) >= 0) {
                        // System.out.println("part en haut vers [" + x + "," + (y - 2) + "]");
                        path_generating();
                    }
                    if (!isEmpty(x, y + 2) && (y + 2) < GRID_HEIGHT) {
                        // System.out.println("part en bas vers [" + x + "," + (y + 2) + "]");
                        path_generating();
                    }
                    if (!isEmpty(x - 2, y) && (x - 2) >= 0) {
                        // System.out.println("part à gauche vers [" + (x - 2) + "," + y + "]");
                        path_generating();
                    }
                    if (!isEmpty(x + 2, y) && (x + 2) < GRID_WIDTH) {
                        // System.out.println("part à droite vers [" + (x + 2) + "," + y + "]");
                        path_generating();
                    }
                }
            }
        }

    }

    public void path_generating() {
        boolean loop = true;
        while (loop) {
            int dir = RandomizedInt(0, 3);

            if (dir == 0 && (y - 2) >= 0 && !isEmpty(x, y - 2)) {
                loop = false;
                m.release(x, y - 1);
                m.release(x, y - 2);
                y = y - 2;
                // System.out.println("a supprimé en haut [" + x + "," + (y - 1) + "], [" + x +
                // "," + (y - 2) + "]");
                path_continue();
            } else if (dir == 1 && (y + 2) < GRID_HEIGHT && !isEmpty(x, y + 2)) {
                loop = false;
                m.release(x, y + 1);
                m.release(x, y + 2);
                y = y + 2;
                // System.out.println("a supprimé en bas [" + x + "," + (y + 1) + "], [" + x +
                // "," + (y + 2) + "]");
                path_continue();
            } else if (dir == 2 && (x - 2) >= 0 && !isEmpty(x - 2, y)) {
                loop = false;
                m.release(x - 1, y);
                m.release(x - 2, y);
                x = x - 2;
                // System.out.println("a supprimé à gauche [" + (x - 1) + "," + y + "], [" + (x
                // - 2) + "," + y + "]");
                path_continue();
            } else if (dir == 3 && (x + 2) < GRID_WIDTH && !isEmpty(x + 2, y)) {
                loop = false;
                m.release(x + 1, y);
                m.release(x + 2, y);
                x = x + 2;
                // System.out.println("a supprimé à droite [" + (x + 1) + "," + y + "], [" + (x
                // + 2) + "," + y + "]");
                path_continue();
            }

            // System.out.println("direction = " + dir);
        }
    }

    public void path_continue() {
        try {
            Thread.currentThread().sleep(75);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isEmpty(x, y - 2))
            path_generating();
        else if (!isEmpty(x, y + 2))
            path_generating();
        else if (!isEmpty(x - 2, y))
            path_generating();
        else if (!isEmpty(x + 2, y))
            path_generating();
        else
            path_init();
    }

    public boolean isEmpty(int x, int y) {
        MapObject mo = m.getAt(x, y);
        if (mo.getType().equals("void")) {
            return true;
        } else {
            return false;
        }
    }
}
