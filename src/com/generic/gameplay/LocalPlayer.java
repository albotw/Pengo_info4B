package com.generic.gameplay;

import com.generic.core.MapEntity;
import com.generic.launcher.Launcher;
import com.generic.utils.InputHandler;

import static com.generic.gameplay.CONFIG_GAME.PLAYER_IS_ANIMAL;
import static com.generic.gameplay.CONFIG_GAME.PLAYER_IS_PENGUIN;
import static java.lang.Thread.sleep;

/**
 * TODO: création du inputHandler différée ?
 * TODO: généralisation pour toute MapEntity
 */

public class LocalPlayer extends AbstractPlayer {
    private InputHandler ih;

    private volatile boolean flush;

    public LocalPlayer(String pseudo) {
        super(pseudo);
    }

    public void run() {
        System.out.println("--- Thread player démarré ---");
        ih = new InputHandler(((LocalGame) (LocalGame.instance)).getWindow());
        this.flush = false;

        while (!flush) {
            if (controlledObject != null) {
                if (ih.UP == true) {
                    controlledObject.goUp();
                } else if (ih.DOWN == true) {
                    controlledObject.goDown();
                } else if (ih.LEFT == true) {
                    controlledObject.goLeft();
                } else if (ih.RIGHT == true) {
                    controlledObject.goRight();
                } else if (ih.ACTION == true) {
                    if (controlledObject.getType().equals("Penguin")) {
                        ((MapEntity) (controlledObject)).action();
                    }
                }
            }
            if (ih != null) {
                ih.flush();
            }
            try {
                sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.controlledObject = null;
        this.ih = null;
        System.out.println("--- Thread player arrété ---");
    }

    public void flush() {
        flush = true;
        ih.flush();
        ih.stop();
    }

    public void removeLive() {
        currentLives--;
        if (currentLives <= 0) {
            Launcher.instance.getGame().setAIwin(true);
            Launcher.instance.getGame().gameEnd();
        } else {
            if (PLAYER_IS_PENGUIN) {
                LocalGame.instance.respawnPenguin(this);
            } else if (PLAYER_IS_ANIMAL) {
                LocalGame.instance.respawnAnimal(this);
            }
        }
    }
}
