package com.generic.AI;

import com.generic.gameplay.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;

import java.util.ArrayList;

import static com.generic.utils.Equations.RandomizedInt;
import static com.generic.utils.Equations.VectorialDistance;

public class AStarInvertStrategy implements Strategy {
    public char direction;
    private MapObject controlledObject;
    private MapObject target;
    private ArrayList<MapObject> targetList;

    public AStarInvertStrategy() {
        targetList = new ArrayList<MapObject>();
    }

    public void flush()
    {
        direction = '\0';
        controlledObject = null;
        target = null;
        targetList.clear();
    }

    public void acquireTarget() {
        targetList.clear();
        GameMap m = AbstractGame.instance.getMap();

        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++) {
                MapObject tmp = m.getAt(j, i);
                if (controlledObject.getType().equals("Penguin")) {
                    if (tmp.getType().equals("Animal")) {
                        targetList.add(tmp);
                    }
                } else if (controlledObject.getType().equals("Animal")) {
                    if (tmp.getType().equals("Penguin")) {
                        targetList.add(tmp);
                    }
                }
            }
        }

        int rand = RandomizedInt(0, targetList.size() - 1);
        if (rand < targetList.size()) {
            target = targetList.get(rand);
        }
    }

    public void process() {
        if (target != null) {
            GameMap m = AbstractGame.instance.getMap();
            if (m.getAt(target.getX(), target.getY()) == target) {
                testDirection();

                switch (direction) {
                    case 'H':
                        controlledObject.goUp();
                        break;
                    case 'B':
                        controlledObject.goDown();
                        break;
                    case 'G':
                        controlledObject.goLeft();
                        break;
                    case 'D':
                        controlledObject.goRight();
                        break;
                }
            } else {
                acquireTarget();
            }
        } else {
            acquireTarget();
        }
    }

    public void testDirection() {
        GameMap m = AbstractGame.instance.getMap();

        int x = controlledObject.getX();
        int y = controlledObject.getY();

        double d_left = VectorialDistance(x - 1, target.getX(), y, target.getY());
        double d_right = VectorialDistance(x + 1, target.getX(), y, target.getY());
        double d_up = VectorialDistance(x, target.getX(), y - 1, target.getY());
        double d_down = VectorialDistance(x, target.getX(), y + 1, target.getY());

        if (d_up >= d_down && d_up >= d_left && d_up >= d_right) {
            direction = 'H';
        } else if (d_down > d_up && d_down >= d_left && d_down >= d_right) {
            direction = 'B';
        } else if (d_left > d_up && d_left >= d_right && d_left > d_down) {
            direction = 'G';
        } else if (d_right > d_up && d_right > d_down && d_right > d_left) {
            direction = 'D';
        }

        if (controlledObject.getX() == target.getX()) {
            if (x == 0) {
                direction = 'D';
            } else {
                direction = 'G';
            }
        } else if (controlledObject.getY() == target.getY()) {
            if (y == 0) {
                direction = 'B';
            } else {
                direction = 'H';
            }
        }
    }

    public void updateControlledObject(MapObject co) {
        this.controlledObject = co;
    }
}
