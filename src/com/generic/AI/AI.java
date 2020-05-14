package com.generic.AI;

import com.generic.core.Animal;
import com.generic.core.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.AbstractGame;
import com.generic.launcher.Launcher;

import static com.generic.gameplay.CONFIG.*;
import static com.generic.utils.Equations.RandomizedInt;
import static com.generic.utils.Equations.VectorialDistance;

/**
 * TODO: Implémenter pattern Strategy
 */

public class AI extends Thread {

    private MapObject target;
    private MapObject controlledObject;

    private boolean stunActive; // a déplacer dans la classe de controle spé animal
    private int stunTimer; // a déplacer dans la classe de controle spé animal (temps de stun, var locale
                           // basée sur la constante de CONFIG

    private boolean respawnActive;
    private int respawnTimer;

    private boolean flush;

    private int tickRate = AI_TICK_RATE;

    private Strategy strat;

    public AI() {
        strat = new AStarStrategy();
    }

    public void run() {
        while (!flush && !isInterrupted()) {
            System.out.println("test flush = " + flush);
            if (controlledObject != null && !flush)
            {
                checkStun();
                checkRespawn();
                if (!respawnActive && !flush) {
                    strat.process();
                }
            } else {
                System.out.println("reset bannedDir");
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
        System.out.println("arret thread IA");
    }

    public void flush() {
        System.out.println("flush triggered");
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

    public MapObject getTarget() {
        return target;
    }

    public void setTarget(MapObject target) {
        this.target = target;
    }

    public MapObject getControlledObject() {
        return controlledObject;
    }

    public void setControlledObject(MapObject controlledObject) {
        System.out.println("reset bannedDir");
        respawnActive = true;
        respawnTimer = 2000;
        strat.updateControlledObject(controlledObject);
        this.controlledObject = controlledObject;
    }
}
