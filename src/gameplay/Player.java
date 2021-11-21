package gameplay;

import core.entities.MapEntity;
import events.*;
import events.types.Event;
import events.types.PlayerLostEvent;
import events.types.RespawnPenguinEvent;
import events.types.ShutdownEvent;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import static config.CONFIG_GAME.PLAYER_INIT_LIVES;


public class Player extends Thread implements NativeKeyListener, MapObjectController {

    private volatile boolean running;
    private NativeKeyEvent currentInput = null;
    private MapEntity controlledObject;

    private int lives;
    private int points;
    private int nextLiveCounter;
    private int nextLiveThreshold = 4000;

    private EventQueue eventQueue;
    private ThreadID ID;

    public Player () {
        this.ID = ThreadID.LocalPlayer;
        this.eventQueue = new EventQueue(this.ID);
        GameController.instance.addThread(this);

        this.lives = PLAYER_INIT_LIVES;
        this.points = 0;
    }

    @Override
    public void start()
    {
        super.start();
        GlobalScreen.addNativeKeyListener(this);
    }

    public void run() {
        System.out.println("--- Thread player démarré ---");
        this.running = true;

        while (running) {
            if (!eventQueue.isEmpty())
            {
                Event e = eventQueue.get();
                if (e instanceof ShutdownEvent)
                {
                    this.running = false;
                }
            }
            if (controlledObject != null && currentInput != null)
            {
                switch(currentInput.getKeyCode())
                {
                    case NativeKeyEvent.VC_Z:
                        controlledObject.goUp();
                        break;
                    case NativeKeyEvent.VC_S:
                        controlledObject.goDown();
                        break;
                    case NativeKeyEvent.VC_Q:
                        controlledObject.goLeft();
                        break;
                    case NativeKeyEvent.VC_D:
                        controlledObject.goRight();
                        break;
                    case NativeKeyEvent.VC_SPACE:
                        controlledObject.action();
                        break;
                }

                currentInput = null;
            }

            try {
                sleep(16);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.controlledObject = null;
        System.out.println("--- Thread player arrété ---");
    }

    public void shutdown() {
        running = false;
    }

    public void removeLive() {
        lives--;
        if (lives <= 0) {
            eventQueue.send(new PlayerLostEvent(), ThreadID.Controller);
        }
        else
        {
            eventQueue.send(new RespawnPenguinEvent(this), ThreadID.Controller);
        }
    }

    public void addKillPoints()
    {
        this.points += 4000;
        this.nextLiveCounter += 4000;
        addLive();
    }

    public void addLive()
    {
        if (nextLiveCounter >= nextLiveThreshold)
        {
            lives++;
            nextLiveThreshold *= 1.2;
        }
    }

    public void addEndPoints(int time)
    {
        if (time <= 20)
        {
            points += 5000;
            nextLiveCounter += 5000;
        }
        else if (time <= 30)
        {
            points += 2000;
            nextLiveCounter += 2000;
        }
        else if (time <= 40)
        {
            points += 1000;
            nextLiveCounter += 1000;
        }
        else if (time <= 60)
        {
            points += 500;
            nextLiveCounter += 500;
        }
        addLive();
    }

    @Override
    public MapEntity getControlledObject() {
        return this.controlledObject;
    }

    @Override
    public void setControlledObject(MapEntity me){
        this.controlledObject = me;
        me.setController(this);
    }

    @Override
    public void clearControlledObject(){
        this.controlledObject = null;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        currentInput = nativeKeyEvent;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) { }
}