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

import java.util.ArrayList;
import static com.generic.utils.CONFIG.WINDOW_TITLE;

public class RenderThread extends Thread {
    private RenderPanel rp;
    private boolean continueDrawing;
    private ArrayList<Sprite> foregroundPile;
    private ArrayList<Sprite> backgroundPile;
    private FPSCounter fps;
    private Window w;

    public RenderThread(Window output)
    {
        fps = new FPSCounter();
        rp = new RenderPanel();

        rp.setSize(output.getWidth(), output.getHeight());
        output.add(rp);

        foregroundPile = rp.getForegroundPile();
        backgroundPile = rp.getBackgroundPile();

        continueDrawing = true;

        w = output;
    }

    public void addToRenderPile(Sprite spr, String position) {
        if (position == "foreground")
        {
            foregroundPile.add(spr);
        }
        else if (position == "background")
        {
            backgroundPile.add(spr);
        }
        else
        {
            System.out.println("Error: invalid parameter, expected foreground | background, found "+ position);
        }
    }

    public void removeFromRenderPile(Sprite spr)
    {
        if (foregroundPile.contains(spr))
        {
            foregroundPile.remove(spr);
        }
        else if (backgroundPile.contains(spr))
        {
            backgroundPile.remove(spr);
        }
        else
        {
            System.out.println("Error: invalid parameter, sprite not found");
        }
    }

    public void run()
    {
        fps.start();
        while(continueDrawing == true)
        {
            fps.frame();
            w.setTitle(WINDOW_TITLE + " | FPS: "+fps.get());
            rp.repaint();
            try
            {
                Thread.sleep(16);
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
