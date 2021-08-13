package com.generic.AI.strategy;

import com.generic.core.blocks.DiamondBlock;
import com.generic.gameplay.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;

import static com.generic.utils.Equations.RandomizedInt;

public class RandStrategy implements Strategy {

    public char direction;
    private MapObject controlledObject;

    public RandStrategy() {
        int rand = RandomizedInt(0, 3);
        switch (rand) {
            case 1:
                direction = 'H';
                break;
            case 2:
                direction = 'B';
                break;
            case 3:
                direction = 'G';
                break;
            case 4:
                direction = 'D';
                break;
        }
    }

    public void flush()
    {
        direction = '\0';
        controlledObject = null;
    }

    public void process() {
        int rand = RandomizedInt(0, 10);
        if (testDirections() && rand <= 8) {
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
            refreshDirection();
        }
    }

    private boolean testDirections() {
        //si direction ou on s'en va est bloquée: retourne false
        //sinon retourne true.
        GameMap m = AbstractGame.instance.getMap();
        int x = controlledObject.getX();
        int y = controlledObject.getY();

        boolean output = true;
        switch (direction) {
            case 'H': {
                if (m.getAt(x, y - 1)instanceof DiamondBlock || y == 0) {
                    output = false;
                }
                break;
            }

            case 'B': {
                if (m.getAt(x, y + 1) instanceof DiamondBlock || y == m.getHeight() - 1) {
                    output = false;
                }
                break;
            }

            case 'G': {
                if (m.getAt(x - 1, y)instanceof DiamondBlock || x == 0) {
                    output = false;
                }
                break;
            }

            case 'D': {
                if (m.getAt(x + 1, y)instanceof DiamondBlock || x == m.getWidth() - 1) {
                    output = false;
                }
                break;
            }
        }

        return output;
    }

    private void refreshDirection() {
        int rand = RandomizedInt(0, 3);
        if (rand == 0 && direction != 'H') {
            direction = 'H';
        } else if (rand == 1 && direction != 'B') {
            direction = 'B';
        } else if (rand == 2 && direction != 'G') {
            direction = 'G';
        } else if (rand == 3 && direction != 'D') {
            direction = 'D';
        }
    }

    public void updateControlledObject(MapObject co) {
        this.controlledObject = co;
    }
}
