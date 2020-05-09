/**
 * Nom de la classe: RenderThread
 *
 * Description: thread responsable de l'affichage et de la gestion de sprites dans un JPanel
 *
 * Version: 1.0
 *
 * Date: 08/03/2020
 *
 * Auteur: Yann
 */

package com.generic.graphics;

import com.generic.UI.GameOverlay;
import com.generic.gameplay.CONFIG;
import com.generic.gameplay.LocalGame;
import com.generic.gameplay.OnlineClient;

import java.awt.*;

import static com.generic.gameplay.CONFIG.WINDOW_TITLE;
import static com.generic.gameplay.CONFIG_GAME.*;

public class RenderThread extends Thread {

    private boolean continueDrawing;

    private FPSCounter fps;
    private Window w;
    private RenderPanel rp;

    private GameOverlay go;

    private boolean client;

    public RenderThread() {
        this.w = new Window(CONFIG.WINDOW_WIDTH, CONFIG.WINDOW_HEIGHT);
        fps = new FPSCounter();
        rp = new RenderPanel();
        go = new GameOverlay();
        w.setLayout(new BorderLayout());
        w.add(rp, BorderLayout.CENTER);
        w.add(go, BorderLayout.NORTH);

        continueDrawing = true;
    }

    public void setClient(boolean val){
        this.client = val;
    }

    public void run() {
        System.out.println("--- RenderThread started ---");
        fps.start();
        while (continueDrawing) {
            if (client)
                SpriteManager.transfer(OnlineClient.instance.getMap(), this);
            else
                SpriteManager.transfer(LocalGame.instance.getMap(), this);

            fps.frame();
            w.setTitle(WINDOW_TITLE + " | FPS: " + fps.get());
            w.revalidate();
            rp.repaint();

            // go.update();
            go.repaint();
            try {
                sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SpriteManager.instance.flushSprites();
        w.setVisible(false);
        w.dispose();
    }

    public void stopRendering() {
        continueDrawing = false;
        fps.stop();
    }

    public Window getWindow() {
        return this.w;
    }
}
