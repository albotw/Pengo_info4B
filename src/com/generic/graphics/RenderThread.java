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

import com.generic.gameplay.Game;

import java.awt.*;

import static com.generic.gameplay.CONFIG.RP_WIDTH;
import static com.generic.gameplay.CONFIG.WINDOW_TITLE;

public class RenderThread extends Thread {

    private boolean continueDrawing;

    private FPSCounter fps;
    private Window w;
    private RenderPanel rp;


    public RenderThread(Window output)
    {
        fps = new FPSCounter();
        rp = new RenderPanel();
        output.add(rp, BorderLayout.CENTER);

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
            w.getGameOverlay().updateValues();

            try
            {
                this.sleep(16);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void stopRendering()
    {
        continueDrawing = false;
        fps.stop();
    }
}
