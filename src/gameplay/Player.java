package gameplay;

import events.*;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class Player extends AbstractPlayer implements NativeKeyListener, EventIO {

    private volatile boolean flush;
    private NativeKeyEvent currentInput = null;

    public Player (String pseudo) {
        super(pseudo);
    }

    @Override
    public void start()
    {
        super.start();
        GlobalScreen.addNativeKeyListener(this);
    }

    public void run() {
        System.out.println("--- Thread player démarré ---");
        this.flush = false;

        while (!flush) {
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

    public void flush() {
        flush = true;
    }

    public void removeLive() {
        System.out.println("remove live");
        currentLives--;
        if (currentLives <= 0) {
            send(new PlayerLostEvent(ThreadID.LocalPlayer), ThreadID.Controller);
        }
        else
        {
            send(new RespawnPenguinEvent(ThreadID.LocalPlayer, this), ThreadID.Controller);
        }
    }


    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        currentInput = nativeKeyEvent;
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) { }

    @Override
    public void grab(Event e) {

    }
}