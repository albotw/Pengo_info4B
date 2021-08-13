package com.generic.AI;

import com.generic.AI.strategy.*;
import com.generic.core.entities.Animal;
import com.generic.core.MapObject;
import com.generic.gameplay.events.Event;
import com.generic.gameplay.events.EventIO;
import com.generic.gameplay.events.ThreadID;
import com.generic.gameplay.v2.GameController;

import java.util.ArrayDeque;
import java.util.Queue;

import static com.generic.gameplay.config.CONFIG.AI_TICK_RATE;
import static com.generic.gameplay.config.CONFIG.STUN_TIME;
import static com.generic.utils.Equations.RandomizedInt;

public class AI extends Thread implements EventIO {
    private Queue<Event> eventQueue;
    private ThreadID ID;

    private MapObject target;
    private MapObject controlledObject;

    private boolean stunActive;
    private int stunTimer;

    private boolean respawnActive;
    private int respawnTimer;

    private boolean running = true;

    private int tickRate = AI_TICK_RATE;

    private Strategy currentStrat;

    public AI(ThreadID id) {
        this.ID = id;
        this.eventQueue = new ArrayDeque<Event>();

        GameController.instance.addThread(this);
        GameController.instance.subscribe(this.ID, this);
    }

    public void run() {
        while (running) {
            //Vérification des messages et traitement
            if (!eventQueue.isEmpty())
            {
                Event e = eventQueue.poll();
                //TODO: ajouter traitements
            }
            //
            else if (controlledObject != null) {
                checkStun();
                checkRespawn();
                if (!respawnActive) {
                    try{
                        currentStrat.process();
                    }catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
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
        currentStrat.flush();
        System.out.println("--- Arrêt Thread IA ---");
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
        if (controlledObject instanceof Animal) {
            Animal a = (Animal) (controlledObject);
            if (stunActive && a.isStun()) {
                stunActive = true;
                stunTimer = STUN_TIME;
                System.out.println("Stun Actif " + stunTimer + " ms restant");
            }

            if (stunActive) {
                stunTimer -= AI_TICK_RATE;
                System.out.println("Stun Actif " + stunTimer + " ms restant");
            }

            if (stunTimer <= 0) {
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
            stunActive = false;
            stunTimer = 0;
        }
        currentStrat.updateControlledObject(controlledObject);
    }

    public void setCurrentStrat() {
        int rand = RandomizedInt(1, 4);
        switch (rand) {
            case 1: {
                currentStrat = new AStarInvertStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante("STALFOS");
                }
                System.out.println("InvASTAR");
                break;
            }


            case 2: {
                currentStrat = new AStarStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante("MOLBLIN");
                }
                System.out.println("ASTAR");
                break;
            }


            case 3: {
                currentStrat = new DefendDiamondBlockStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante("DARKNUT");
                }
                System.out.println("DDB");
                break;
            }


            case 4: {
                currentStrat = new RandStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante("LEECHER");
                }
                System.out.println("RAND");
                break;
            }

        }
    }

    @Override
    public void grab(Event e) {

    }
}
