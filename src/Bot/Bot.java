package Bot;

import Bot.strategy.*;
import core.MapObject;
import core.entities.MapEntity;
import events.EventQueue;
import events.types.ShutdownEvent;
import gameplay.MapObjectController;
import graphics.TextureID;
import core.entities.Animal;
import events.types.Event;
import events.ThreadID;
import gameplay.GameController;

import static config.CONFIG.AI_TICK_RATE;
import static config.CONFIG.STUN_TIME;

public class Bot extends Thread implements MapObjectController {
    private EventQueue eventQueue;
    private ThreadID ID;

    private MapObject target;
    private MapEntity controlledObject;

    private boolean stunActive;
    private int stunTimer;

    private boolean respawnActive;
    private int respawnTimer;

    private boolean running = true;

    private int tickRate = AI_TICK_RATE;

    private Strategy currentStrat;

    public Bot(ThreadID id) {
        this.ID = id;
        this.eventQueue = new EventQueue(this.ID);

        GameController.instance.addThread(this);
    }

    public void run() {
        while (running) {
            //Vérification des messages et traitement
            if (!eventQueue.isEmpty())
            {
                Event e = eventQueue.get();
                if (e instanceof ShutdownEvent)
                {
                    this.running = false;
                }
            }

            else if (controlledObject != null) {
                checkStun();
                checkRespawn();
                if (!respawnActive) {
                    try{
                        currentStrat.process();
                    }catch(Exception e)
                    {
                        System.out.println("Erreur pendant le traitement de la stratégie");
                        e.printStackTrace(System.err);
                    }
                }
            }

            try {
                sleep(tickRate);
            } catch (Exception e) {
                System.out.println("Erreur IA");
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

    @Override
    public void setControlledObject(MapEntity controlledObject) {
        this.controlledObject = controlledObject;
        controlledObject.setController(this);
        setCurrentStrat();
        respawnActive = true;
        respawnTimer = 2000;
        stunActive = false;
        stunTimer = 0;
        currentStrat.updateControlledObject(controlledObject);
    }

    @Override
    public void clearControlledObject()
    {
        this.controlledObject = null;
    }

    public void setCurrentStrat() {
        //FIXME
        //int rand = RandomizedInt(1, 4);
        int rand = 2;
        switch (rand) {
            case 1: {
                currentStrat = new AStarInvertStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante(TextureID.STALFOS);
                }
                System.out.println("InvASTAR");
                break;
            }


            case 2: {
                currentStrat = new AStarStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante(TextureID.MOLBLIN);
                }
                System.out.println("ASTAR");
                break;
            }


            case 3: {
                currentStrat = new DefendDiamondBlockStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante(TextureID.DARKNUT);
                }
                System.out.println("DDB");
                break;
            }


            case 4: {
                currentStrat = new RandStrategy();
                if (controlledObject instanceof Animal) {
                    ((Animal) controlledObject).setVariante(TextureID.LEECHER);
                }
                System.out.println("RAND");
                break;
            }

        }
    }

    @Override
    public MapEntity getControlledObject() {
        return this.controlledObject;
    }
}
