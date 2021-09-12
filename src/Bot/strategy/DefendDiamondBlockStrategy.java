package Bot.strategy;

import core.MapObject;
import core.blocks.DiamondBlock;
import core.blocks.IceBlock;
import core.blocks.MapBlock;
import gameplay.GameController;
import gameplay.GameMap;

import java.util.ArrayList;

import static utils.Equations.RandomizedInt;
import static utils.Equations.VectorialDistance;

public class DefendDiamondBlockStrategy implements Strategy {
    public char direction;
    private MapObject controlledObject;
    private MapObject target;
    private GameMap m = GameController.instance.getMap();
    private ArrayList<MapObject> targetList;

    public DefendDiamondBlockStrategy() {
        targetList = new ArrayList<MapObject>();
    }

    public void flush()
    {
        direction = '\0';
        controlledObject = null;
        target = null;
        m = null;
        targetList.clear();
    }

    public void updateControlledObject(MapObject o) {
        this.controlledObject = o;
    }

    public void process() {
        if (target != null) {
            if (m.getAt(target.getX(), target.getY()) == target) {
                setDirection();
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

    public void acquireTargetList() {
        targetList.clear();
        GameMap m = GameController.instance.getMap();

        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++) {
                MapObject tmp = m.getAt(j, i);
                if (tmp instanceof DiamondBlock) {
                    targetList.add(tmp);
                }
            }
        }

        int rand = RandomizedInt(0, targetList.size() - 1);
        target = targetList.get(rand);
    }

    public void acquireTarget() {
        if (target == null) {
            acquireTargetList();
        }

        int x = target.getX();
        int y = target.getY();

        int rayon = RandomizedInt(1, 3);

        int searchX = x - rayon;
        int searchY = y - rayon;
        boolean loop = true;
        do {
            MapObject tmp = GameController.instance.getMap().getAt(searchX, searchY);
            if (tmp instanceof IceBlock) {
                loop = false;
                target = tmp;
            } else {
                if (searchX < (x + rayon)) searchX++;
                else {
                    searchX = x - rayon;
                    searchY++;
                }
            }
        } while (searchY < (y + rayon) && loop);

        if (loop == false) {
            acquireTargetList();
        }
    }

    private void setDirection() {
        int x = controlledObject.getX();
        int y = controlledObject.getY();

        double d_left = VectorialDistance(x - 1, target.getX(), y, target.getY());
        double d_right = VectorialDistance(x + 1, target.getX(), y, target.getY());
        double d_up = VectorialDistance(x, target.getX(), y - 1, target.getY());
        double d_down = VectorialDistance(x, target.getX(), y + 1, target.getY());

        if (d_up <= d_down && d_up <= d_left && d_up <= d_right) {
            direction = 'H';
        } else if (d_down < d_up && d_down <= d_left && d_down <= d_right) {
            direction = 'B';
        } else if (d_left < d_up && d_left <= d_right && d_left < d_down) {
            direction = 'G';
        } else if (d_right < d_up && d_right < d_down && d_right < d_left) {
            direction = 'D';
        }

        if (m.getAt(x - 1, y) instanceof MapBlock) {
            int rand = RandomizedInt(0, 1);
            if (rand == 0) direction = 'H';
            else direction = 'B';
        } else if (m.getAt(x, y - 1) instanceof MapBlock) {
            int rand = RandomizedInt(0, 1);
            if (rand == 0) direction = 'G';
            else direction = 'D';
        }
    }
}
