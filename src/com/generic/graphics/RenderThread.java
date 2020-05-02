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
import com.generic.gameplay.Game;

import java.awt.*;

import static com.generic.gameplay.CONFIG.WINDOW_TITLE;

public class RenderThread extends Thread {

    private boolean continueDrawing;

    private FPSCounter fps;
    private Window w;
    private RenderPanel rp;

    private GameOverlay go;
    public RenderThread(Window output)
    {
        fps = new FPSCounter();
        rp = new RenderPanel();
        go = new GameOverlay();
        output.setLayout(new BorderLayout());
        output.add(rp, BorderLayout.CENTER);
        output.add(go, BorderLayout.NORTH);

        continueDrawing = true;

        w = output;
    }

    public void run()
    {
        fps.start();
        while(continueDrawing)
        {
            SpriteManager.transfer(Game.instance.getMap(), this);
            fps.frame();
            w.setTitle(WINDOW_TITLE + " | FPS: "+fps.get());
            rp.repaint();
            go.repaint();

            try
            {
                sleep(16);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        w.setVisible(false);
        w.dispose();
    }

    public void stopRendering()
    {
        continueDrawing = false;
        fps.stop();
    }
}
