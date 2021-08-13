package com.generic.gameplay;

import com.generic.launcher.Launcher;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import static com.generic.gameplay.config.CONFIG_GAME.PLAYER_IS_ANIMAL;
import static com.generic.gameplay.config.CONFIG_GAME.PLAYER_IS_PENGUIN;
import static java.lang.Thread.sleep;

/**
 * TODO: création du inputHandler différée ?
 * TODO: généralisation pour toute MapEntity
 */

public class LocalPlayer extends AbstractPlayer implements NativeKeyListener {

    private volatile boolean flush;
    private NativeKeyEvent currentInput = null;

    public LocalPlayer(String pseudo) {
        super(pseudo);
    }

    public void run() {
        System.out.println("--- Thread player démarré ---");
        this.flush = false;

        while (!flush) {
            if (controlledObject != null)
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
        currentLives--;
        if (currentLives <= 0) {
            //TODO: virer tout ça.
            //Launcher.instance.getGame().setAIwin(true);
            //Launcher.instance.getGame().postGame();
        }
        else
        {
            //TODO: envoyer message de respawn
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        currentInput = nativeKeyEvent;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }
}
