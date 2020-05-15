package com.generic.AI;

import com.generic.core.Animal;
import com.generic.core.MapObject;

import static com.generic.gameplay.CONFIG.AI_TICK_RATE;
import static com.generic.gameplay.CONFIG.STUN_TIME;
import static com.generic.utils.Equations.RandomizedInt;

public class AI extends Thread {

    private MapObject target;
    private MapObject controlledObject;

    private boolean stunActive;
    private int stunTimer;

    private boolean respawnActive;
    private int respawnTimer;

    private boolean flush;

    private int tickRate = AI_TICK_RATE;

    private Strategy currentStrat;

    public AI() {

    }

    public void run() {
        while (!flush && !isInterrupted()) {
            if (controlledObject != null && !flush) {
                checkStun();
                checkRespawn();
                if (!respawnActive && !flush) {
                    currentStrat.process();
                }
            } else {
            }

            try {
                sleep(tickRate);
            } catch (Exception e) {
                e.printStackTrace();
                this.interrupt();
            }
        }

        target = null;
        controlledObject = null;
        System.out.println("--- Arrêt Thread IA ---");
    }

    public void flush() {
        flush = true;
    }

    public void checkRespawn() {
        if (respawnActive) {
            respawnTimer -= AI_TICK_RATE;
        }

        if (respawnTimer <= 0) {
            respawnActive = false;
        }
    }

    public void checkStun() {
        if (controlledObject.getType().equals("Animal")) {
            Animal a = (Animal) (controlledObject);
            if (stunActive == false && a.isStun()) {
                stunActive = true;
                stunTimer = STUN_TIME;
                System.out.println("Stun Actif " + stunTimer + " ms restant");
            }

            if (stunActive == true) {
                stunTimer -= AI_TICK_RATE;
                System.out.println("Stun Actif " + stunTimer + " ms restant");
            }

            if (stunTimer == 0) {
                stunActive = false;
                a.deactivateStun();
            }
        }
    }

    public void setTarget(MapObject target) {
        this.target = target;
    }

    public void setControlledObject(MapObject controlledObject) {
        this.controlledObject = controlledObject;
        if (controlledObject != null) {
            setCurrentStrat();
            respawnActive = true;
            respawnTimer = 2000;
        }
        currentStrat.updateControlledObject(controlledObject);
    }

    public void setCurrentStrat() {
        int rand = RandomizedInt(1, 4);
        switch (rand) {
            case 1: {
                currentStrat = new AStarInvertStrategy();
                if (controlledObject.getType().equals("Animal")) {
                    ((Animal) controlledObject).setVariante("STALFOS");
                }
                System.out.println("InvASTAR");
                break;
            }


            case 2: {
                currentStrat = new AStarStrategy();
                if (controlledObject.getType().equals("Animal")) {
                    ((Animal) controlledObject).setVariante("MOLBLIN");
                }
                System.out.println("ASTAR");
                break;
            }


            case 3: {
                currentStrat = new DefendDiamondBlockStrategy();
                if (controlledObject.getType().equals("Animal")) {
                    ((Animal) controlledObject).setVariante("DARKNUT");
                }
                System.out.println("DDB");
                break;
            }


            case 4: {
                currentStrat = new RandStrategy();
                if (controlledObject.getType().equals("Animal")) {
                    ((Animal) controlledObject).setVariante("LEECHER");
                }
                System.out.println("RAND");
                break;
            }

        }
    }

}
