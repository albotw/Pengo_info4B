package com.generic.gameplay.v2;

import com.generic.gameplay.GameMap;
import com.generic.gameplay.GameTimer;
import com.generic.gameplay.MapGenerator;
import com.generic.gameplay.events.Event;
import com.generic.gameplay.events.EventIO;
import com.generic.gameplay.events.ThreadID;
import com.generic.graphics.RenderThread;
import com.generic.launcher.Launcher;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;

public class GameController extends Thread implements EventIO{
    public static GameController instance = null;
    public Queue<Event> eventQueue;
    public volatile boolean running = true;
    public ThreadID ID = ThreadID.Game;

    private HashMap<ThreadID, EventIO> eventChannel;

    private RenderThread renderer;
    protected MapGenerator mg;
    protected GameMap map;
    protected GameTimer time;
    protected Launcher launcher = Launcher.instance;

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

        Launcher.instance.onGameEnded();
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
