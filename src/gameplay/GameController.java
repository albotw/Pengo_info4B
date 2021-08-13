package gameplay;


import events.Event;
import events.EventIO;
import events.ThreadID;
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

public class GameController extends Thread implements EventIO {
    public static GameController instance = null;
    public Queue<Event> eventQueue;
    public volatile boolean running = true;
    public ThreadID ID = ThreadID.Game;

    private HashMap<ThreadID, EventIO> eventChannel;

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

        eventChannel = new HashMap<ThreadID, EventIO>();
        subscribe(this.ID, this);

        threadSet = new ArrayList<Thread>();
        eventQueue = new ArrayDeque<Event>();
    }

    public void start()
    {
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
        renderer = null;
        mg = null;
        time = null;

        for(Thread t : threadSet)
        {
            try{
                t.interrupt();
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

    public void subscribe(ThreadID ID, EventIO eio)
    {
        eventChannel.put(ID, eio);
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

    public void run()
    {
        while (running)
        {
            if (!eventQueue.isEmpty())
            {
                Event e = eventQueue.poll();
                if (e instanceof Event)
                {
                    // faire des trucs
                }
            }
        }
    }

    @Override
    public void grab(Event e) {
        eventQueue.add(e);
    }

    public GameMap getMap() { return this.map; }
}
