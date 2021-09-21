package gameplay;


import events.*;
import graphics.RenderThread;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static config.CONFIG.GRID_HEIGHT;
import static config.CONFIG.GRID_WIDTH;

public class GameController extends Thread {
    public static GameController instance = null;
    private EventQueue eventQueue;
    private volatile boolean running = true;
    private ThreadID ID = ThreadID.Controller;

    private HashMap<ThreadID, EventQueue> eventChannel;

    private RenderThread renderer;
    protected MapGenerator mg;
    protected GameMap map;
    protected GameTimer time;

    private ArrayList<Thread> threadSet;

    private Gameplay gameplay;

    public static GameController createGameController()
    {
        if (instance == null)
        {
            instance = new GameController();
        }

        return instance;
    }

    private GameController()
    {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        try
        {
            GlobalScreen.registerNativeHook();
        }catch(NativeHookException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        };

        eventChannel = new HashMap<ThreadID, EventQueue>();
        threadSet = new ArrayList<Thread>();
    }

    public void start()
    {
        eventQueue = new EventQueue(ThreadID.Controller);
        super.start();
        map = new GameMap(GRID_WIDTH, GRID_HEIGHT);
        mg = new MapGenerator(this.map);
        time = new GameTimer();
        renderer = new RenderThread();

        map.setLocal(true);

        gameplay = new LocalGameplay();

        renderer.setClient(false);
        renderer.start();

        mg.pre_init();
        mg.path_init();

        time.start();

        gameplay.init();
    }

    public void shutdown()
    {
        eventQueue.broadcast(new ShutdownEvent());
        gameplay.stop();
        renderer.stopRendering();
        time.stopTimer();
        map.clear();

        renderer = null;
        mg = null;
        time = null;

        for(Thread t : threadSet)
        {
            try{
                //t.interrupt();
            }catch(Exception e)
            {
                System.out.println("Un thread a rencontré une erreur lors de son arrêt");
            }
        }
        threadSet.clear();
        eventChannel.clear();

        //Launcher.instance.onGameEnded();
    }


    public void addThread(Thread t)
    {
        threadSet.add(t);
        t.start();
    }

    public void subscribe(ThreadID ID, EventQueue eq)
    {
        eventChannel.put(ID, eq);
    }

    public void removeThread(Thread t)
    {
        threadSet.remove(t);
        t.interrupt();
    }

    public void unsubscribe(ThreadID id)
    {
        eventChannel.remove(id);
    }

    public static void publish(Event e, ThreadID target) { instance.eventChannel.get(target).grab(e);}

    public static void publishToAll(Event e) {
        for (EventQueue eq : instance.eventChannel.values())
        {
            eq.grab(e);
        }
    }

    public void run()
    {
        while (running)
        {
            if (!eventQueue.isEmpty())
            {
                Event e = eventQueue.get();

                if (e instanceof AnimalKilledEvent)
                {
                    AnimalKilledEvent event = (AnimalKilledEvent)e;
                    gameplay.onAnimalKilled(event.getOwner());
                }
                else if (e instanceof PenguinKilledEvent)
                {
                    System.out.println("got penguin killed event");
                    PenguinKilledEvent event = (PenguinKilledEvent) e;
                    gameplay.onPenguinKilled(event.getOwner());
                }
                else if (e instanceof RespawnPenguinEvent)
                {
                    System.out.println("ask for respawn");
                    RespawnPenguinEvent event = (RespawnPenguinEvent) e;
                    gameplay.respawnPenguin(event.getController());
                }
                else if (e instanceof PlayerLostEvent)
                {
                    System.out.println("player lost");
                    running = false;
                }
            }
        }
        shutdown();
    }

    public GameMap getMap() { return this.map; }
}
