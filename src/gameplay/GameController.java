package gameplay;


import events.*;
import events.types.*;
import graphics.RenderThread;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static config.CONFIG.GRID_HEIGHT;
import static config.CONFIG.GRID_WIDTH;

public class GameController extends Thread {
    public static GameController instance = null;
    private EventQueue eventQueue;
    private volatile boolean running = true;

    private RenderThread renderer;
    private MapGenerator mg;
    public GameMap map;
    private GameTimer time;
    private EventDispatcher ed;

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

        ed = EventDispatcher.createEventDispatcher();
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


        //Launcher.instance.onGameEnded();
    }


    public void addThread(Thread t)
    {
        threadSet.add(t);
        t.start();
    }


    public void removeThread(Thread t)
    {
        threadSet.remove(t);
        t.interrupt();
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
